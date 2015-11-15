package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * Represents a modification to a LinkedList<Character>
 * with a specific type, location and optional character
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Modification {
    /**
     * The location of this modification
     */
    private int location;

    /**
     * The type of this modification
     */
    private ModificationType type;

    /**
     * The character (optional) that is represented by this class
     */
    private Character character;

    public Modification(ModificationType type, int location){
        if (type == ModificationType.DELETION)
            throw new IllegalArgumentException();

        this.type = type;
        this.location = location;
    }

    public Modification(ModificationType type, int location,
                        Character character){
        if (character == null || type == ModificationType.INSERTION)
            throw new IllegalArgumentException();

        this.character = character;
        this.type = type;
        this.location = location;
    }

    public Modification(String generatedString){
        parseFromString(generatedString);
    }

    /*******************************************************************
     * Get the type of this modification
     * @return The type of this modification
     ******************************************************************/
    public ModificationType getType() {
        return type;
    }

    /*******************************************************************
     * Get the location of this modification
     * @return The location of this modification
     ******************************************************************/
    public int getLocation() {
        return location;
    }

    /*******************************************************************
     * Get the character (if applicable) for this modification
     * @return The applicable character (or null) from this modification
     ******************************************************************/
    public Character getCharacter() {
        return character;
    }

    /*******************************************************************
     * Return a string representation of the modification
     * @return A string in the form of:
     *          "MODIFICATION_TYPE LOCATION (Optional: CHARACTER)"
     ******************************************************************/
    @Override
    public String toString(){
        String out = "";

        out += type.name();
        out += " " + location;

        if (character != null){
            out += " " + character;
        }

        return out;
    }

    /*******************************************************************
     * Set this object's fields according to a string from toString()
     * @param input The string to parse
     * @throws IllegalArgumentException if input is invalid in any way
     ******************************************************************/
    public void parseFromString(String input){
        if (input == null)
            throw new IllegalArgumentException();

        String[] data = input.split(" ");

        //The data should be in two or three chunks
        if (data.length != 2 && data.length != 3)
            throw new IllegalArgumentException();

        ModificationType type;
        try {
            String incomingType = data[0];
            type = Enum.valueOf(ModificationType.class, incomingType);
        } catch (IllegalArgumentException e){
            //We want to be the source of the exception
            throw new IllegalArgumentException();
        }

        int location;
        try {
            String incomingLocation = data[1];
            location = Integer.parseInt(incomingLocation);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

        Character character = null;
        if (data.length == 3) {
            String incomingCharacter = data[2];
            if (incomingCharacter.length() != 1)
                throw new IllegalArgumentException();

            character = incomingCharacter.charAt(0);
        }

        //If we made it this far, the data is all valid
        this.type = type;
        this.location = location;

        if (type != ModificationType.INSERTION)
            this.character = character;
    }
}
