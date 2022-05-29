package me.camm.productions.bedwars.Explosions;



import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.List;

import static java.lang.Double.NaN;


/**
 * todo unfinished.
 * @author CAMM
 * Models a class for calculating velocity to apply to entities
 */
public class VelocityComponent {
    private final EntityExplodeEvent event;
    private final boolean isFireball;

    public VelocityComponent(EntityExplodeEvent event)
    {
        this.event = event;
        Entity entity = event.getEntity();
      isFireball = entity instanceof Fireball  || entity.getType().toString().toLowerCase().contains("fireball");
    }


    public void applyVelocity()  //unfinished. Need to refactor since physics is not entirely accurate. Note added 2021-11m-16d
    {

        Entity exploded = this.event.getEntity();
        Location origin = exploded.getLocation();

        List<Entity> nearEntities = exploded.getNearbyEntities(exploded.getLocation().getX(), exploded.getLocation().getY(), exploded.getLocation().getZ());

        for (Entity e: nearEntities) //for all of the nearby entities to the explosion..
        {
            if (!VectorToolBox.isValidVelocityType(e))   //So if the entity can be affected by velocity
               continue;
            constructAndImpart(origin, e.getLocation(),e);

        }//for nearby
    }//method


    /*
    Construct the velocity and imparts it
     */
    private void constructAndImpart(Location explosionLoc, Location entityLoc, Entity target){


          /*
        Bounding box fields:

        a = min X
        b = min Y
        c = min Z

        d = max X
        e = max Y
        f = max Z

        See: https://nms.screamingsandals.org/1.8.8/net/minecraft/server/VVV/AxisAlignedBB.html
        See: https://github.com/KevyPorter/Minecraft-Forge-Utils/blob/master/fields.csv
         */



        /*

        Hypothesis:
        The y magnitude is the total magnitude of the explosion split into the x,y,and z axes.
        the x and z do not split up the H magnitude, but it is instead used for both.

        Use the centre of mass (Not the head, but the centre of the body.)

          Results: Promising.

          also check on explosion pwr to make it stronger

         */

        AxisAlignedBB box = ((CraftEntity)target).getHandle().getBoundingBox();
        Location centreMass =
        new Location(entityLoc.getWorld(), (box.d-box.a)/2 + box.a, (box.e-box.b)/2 + box.b,(box.f-box.c)/2 + box.c);


        double delX, delY, delZ;
        delX = centreMass.getX() - explosionLoc.getX();
        delY = centreMass.getY() - explosionLoc.getY();
        delZ = centreMass.getZ() - explosionLoc.getZ();

        double totalDist = Math.sqrt(delX*delX + delY*delY + delZ*delZ); //hypotenuse for vert angle
        double horDistance = Math.sqrt(delX*delX +delZ*delZ);
        double totalMagnitude = getTNTVectorMagnitude(totalDist);

        System.out.println("tot mag init: "+totalMagnitude);

        boolean straightUp = false;
        boolean onlyHorizontal = false;

        //theta = arcsin(opposite/hypotenuse)
        //we are suggesting that sine represents y axis (from a side view, since sine90 = 1, which is directly up)
        //1*totalMag = player goes directly up. as opposed to cos, which goes to 0.
        //0*totalMag = player goes nowhere.


        /*
        Get the vertical angle of the motion.
        If the horDistance ==0, we want to avoid div by 0, so it is 90* since it is straight up.

        theta = atan (opp / adj)
         */
        double vertAngle;

        /*
             +z (90)
             |
             |
       ------------- +x (0)
             |
             |
        Accounting for edge cases here.
        since we cannot div by 0, we say it's 90*, so directly up.
         */
        if (horDistance == 0) {
            vertAngle = Math.toRadians(90);
        }
        else if (delY == 0)
        {

            //so if the displacement y is 0, we know that there is no upwards motion. There is no vert angle.
            vertAngle = NaN;
            onlyHorizontal = true;
        }
        else {
            /*
            Theta = tan^-1 (opp/adj)
                |
                |  y
            ----|
         hor dist

             */
            vertAngle = Math.atan(delY/horDistance);
        }



        double horAngle;
        if (delX == 0 && delZ == 0) {
            //if hor dist = 0, then hor Angle dne, and we are going straight up.
            horAngle = NaN;
            straightUp = true;
        } else if (delX == 0 || delZ == 0) {

            //if 1 is 0, we account for an edge case.
            horAngle = 0;

            //if x (+), then we have 0 deg, else 180.
            /*
           +z (90)
             |
             |
       ------------- +x (0)
             |
             |
             */
            if (delZ == 0)
                horAngle = delX > 0 ? 0: Math.toRadians(180);


            //same with the z values.
            if (delX == 0)
                horAngle = delZ > 0 ? Math.toRadians(90) : Math.toRadians(270);
        }
        else {
            //the angle is the tan of delZ and delX, with Z as the "y axis" and x as the "x axis".
            horAngle = Math.atan(delZ/delX);
            //this gives a value close to 0 from the (-) side if the position of the player
            // is on the -x side, so we must add 180* to normalize it if so.

            if (delX<0)
                horAngle += Math.PI;  //180* in rad form
        }

        double xVel, yVel, zVel;
        double horMagnitude;



        //splitting up the hor magnitude into magnitudes for the x and z planes.
        if (onlyHorizontal) {
            yVel = 0;
            horMagnitude = totalMagnitude;
        }
        else {
            yVel = totalMagnitude * Math.sin(vertAngle);
            horMagnitude = totalMagnitude * Math.cos(vertAngle);
        }


        if (straightUp) {
            xVel = 0;
            zVel = 0;
        }
        else {
            xVel = horMagnitude * Math.cos(horAngle);
            zVel = horMagnitude * Math.sin(horAngle);
        }

        System.out.println("Vel: "+xVel+" "+yVel+" "+zVel);
        System.out.println("Vel recnst: "+Math.sqrt(xVel*xVel + yVel*yVel + zVel*zVel));



        //impart the velocity
        impartVelocity(xVel,yVel,zVel, target);

    }

    /*
    returns a value for the y value of the velocity vector. x is used since
     the value is calculated through a function.

  distance is the distance from the explosion
  MAX is the max velocity to impart on the entity
     */
    private double getTNTVectorMagnitude(double distance){
        final double MAX = 1.86;
        if (distance < 0.5)
            return MAX;
        else {
            //this is a function for a graph.
            //we used measurements to get a series of points, then made a graph that best fits those
            //points for the velocity.
            // (Are there other ways to write this w/o the magic numbers?)

        /*
        -0.00489x^6 + 0.088x^5 - 0.602x^4 + 1.916x^3 -2.702x^2 +0.942x +1.86
         */
            double magnitude = (-0.00489 * (Math.pow(distance, 6))) +
                    (0.088 * (Math.pow(distance, 5))) -
                    (0.602 * (Math.pow(distance, 4))) +
                    (1.916 * (distance * distance * distance)) -
                    (2.702 * (distance * distance)) +
                    (0.942 * distance) +
                    (MAX);

            return Math.max(magnitude, 0);
        }
    }

    /*
    Imparts velocity onto the entity
     */
    private void impartVelocity(double xComponent, double yComponent, double zComponent, Entity targeted)
    {
        Vector velocity = new Vector(xComponent,yComponent,zComponent);
        targeted.setVelocity(targeted.getVelocity().add(velocity));
    }

}

