package me.camm.productions.bedwars.Files.FileKeywords;

/*
 * @author CAMM
 * List of contributors and what they did (alphabetical order)
 */
public enum ContributorList
{
    CODE_TEAM(new String[] {"Coding Crew:"}),
    CODER(new String[] {"Developer:", "CAMM_H87"}),

    PLAY_TEAM(new String[]{"Play Team:"}),
    TESTING(new String[] {"General Testing:","CAMM_H87","GR3aterG0ld","KiwiGod888","Lachi_molala","Marcaroni_","MrTeavee","sophieeeishere"}),
    MAJORS(new String[] {"Major Contributors: ","Buster_Buckaroo","CAMM_H87","KiwiGod888","Lachi_molala","Lercerpe","Marcaroni_","MrTeavee"}),
    RESEARCH(new String[] {"Research:","adithemaddy","Buster_Buckaroo", "CAMM_H87","GR3aterG0ld", "KiwiGod888", "Lercerpe", "Marcaroni_", "MrTeavee"}),
    MORALE(new String[] {"Emotional Support:","Lachi_molala","Marcaroni_","MrTeavee"}),
    SPECIAL(new String[] {"Special thanks:","Buster_Buckaroo, KiwiGod888 - For a private Hypixel BedWars match.","Lachi_molala - For lending a Minecraft account to help with dedicated testing."}),
    PLAY_TEST(new String[]{"Play Testing:","CAMM_H87","ChristmasPotato","J4CKTH3REAPER","KiwiGod888","MaidTheMage","Marcaroni_"}),
    ORIGIN(new String[] {"\"No matter how hard something is, keep up with the persistence; you can do it. I originally started the plugin by myself, just after finishing a java course. I'm coding on my free time, and often am quite busy.",
            " Bedwars is originally made by the Hypixel Team. This plugin is for private educational and developmental purposes only, not for commercial use.","Idea for the challenge was initially from MrTeavee and KiwiGod888."}),


    DATE(new String[]{"List up to date as of July 30th 2022. Plugin development started in November 2019. Core finished on Jan 23 2021, Release 1.0.0 on Aug 2 2022"});

    private final String[] players;

    ContributorList(String[] players)
    {
        this.players = players;
    }

    public String[] getSection()
    {
        return players;
    }
}
