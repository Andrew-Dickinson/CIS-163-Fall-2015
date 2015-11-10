package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * Represents a modification to a LinkedChar list with a specific type,
 * location and optional character
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
    private char character;

    public Modification(ModificationType type, int location){
        this.type = type;
        this.location = location;
    }

    public Modification(ModificationType type, int location,
                        char character){
        this.character = character;
        this.type = type;
        this.location = location;
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
    public char getCharacter() {
        return character;
    }
}
