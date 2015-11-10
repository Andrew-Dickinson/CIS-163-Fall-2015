package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/***********************************************************************
 * Contains a linked list representing a message and an a list of all
 * of the modifications made to it
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Message {
    /**
     * The top of the linked list that stores the message
     */
    private Link<Character> top;

    /**
     * A list of modifications made to this message since its creation
     */
    private ArrayList<Modification> changeStack;

    public Message(String plainMessage){
        //Call default constructor
        this();

        //Build the message from the string
        for (int i = 0; i < plainMessage.length(); i++){
            addAtEnd(plainMessage.charAt(i));
        }
    }

    public Message(Link<Character> top,
                   ArrayList<Modification> changeStack){
        this.changeStack = changeStack;
        this.top = top;
    }

    public Message(Link<Character> top) {
        this();
        this.top = top;
    }

    public Message(){
        //Initialize top to null
        top = null;

        //Initialize an empty changeStack
        changeStack = new ArrayList<>();
    }

    /*******************************************************************
     * Adds a character to the beginning of the linked list.
     * Doesn't record this as a modification
     * @param character The character to add
     ******************************************************************/
    private void addAtBegin(Character character){
        top = new Link<>(character, top);
    }

    /*******************************************************************
     * Adds a character to the end of the linked list.
     * Doesn't record this as a modification
     * @param character The character to add
     ******************************************************************/
    private void addAtEnd(Character character){
        //Handle a null top
        if (top == null) {
            top = new Link<>(character, null);
        } else {
            //Set cur to the current final link
            Link<Character> cur = top;
            while (cur.hasNext()) {
                cur = cur.getNext();
            }

            //Append the new character
            cur.setNext(new Link<>(character, null));
        }
    }

    /*******************************************************************
     * Inserts a character into the linked list
     * Doesn't record this as a modification
     * @param pos The location to insert to
     * @param character The character to insert
     * @throws IllegalArgumentException if pos > length() or < 0
     ******************************************************************/
    private void insertIn(int pos, @NotNull Character character){
        //If pos is invalid, thrown an exception
        if (pos > length() || pos < 0)
            throw new IllegalArgumentException();

        //Handle the beginning/end cases with the appropriate methods
        if (pos == 0){
            addAtBegin(character);
        } else if (pos == length()) {
            addAtEnd(character);
        } else {
            //If pos is somewhere in the middle, insert appropriately
            int out = 0;
            Link<Character> cur = top;
            while (cur != null){
                if (out + 1 == pos){
                    //Catch the spot one before the intended insert point
                    cur.setNext(new Link<>(character, cur.getNext()));
                }

                out++;
                cur = cur.getNext();
            }
        }
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

        //Handle the beginning case
        if (pos == 0){
            top = top.getNext();
        } else {
            //If pos is somewhere in the middle, remove appropriately
            int out = 0;
            Link<Character> cur = top;
            while (cur != null){
                if (out + 1 == pos){
                    //Catch the spot one before the intended remove point
                    cur.setNext(cur.getNext().getNext());
                }

                out++;
                cur = cur.getNext();
            }
        }
    }

    /*******************************************************************
     * Inserts a character into the linked list representation and
     * creates an appropriate change stack entry
     * @param pos The position to insert the character into
     * @param character The character to insert
     * @throws IllegalArgumentException if pos > length() or < 0
     *                                  or character == null
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
     * Get the number of characters in the current (modified) linked list
     * @return The number of characters
     ******************************************************************/
    public int length(){
        int out = 0;
        Link<Character> cur = top;
        while (cur != null){
            out++;
            cur = cur.getNext();
        }

        return out;
    }

    /*******************************************************************
     * Get a string representation of the current (modified) linked list
     * @return The string representation of the current message
     ******************************************************************/
    public String getString(){
        String out = "";
        Link<Character> cur = top;
        while (cur != null){
            out += cur.getData();
            cur = cur.getNext();
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
