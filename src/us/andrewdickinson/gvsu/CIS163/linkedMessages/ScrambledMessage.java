package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * Contains a linked list representing a message and an a list of all
 * of the modifications made to it
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class ScrambledMessage {
    //TODO: re-assembly mechanic
    //TODO: Add scramble(N) method
    //TODO: File interaction

    /**
     * The linked list that stores the scrambled message
     */
    private LinkedList<Character> scrambledMessage;

    /**
     * A list of modifications made to this message since its creation
     */
    private LinkedList<Modification> changeStack;

    public ScrambledMessage(String plainMessage){
        //Call default constructor
        this();

        //In the beginning, the scrambled text starts out
        //as the plaintext

        //Build the message from the string
        for (int i = 0; i < plainMessage.length(); i++){
            scrambledMessage.add(plainMessage.charAt(i));
        }
    }

    public ScrambledMessage(LinkedList<Character> scrambledMessage,
                            LinkedList<Modification> changeStack){
        this.changeStack = changeStack;
        this.scrambledMessage = scrambledMessage;
    }

    public ScrambledMessage(LinkedList<Character> plainMessage) {
        this();
        //In the beginning, the scrambled text starts out
        //as the plaintext
        this.scrambledMessage = plainMessage;
    }

    public ScrambledMessage(){
        //Initialize top to null
        scrambledMessage = new LinkedList<>();

        //Initialize the changeStack list to null
        changeStack = new LinkedList<>();
    }

    /*******************************************************************
     * Inserts a character into the linked list representation and
     * creates an appropriate change stack entry
     * @param pos The position to insert the character into
     * @param character The character to insert
     * @throws IndexOutOfBoundsException if pos > length() or < 0
     * @throws IllegalArgumentException if character == null or
     ******************************************************************/
    public void insertCharacter(int pos, Character character){
        if (character == null) throw new IllegalArgumentException();

        if (scrambledMessage == null)
            scrambledMessage = new LinkedList<>();

        scrambledMessage.add(pos, character);

        if (changeStack == null)
            changeStack = new LinkedList<>();

        changeStack.add(
                new Modification(
                        ModificationType.INSERTION,
                        pos,
                        character
                )
        );
    }

    /*******************************************************************
     * Removes a character from the linked list representation and
     * creates an appropriate change stack entry
     * @param pos The position to remove the character from
     * @throws IndexOutOfBoundsException if pos >= length() or < 0
     * @throws IllegalArgumentException if scrambledMessage is null
     ******************************************************************/
    public void removeCharacter(int pos){
        if (scrambledMessage == null)
            //If null, the length is 0, so it's always out of bounds
            throw new IndexOutOfBoundsException();

        Character removed = scrambledMessage.remove(pos);

        changeStack.add(
                new Modification(ModificationType.DELETION, pos, removed)
        );
    }

    /*******************************************************************
     * Replaces a character in the linked list representation and
     * creates an appropriate change stack entry
     * @param pos The position to replace the character at
     * @param character The new character to replace the old one
     * @throws IndexOutOfBoundsException if pos > length() or < 0
     ******************************************************************/
    public void replaceCharacter(int pos, Character character){
        //If pos is invalid, thrown an exception
        if (pos >= length() || pos < 0)
            throw new IndexOutOfBoundsException();

        //Remove the previous character
        removeCharacter(pos);

        //Replace it with the new character
        insertCharacter(pos, character);
    }

    /*******************************************************************
     * Swaps the characters at the given positions and
     * creates an appropriate change stack entry
     * @param pos1 The index of the first character to reference
     * @param pos2 The index of the second character to reference
     * @throws IndexOutOfBoundsException if pos1/pos2 >= length() or < 0
     ******************************************************************/
    public void swapCharacters(int pos1, int pos2){
        //If a pos is invalid, throw an exception
        if (pos1 >= length() || pos1 < 0 || pos2 >= length() || pos2 < 0)
            throw new IndexOutOfBoundsException();

        //Save the character from pos1
        Character temp = getCharacter(pos1);

        //Replace the pos1 character with the pos2 character
        replaceCharacter(pos1, getCharacter(pos2));

        //Replace the pos2 character with the saved pos1 character
        replaceCharacter(pos2, temp);

    }

    /*******************************************************************
     * Get the character at the provided index in the current (modified)
     * linked list
     * @param index The index of the character to get
     * @return The character at the provided location
     * @throws IndexOutOfBoundsException if index >= length() or < 0
     ******************************************************************/
    public Character getCharacter(int index){
        //If index is invalid, thrown an exception
        if (index >= length() || index < 0 || scrambledMessage == null)
            throw new IndexOutOfBoundsException();

        return scrambledMessage.get(index);
    }

    /*******************************************************************
     * Get the number of characters in the current (modified) linked list
     * @return The number of characters
     ******************************************************************/
    public int length(){
        if (scrambledMessage == null)
            return 0;

        return scrambledMessage.size();
    }

    /*******************************************************************
     * Get a string representation of the current (modified) linked list
     * @return The string representation of the current message
     ******************************************************************/
    @Override
    public String toString(){
        if (scrambledMessage == null)
            return "";

        String out = "";
        for (int i = 0; i < scrambledMessage.size(); i++){
            out += scrambledMessage.get(i);
        }

        return out;
    }

    /*******************************************************************
     * Get a LinkedList of strings representing the the change stack
     * @return A LinkedList of the strings from Modification.toString()
     ******************************************************************/
    public LinkedList<String> exportChangeStack(){
        if (changeStack == null)
            return new LinkedList<>();

        LinkedList<String> out = new LinkedList<>();

        for (int i = 0; i < changeStack.size(); i++){
            out.add(changeStack.get(i).toString());
        }

        return out;
    }
}
