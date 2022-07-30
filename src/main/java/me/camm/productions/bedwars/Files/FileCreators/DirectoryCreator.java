package me.camm.productions.bedwars.Files.FileCreators;

import me.camm.productions.bedwars.Files.FileKeywords.ContributorList;
import me.camm.productions.bedwars.Files.FileKeywords.Instructions;
import me.camm.productions.bedwars.Files.FileStreams.GameFileWriter;
import me.camm.productions.bedwars.Util.Helpers.ChatSender;
import me.camm.productions.bedwars.Util.Helpers.StringHelper;
import me.camm.productions.bedwars.Validation.BedWarsException;
import me.camm.productions.bedwars.Validation.FileException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static me.camm.productions.bedwars.Util.Helpers.StringHelper.*;

/*
 * @author CAMM
 * Class for creating folders in the server files for configuration
 */
public class DirectoryCreator
{
    private final Plugin plugin;

    private final File credits;
    private final File instructionFile;

    private final File[] list;
    private final File[] folders;

    List<String[]> contributors;
    List<String[]> instructions;
    private final ChatSender sender;


    public DirectoryCreator(Plugin plugin) throws BedWarsException
    {

        contributors = new ArrayList<>();
        instructions = new ArrayList<>();

        sender = ChatSender.getInstance();
        this.plugin = plugin;


        File mainFile = new File(StringHelper.getMainFolderPath());
        File teamFile = new File(getTeamPath());
        File worldFile = new File(getWorldPath());
        File playerFolder = new File(getPlayerFolderPath());
        credits = new File(getCreditsPath());
        instructionFile = new File(getInstructionsPath());

        list = new File[]{mainFile, teamFile, worldFile, playerFolder, credits, instructionFile};
        folders = new File[]{mainFile, playerFolder};

        init();

    }

    private void init() throws BedWarsException {

        ContributorList[] contributors = ContributorList.values();
        Instructions[] instructions = Instructions.values();

        for (ContributorList list: contributors) {
            this.contributors.add(list.getSection());
        }

        for (Instructions instruct: instructions) {
            this.instructions.add(instruct.getInstructions());
        }


        sender.sendConsoleMessage("Attempting to designate folders...", Level.INFO);

        try {

            for (File file: folders) {

                if (file.exists() && file.isDirectory()) {
                    sender.sendConsoleMessage("Detected that Plugin File folder "+file.getName()+" exists",Level.INFO);
                    continue;
                }

                if (!file.mkdir()) {
                    throw new FileException("Could not make the "+file.getName()+" a folder");
                }

                sender.sendConsoleMessage("Made the "+file.getName()+" file a folder", Level.INFO);
            }

        }
        catch (Exception e) {
            throw new FileException("Could not initialize the main and player folders");
        }
    }


    //Creates the directory folders the plugin uses
    public void createFiles() throws BedWarsException
    {

        boolean allExists = true;
        sender.sendConsoleMessage("Attempting to create files...",Level.INFO);
        for (File file: list) {
            try {

                if (file.exists()) {
                    sender.sendConsoleMessage("Detected that the file: "+file.getName()+" exists",Level.INFO);
                    continue;
                }

                if (!file.createNewFile()) {
                    throw new FileException("Could not create the file: "+file.getName()+" with the path: "+file.getAbsolutePath());
                }

                sender.sendConsoleMessage("Created a new "+file.getName()+" file",Level.INFO);
                allExists = false;

            }
            catch (IOException e) {
                throw new FileException(e.getMessage());
            }
        }


        try {


            GameFileWriter creditWriter = new GameFileWriter(credits.getAbsolutePath(), plugin);
            GameFileWriter instructWriter = new GameFileWriter(instructionFile.getAbsolutePath(), plugin);

            sender.sendConsoleMessage("Refreshing instructions and credits...",Level.INFO);

            creditWriter.clear();
            creditWriter.writeSection(contributors);

            instructWriter.clear();
            instructWriter.writeSection(instructions);


            if (allExists) {
                sender.sendConsoleMessage("All files exist. Please make sure they are configured.", Level.INFO);
            }
            else
                throw new FileException("File creation failed. Not all files exist.");


        }catch (Exception e) {
            throw new FileException(e.getMessage());
        }

    }

}
