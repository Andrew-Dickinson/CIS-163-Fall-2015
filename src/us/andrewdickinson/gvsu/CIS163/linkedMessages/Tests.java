package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import org.junit.Test;

import static org.junit.Assert.*;

/***********************************************************************
 * Comprehensive testing for this package
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Tests {

    //Test the lower boundary of the exception handling
    @Test (expected = IllegalArgumentException.class)
    public void testInsertCharacterLowBound() {
        Message m = new Message("5#@0safa");

        m.insertCharacter(-1, 'a');
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IllegalArgumentException.class)
    public void testInsertCharacterUpperBound() {
        Message m = new Message("5#@0safa");

        m.insertCharacter(10, 'g');
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IllegalArgumentException.class)
    public void testInsertCharacterUpperBoundEmpty() {
        Message m = new Message("");

        m.insertCharacter(1, 'y');
    }

    //Test inserting a character at the beginning of the message
    @Test
    public void testInsertCharacterAtBeginning() {
        Message m = new Message("abcd");
        m.insertCharacter(0, 'x');

        assertEquals("xabcd", m.getString());
    }

    //Test inserting a character in the middle of the message
    @Test
    public void testInsertCharacterInMiddle() {
        Message m = new Message("abcd");
        m.insertCharacter(2, 'e');

        assertEquals("abecd", m.getString());
    }

    //Test inserting a character at the end of the message
    @Test
    public void testInsertCharacterAtEnd() {
        Message m = new Message("abcd");
        m.insertCharacter(4, '6');

        assertEquals("abcd6", m.getString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveCharacterLowBound() {
        Message m = new Message("5#@0safa");

        m.removeCharacter(-1);
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveCharacterUpperBound() {
        Message m = new Message("5#@0safa");

        m.removeCharacter(9);
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveCharacterUpperBoundEmpty() {
        Message m = new Message("");

        m.removeCharacter(0);
    }

    //Test removing a character from the beginning of the message
    @Test
    public void testRemoveCharacterFromBeginning() {
        Message m = new Message("abcd");
        m.removeCharacter(0);

        assertEquals("bcd", m.getString());
    }

    //Test removing a character from the middle of the message
    @Test
    public void testRemoveCharacterFromMiddle() {
        Message m = new Message("abcd");
        m.removeCharacter(2);

        assertEquals("abd", m.getString());
    }

    //Test removing a character from the end of the message
    @Test
    public void testRemoveCharacterFromEnd() {
        Message m = new Message("abcd");
        m.removeCharacter(3);

        assertEquals("abc", m.getString());
    }

    //Test that the length method returns zero when there are no elements
    @Test
    public void testLengthZero() {
        Message m = new Message();

        assertEquals(0, m.length());
    }

    //Test that the length method behaves correctly in a normal situation
    @Test
    public void testLengthNonzero() {
        Message m = new Message("alsf");

        assertEquals(4, m.length());
    }

    //Test the getString method with an empty string
    @Test
    public void testGetEmptyString() {
        Message m = new Message("");

        assertEquals("", m.getString());
    }

    //Test the getString method with an full string
    @Test
    public void testGetString() {
        Message m = new Message("gahn2");

        assertEquals("gahn2", m.getString());
    }

    //Test the Link.hasNext() method when it should be true
    @Test
    public void testHasNextTrue(){
        Link<Character> l = new Link<>('a');
        l.setNext(new Link<Character>('b'));
        assertEquals(true, l.hasNext());
    }

    //Test the Link.hasNext method when it should be false
    @Test
    public void testHasNextFalse(){
        Link<Character> l = new Link<>('a');
        assertEquals(false, l.hasNext());
    }
}