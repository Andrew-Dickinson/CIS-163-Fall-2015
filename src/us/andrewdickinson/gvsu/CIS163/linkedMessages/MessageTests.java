package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import org.junit.Test;

import static org.junit.Assert.*;

/***********************************************************************
 * Comprehensive testing for the message class
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class MessageTests {

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCharacterLowBound() {
        Message m = new Message("5#@0safa");

        m.insertCharacter(-1, 'a');
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCharacterUpperBound() {
        Message m = new Message("5#@0safa");

        m.insertCharacter(10, 'g');
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCharacterUpperBoundEmpty() {
        Message m = new Message("");

        m.insertCharacter(1, 'y');
    }

    //Test inserting a character at the beginning of the message
    @Test
    public void testInsertCharacterAtBeginning() {
        Message m = new Message("abcd");
        m.insertCharacter(0, 'x');

        assertEquals("xabcd", m.toString());
    }

    //Test inserting a character in the middle of the message
    @Test
    public void testInsertCharacterInMiddle() {
        Message m = new Message("abcd");
        m.insertCharacter(2, 'e');

        assertEquals("abecd", m.toString());
    }

    //Test inserting a character at the end of the message
    @Test
    public void testInsertCharacterAtEnd() {
        Message m = new Message("abcd");
        m.insertCharacter(4, '6');

        assertEquals("abcd6", m.toString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveCharacterLowBound() {
        Message m = new Message("5#@0safa");

        m.removeCharacter(-1);
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveCharacterUpperBound() {
        Message m = new Message("5#@0safa");

        m.removeCharacter(9);
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveCharacterUpperBoundEmpty() {
        Message m = new Message("");

        m.removeCharacter(0);
    }

    //Test removing a character from the beginning of the message
    @Test
    public void testRemoveCharacterFromBeginning() {
        Message m = new Message("abcd");
        m.removeCharacter(0);

        assertEquals("bcd", m.toString());
    }

    //Test removing a character from the middle of the message
    @Test
    public void testRemoveCharacterFromMiddle() {
        Message m = new Message("abcd");
        m.removeCharacter(2);

        assertEquals("abd", m.toString());
    }

    //Test removing a character from the end of the message
    @Test
    public void testRemoveCharacterFromEnd() {
        Message m = new Message("abcd");
        m.removeCharacter(3);

        assertEquals("abc", m.toString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testReplaceCharacterLowBound() {
        Message m = new Message("5#@0safa");

        m.replaceCharacter(-1, 'a');
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testReplaceCharacterUpperBound() {
        Message m = new Message("5#@0safa");

        m.replaceCharacter(10, 'g');
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testReplaceCharacterUpperBoundEmpty() {
        Message m = new Message("");

        m.replaceCharacter(1, 'y');
    }

    //Test replacing a character at the beginning of the message
    @Test
    public void testReplaceCharacterAtBeginning() {
        Message m = new Message("abcd");
        m.replaceCharacter(0, 'x');

        assertEquals("xbcd", m.toString());
    }

    //Test replacing a character in the middle of the message
    @Test
    public void testReplaceCharacterInMiddle() {
        Message m = new Message("abcd");
        m.replaceCharacter(2, 'e');

        assertEquals("abed", m.toString());
    }

    //Test replacing a character at the end of the message
    @Test
    public void testReplaceCharacterAtEnd() {
        Message m = new Message("abcd");
        m.replaceCharacter(3, '6');

        assertEquals("abc6", m.toString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterLowBound1() {
        Message m = new Message("5#@0safa");

        m.swapCharacters(-1, 4);
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterLowBound2() {
        Message m = new Message("5#@0safa");

        m.swapCharacters(2, -4);
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterUpperBound1() {
        Message m = new Message("5#@0safa");

        m.swapCharacters(10, 2);
    }


    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterUpperBound2() {
        Message m = new Message("5#@0safa");

        m.swapCharacters(4, 10);
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterUpperBoundEmpty() {
        Message m = new Message("");

        m.swapCharacters(1, 0);
    }

    //Test swapping a character at the beginning of the message
    @Test
    public void testSwapCharacterAtBeginning() {
        Message m = new Message("abcd");
        m.swapCharacters(0, 2);

        assertEquals("cbad", m.toString());
    }

    //Test swapping a character in the middle of the message
    @Test
    public void testSwapCharacterInMiddle() {
        Message m = new Message("abcd");
        m.swapCharacters(2, 1);

        assertEquals("acbd", m.toString());
    }

    //Test swapping a character at the end of the message
    @Test
    public void testSwapCharacterAtEnd() {
        Message m = new Message("abcd");
        m.swapCharacters(0, 3);

        assertEquals("dbca", m.toString());
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

        assertEquals("", m.toString());
    }

    //Test the getString method with an full string
    @Test
    public void testGetString() {
        Message m = new Message("gahn2");

        assertEquals("gahn2", m.toString());
    }

    //Test the getChar method with an empty string
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetCharEmptyString() {
        Message m = new Message("");

        m.getCharacter(0);
    }

    //Test the getChar method with a too low index
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetCharLowBound() {
        Message m = new Message("daslfj");

        m.getCharacter(-1);
    }

    //Test the getChar method with a too high index
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetCharHighBound() {
        Message m = new Message("daslfj");

        m.getCharacter(7);
    }

    //Test the getChar method on the first character
    @Test
    public void testGetFirstChar() {
        Message m = new Message("gahn2");

        assertTrue(m.getCharacter(0).equals('g'));
    }

    //Test the getChar method on the last character
    @Test
    public void testGetLastChar() {
        Message m = new Message("gahn2");

        assertTrue(m.getCharacter(4).equals('2'));
    }

    //Test the getChar method on one of the middle characters
    @Test
    public void testGetMiddleChar() {
        Message m = new Message("gahn2");

        assertTrue(m.getCharacter(2).equals('h'));
    }

    @Test
    public void testToStringAndLenthWithNullInstanceVars(){
        Message m = new Message(null, null);

        assertEquals("", m.toString());
        assertEquals(0, m.length());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveWithNullInstanceVars(){
        Message m = new Message(null, null);
        m.removeCharacter(0);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetCharacterWithNullInstanceVars(){
        Message m = new Message(null, null);
        m.getCharacter(0);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testSwapCharactersWithNullInstanceVars(){
        Message m = new Message(null, null);
        m.swapCharacters(0, 1);
    }

    @Test
    public void testInsertCharacterWithNullInstanceVars(){
        Message m = new Message(null, null);
        m.insertCharacter(0, 'a');
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testReplaceCharacterWithNullInstanceVars(){
        Message m = new Message(null, null);
        m.replaceCharacter(0, 'a');
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInsertNullCharacter(){
        Message m = new Message();
        m.insertCharacter(0, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testReplaceNullCharacter(){
        Message m = new Message("abd");
        m.replaceCharacter(0, null);
    }
}