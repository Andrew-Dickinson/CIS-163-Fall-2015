package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs.SymmetricBulletProofDialog;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist.LinkedList;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

/***********************************************************************
 * Displays a command line interface for user interaction with the
 * ScrambledMessage class
 * Created by Andrew on 12/1/15.
 **********************************************************************/
public class MessageConsole {
    /**
     * The message for this class to interact with
     */
    private ScrambledMessage message;

    /**
     * The console to interact with the user
     */
    private Scanner input;

    /**
     * True when the last command executed was valid
     */
    private boolean lastCommandExitGood = true;

    /**
     * Used for cut, copy and paste
     */
    private LinkedList<Character> clipboard;

    /*******************************************************************
     * Correctly establishes console and message based on user input
     ******************************************************************/
    public MessageConsole(){
        input = new Scanner(System.in);

        message = getMessageFromSource();
    }

    /*******************************************************************
     * Displays the current message, Uses readLine() to take a command,
     * executes the command (if applicable) and displays any errors
     ******************************************************************/
    public void takeCommand(){
        //Only display the overview if the user needs an update
        String overViewString;
        if (lastCommandExitGood){
            overViewString = getMessageWithIndices();
        } else {
            overViewString = "";
        }

        String in = readDataLine(overViewString, "Enter a command");

        //We know that in was at least one character long and not " "
        //Therefore split[0] is at least one character long and not null
        String base_cmd = in.split(" ")[0];

        //Handle errors appropriately
        lastCommandExitGood = false;

        String[] args;
        switch (base_cmd){
            case "Q":
            case "quit":
                //Quit. Accepts no arguments
                //Print the scrambled form of the message
                writeLine("Final scrambled message: ");
                writeLine(message.toString());
                lastCommandExitGood = true;
                System.exit(0);
                break;
            case "b":
            case "i":
            case "insert":
                //Insert. Arguments are character and index
                args = parseArguments(in, 3);
                if (args == null) break;
                if (!assertIndex(args[2], message.length())) break;
                message.insertCharacter(Integer.parseInt(args[2]),
                                        args[1].charAt(0));
                lastCommandExitGood = true;
                break;
            case "a":
            case "append":
                //Append. Argument is character
                args = parseArguments(in, 2);
                if (args == null) break;
                message.insertCharacter(message.length(),
                                        args[1].charAt(0));
                lastCommandExitGood = true;
                break;
            case "r":
            case "remove":
                //Remove. Argument is index
                args = parseArguments(in, 2);
                if (args == null) break;
                if (!assertIndex(args[1], message.length() - 1)) break;
                message.removeCharacter(Integer.parseInt(args[1]));
                lastCommandExitGood = true;
                break;
            case "w":
            case "swap":
                //Swap. Arguments are index and index
                args = parseArguments(in, 3);
                if (args == null) break;
                if (!assertIndex(args[1], message.length() - 1)) break;
                if (!assertIndex(args[2], message.length() - 1)) break;
                message.swapCharacters(Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]));
                lastCommandExitGood = true;
                break;
            case "s":
            case "save":
                //Save. Argument is file path
                args = parseArguments(in, 2);
                if (args == null) break;
                try {
                    message.saveChangeStackToFile(args[1]);

                    //Don't display the update. Nothing has changed
                    lastCommandExitGood = false;

                    writeLine("File written successfully!");
                } catch (IOException e){
                    writeError("Error writing file");
                } catch (UnsupportedOperationException e){
                    writeError("You must make at least one " +
                               "change before you save");
                } catch (IllegalArgumentException e){
                    writeError("There must be at least one " +
                               "character in order to save");
                }
                break;
            case "x":
            case "cut":
                //Cut. Arguments are index and index
                args = parseArguments(in, 3);
                if (args == null) break;
                if (!assertIndex(args[1], message.length() - 1)) break;
                if (!assertIndex(args[2], message.length() - 1)) break;
                int i1 = Integer.parseInt(args[1]);
                int i2 = Integer.parseInt(args[2]);
                if (i1 > i2) { writeError("Invalid Index"); break; }
                clipboard = new LinkedList<>();
                for (int i = 0; i <= (i2 - i1); i++){
                    clipboard.add(message.getCharacter(i1));
                    message.removeCharacter(i1);
                }
                lastCommandExitGood = true;
                break;
            case "p":
            case "paste":
                //Paste. Argument is index
                args = parseArguments(in, 2);
                if (args == null) break;
                if (!assertIndex(args[1], message.length())) break;
                for (int i = 0; i < clipboard.size(); i++){
                    message.insertCharacter(Integer.parseInt(args[1]),
                                            clipboard.get(i));
                }
                lastCommandExitGood = true;
                break;
            case "c":
            case "copy":
                //Copy. Arguments are index and index
                args = parseArguments(in, 3);
                if (args == null) break;
                if (!assertIndex(args[1], message.length() - 1)) break;
                if (!assertIndex(args[2], message.length() - 1)) break;
                i1 = Integer.parseInt(args[1]);
                i2 = Integer.parseInt(args[2]);
                if (i1 > i2) { writeError("Invalid Index"); break; }
                clipboard = new LinkedList<>();
                for (int i = 0; i <= (i2 - i1); i++){
                    clipboard.add(message.getCharacter(i1));
                }
                lastCommandExitGood = true;
                break;
            case "rb":
            case "randinsert":
                //Random Insert. Takes no arguments
                message.insertRandomCharacter();
                lastCommandExitGood = true;
                break;
            case "rs":
            case "randreplace":
                //Random Set (Replace). Takes no arguments
                message.replaceRandomCharacter();
                lastCommandExitGood = true;
                break;
            case "rr":
            case "randsemove":
                //Random Remove. Takes no arguments
                message.removeRandomCharacter();
                lastCommandExitGood = true;
                break;
            case "rw":
            case "randswap":
                //Random Swap. Takes no arguments
                message.swapRandomCharacters();
                lastCommandExitGood = true;
                break;
            case "h":
            case "shuffle":
                //Shuffle. Takes no arguments
                message.randomlyScramble();
                lastCommandExitGood = true;
                break;
            default:
                writeError("Command not recognised");
                break;
        }
    }

    private String getMessageWithIndices(){
        String currentMessage = message.toString();

        if (currentMessage.length() > 0) {
            //Build a string in the form "0 1 2"
            //Also build a string the form "h i  I ' m   A n d r e w"
            String indicesString = "";
            String spacedString = "";
            for (int i = 0; i < currentMessage.length(); i++) {
                indicesString += i;
                spacedString += currentMessage.charAt(i);
                if (i != currentMessage.length() - 1) {
                    indicesString += " ";
                    spacedString += " ";
                    if (i > 9) spacedString += " ";
                }
            }

            return indicesString + "\n" + spacedString;
        } else {
            return "{ Scrambled Message is Empty }";
        }
    }

    /*******************************************************************
     * Get the message source from the user and create the corresponding
     * ScrambledMessage object
     * @return The created ScrambledMessage
     ******************************************************************/
    private ScrambledMessage getMessageFromSource(){
        //Prompt the user to determine the message source
        writeLine("Would you like to create a new scrambled " +
                "message or open a previous one to de-scramble it?");
        writeLine("N - New message");
        writeLine("O - Open a previously scrambled message");
        String source = readLine("Enter either N or O: ");
        source = source.toLowerCase();

        ScrambledMessage messageIn;

        //Process user input
        if (source.equals("n")) {
            String msg =
                readDataLine("Enter the plaintext of the new message",
                             "Please enter a message");
            messageIn = new ScrambledMessage(msg);
            return messageIn;
        } else if (source.equals("o")){
            String scrmbl = readDataLine("Enter the scrambled message",
                                         "Please enter a message");
            String filePath =
                    readDataLine("Enter the path to the changes file",
                                 "Please enter a file path");
            try {
                messageIn = new ScrambledMessage(scrmbl, filePath);

                writeLine("De-Scrambled Message is:");
                writeLine(messageIn.getDeScrambled());
                writeLine(" ");
                writeLine("You may now make additional modifications:");

                return messageIn;
            } catch (IOException e){
                writeError("Error reading file");
                System.exit(1);
            } catch (IllegalArgumentException e){
                writeError("Invalid file for given message");
                System.exit(1);
            }
        } else {
            writeError("Invalid value");
            System.exit(1);
        }

        //Will never occur
        return null;
    }

    /*******************************************************************
     * Use the scanner to get a line from System.in
     * @param prompt The prompt to present the user with (can be null)
     * @return The typed line
     ******************************************************************/
    private String readLine(String prompt){
        if (prompt == null) prompt = "";
        System.out.print(prompt);
        return input.nextLine();
    }

    /*******************************************************************
     * Call readLine(": ")
     * @return The typed line
     ******************************************************************/
    private String readLine(){
        return readLine("> ");
    }

    /*******************************************************************
     * Display a line to the user
     * @param line The line to display
     ******************************************************************/
    private void writeLine(String line){
        System.out.println(line);
    }

    /*******************************************************************
     * Display an error to the user
     * @param line The line to display
     ******************************************************************/
    private void writeError(String line){
        System.err.println(line);
        try {
            //Sleep briefly to keep the prompt from overlapping
            //the error
            Thread.sleep(10);
        } catch (InterruptedException e){
            //Do literally nothing
            //We're only sleeping to help keep the UI clean
            //Stupid compiler
        }
    }

    /*******************************************************************
     * Prompts the user using readLine() until they type a value
     * @param prompt The prompt to initially display
     * @param error The prompt to display if the user doesn't enter data
     * @return The entered data
     ******************************************************************/
    private String readDataLine(String prompt, String error){
        //No need to write ""
        if (!prompt.equals("")) writeLine(prompt);

        //Ensure that the user enters at least one character
        String data = "";
        while (data.length() == 0 && !data.equals(" ")) {
            data = readLine();
            if (data.length() == 0 && !data.equals(" "))
                writeError(error);
        }

        return data;
    }

    /*******************************************************************
     * Check cmd has the correct number of arguments and parse out the
     * arguments (including " " as a character)
     *
     * If it's going to output null it prints an error:
     * "Invalid Arguments"
     *
     * @param cmd The entered command string
     * @param expected The number of anticipated arguments
     * @return An array of the arguments with spaces parsed correctly or
     *          null if the cmd doesn't match the number of expected
     *          arguments
     ******************************************************************/
    private String[] parseArguments(String cmd, int expected){
        Character marker = '\0';
        String[] raw_split = cmd.split(" ");

        //Clean out the marker if necessary
        for (int i = 0; i < raw_split.length; i++){
            if (raw_split[i].equals(marker.toString())){
                raw_split[i] = " ";
            }
        }

        if (raw_split.length == expected){
            //The number of arguments is already correct
            return raw_split;
        } else if (raw_split.length == expected + 1){
            //A space character was possibly entered

            //Check if the command has a space with appropriate padding
            if (cmd.contains("   ")){
                cmd = cmd.replace("   ", " " + marker + " ");

                //Replaced the intended space with a marker
                //And recursively call with the marker embedded
                return parseArguments(cmd, expected);
            } else {
                //The number of arguments is just wrong
                writeError("Invalid Arguments");
                return null;
            }
        } else {
            //The cmd makes no sense for the given length
            writeError("Invalid Arguments");
            return null;
        }
    }

    /*******************************************************************
     * Checks if s is a valid int >= 0 and <= max.
     * If it's not, print the error: "Invalid Index" and return false
     * If it is, return true
     * @param s The string to parse
     * @param max The max value (inclusive) of the parsed int
     * @return True if s is valid. False otherwise
     ******************************************************************/
    private boolean assertIndex(String s, int max){
        try {
            Integer index = Integer.parseInt(s);
            if (index >= 0 && index <= max){
                return true;
            } else {
                writeError("Invalid Index");
                return false;
            }
        } catch (NumberFormatException e){
            writeError("Invalid Index");
            return false;
        }
    }
}
