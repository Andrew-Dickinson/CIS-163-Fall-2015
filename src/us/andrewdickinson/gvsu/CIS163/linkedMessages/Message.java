package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import java.util.ArrayList;

/***********************************************************************
 * Contains a linked list representing a message and an a list of all
 * of the modifications made to it
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Message {
    //TODO: Export changeStack
    //TODO: re-assembly mechanic
    //TODO: Add scramble(N) method
    //TODO: File interaction
    //TODO: Tail pointer?

    /**
     * The top of the linked list that stores the message
     */
    private LinkedList<Character> characterList;

    /**
     * A list of modifications made to this message since its creation
     */
    private LinkedList<Modification> changeStack;

    public Message(String plainMessage){
        //Call default constructor
        this();

        //Build the message from the string
        for (int i = 0; i < plainMessage.length(); i++){
            addAtEnd(plainMessage.charAt(i));
        }
    }

    public Message(LinkedList<Character> characterList,
                   LinkedList<Modification> changeStack){
        this.changeStack = changeStack;
        this.characterList = characterList;
    }

    public Message(LinkedList<Character> characterList) {
        this();
        this.characterList = characterList;
    }

    public Message(){
        //Initialize top to null
        characterList = new LinkedList<>();

        //Initialize the changeStack list to null
        changeStack = new LinkedList<>();
    }

    /*******************************************************************
     * Adds a character to the beginning of the linked list.
     * Doesn't record this as a modification
     * @param character The character to add
     ******************************************************************/
    private void addAtBegin(Character character){
        characterList.add(0, character);
    }

    /*******************************************************************
     * Adds a character to the end of the linked list.
     * Doesn't record this as a modification
     * @param character The character to add
     ******************************************************************/
    private void addAtEnd(Character character){
        characterList.add(character);
    }

    /*******************************************************************
     * Inserts a character into the linked list
     * Doesn't record this as a modification
     * @param pos The location to insert to
     * @param character The character to insert
     * @throws IllegalArgumentException if pos > length() or < 0
     ******************************************************************/
    private void insertIn(int pos, Character character){
        //If pos is invalid, thrown an exception
        if (pos > length() || pos < 0)
            throw new IllegalArgumentException();

        characterList.add(pos, character);
    }

    /*******************************************************************
     * Removes the specified character from the linked list
     * Doesn't record this as a modification
     * @param pos The index of the character to remove
     * @throws IllegalArgumentException if pos >= length() or < 0
     ******************************************************************/
    private void removeFrom(int pos){
        //If pos is invalid, thrown an exception
        if (pos >= length() || pos < 0)
            throw new IllegalArgumentException();

        characterList.remove(pos);
    }

    /*******************************************************************
     * Inserts a character into the linked list representation and
     * creates an appropriate change stack entry
     * @param pos The position to insert the character into
     * @param character The character to insert
     * @throws IllegalArgumentException if pos > length() or < 0
     ******************************************************************/
    public void insertCharacter(int pos, Character character){
        if (character == null) throw new IllegalArgumentException();

        insertIn(pos, character);

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
     * @throws IllegalArgumentException if pos >= length() or < 0
     ******************************************************************/
    public void removeCharacter(int pos){
        removeFrom(pos);

        changeStack.add(
                new Modification(ModificationType.DELETION, pos));
    }

    /*******************************************************************
     * Replaces a character in the linked list representation and
     * creates an appropriate change stack entry
     * @param pos The position to replace the character at
     * @param character The new character to replace the old one
     * @throws IllegalArgumentException if pos > length() or < 0
     ******************************************************************/
    public void replaceCharacter(int pos, Character character){
        //If pos is invalid, thrown an exception
        if (pos >= length() || pos < 0)
            throw new IllegalArgumentException();

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
     * @throws IllegalArgumentException if pos1/pos2 >= length() or < 0
     ******************************************************************/
    public void swapCharacters(int pos1, int pos2){
        //If a pos is invalid, throw an exception
        if (pos1 >= length() || pos1 < 0 || pos2 >= length() || pos2 < 0)
            throw new IllegalArgumentException();

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
     * @throws IllegalArgumentException if index >= length() or < 0
     ******************************************************************/
    public Character getCharacter(int index){
        //If index is invalid, thrown an exception
        if (index >= length() || index < 0)
            throw new IllegalArgumentException();

        return characterList.get(index);
    }

    /*******************************************************************
     * Get the number of characters in the current (modified) linked list
     * @return The number of characters
     ******************************************************************/
    public int length(){
        return characterList.size();
    }

    /*******************************************************************
     * Get a string representation of the current (modified) linked list
     * @return The string representation of the current message
     ******************************************************************/
    public String getString(){
        String out = "";
        for (int i = 0; i < characterList.size(); i++){
            out += characterList.get(i);
        }

        return out;
    }

    /*******************************************************************
     * Get a string representation of the current (modified) linked list
     * @return The string representation of the current message
     ******************************************************************/
    @Override
    public String toString(){
        return getString();
    }
}
