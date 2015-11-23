package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import org.junit.Test;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist.LinkedList;

import static org.junit.Assert.*;

/***********************************************************************
 * Comprehensive testing for the ScrambledMessage class
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class ScrambledMessageTests {

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCharacterLowBound() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.insertCharacter(-1, 'a');
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCharacterUpperBound() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.insertCharacter(10, 'g');
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCharacterUpperBoundEmpty() {
        ScrambledMessage m = new ScrambledMessage("");

        m.insertCharacter(1, 'y');
    }

    //Test inserting a character at the beginning of the message
    @Test
    public void testInsertCharacterAtBeginning() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.insertCharacter(0, 'x');

        assertEquals("xabcd", m.toString());
    }

    //Test inserting a character in the middle of the message
    @Test
    public void testInsertCharacterInMiddle() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.insertCharacter(2, 'e');

        assertEquals("abecd", m.toString());
    }

    //Test inserting a character at the end of the message
    @Test
    public void testInsertCharacterAtEnd() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.insertCharacter(4, '6');

        assertEquals("abcd6", m.toString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveCharacterLowBound() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.removeCharacter(-1);
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveCharacterUpperBound() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.removeCharacter(9);
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveCharacterUpperBoundEmpty() {
        ScrambledMessage m = new ScrambledMessage("");

        m.removeCharacter(0);
    }

    //Test removing a character from the beginning of the message
    @Test
    public void testRemoveCharacterFromBeginning() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.removeCharacter(0);

        assertEquals("bcd", m.toString());
    }

    //Test removing a character from the middle of the message
    @Test
    public void testRemoveCharacterFromMiddle() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.removeCharacter(2);

        assertEquals("abd", m.toString());
    }

    //Test removing a character from the end of the message
    @Test
    public void testRemoveCharacterFromEnd() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.removeCharacter(3);

        assertEquals("abc", m.toString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testReplaceCharacterLowBound() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.replaceCharacter(-1, 'a');
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testReplaceCharacterUpperBound() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.replaceCharacter(10, 'g');
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testReplaceCharacterUpperBoundEmpty() {
        ScrambledMessage m = new ScrambledMessage("");

        m.replaceCharacter(1, 'y');
    }

    //Test replacing a character at the beginning of the message
    @Test
    public void testReplaceCharacterAtBeginning() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.replaceCharacter(0, 'x');

        assertEquals("xbcd", m.toString());
    }

    //Test replacing a character in the middle of the message
    @Test
    public void testReplaceCharacterInMiddle() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.replaceCharacter(2, 'e');

        assertEquals("abed", m.toString());
    }

    //Test replacing a character at the end of the message
    @Test
    public void testReplaceCharacterAtEnd() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.replaceCharacter(3, '6');

        assertEquals("abc6", m.toString());
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterLowBound1() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.swapCharacters(-1, 4);
    }

    //Test the lower boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterLowBound2() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.swapCharacters(2, -4);
    }

    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterUpperBound1() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.swapCharacters(10, 2);
    }


    //Test the upper boundary of the exception handling
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterUpperBound2() {
        ScrambledMessage m = new ScrambledMessage("5#@0safa");

        m.swapCharacters(4, 10);
    }

    //Test the upper boundary of the exception handling empty message
    @Test (expected = IndexOutOfBoundsException.class)
    public void testSwapCharacterUpperBoundEmpty() {
        ScrambledMessage m = new ScrambledMessage("");

        m.swapCharacters(1, 0);
    }

    //Test swapping a character at the beginning of the message
    @Test
    public void testSwapCharacterAtBeginning() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.swapCharacters(0, 2);

        assertEquals("cbad", m.toString());
    }

    //Test swapping a character in the middle of the message
    @Test
    public void testSwapCharacterInMiddle() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.swapCharacters(2, 1);

        assertEquals("acbd", m.toString());
    }

    //Test swapping a character at the end of the message
    @Test
    public void testSwapCharacterAtEnd() {
        ScrambledMessage m = new ScrambledMessage("abcd");
        m.swapCharacters(0, 3);

        assertEquals("dbca", m.toString());
    }

    //Test that the length method returns zero when there are no elements
    @Test
    public void testLengthZero() {
        ScrambledMessage m = new ScrambledMessage();

        assertEquals(0, m.length());
    }

    //Test that the length method behaves correctly in a normal situation
    @Test
    public void testLengthNonzero() {
        ScrambledMessage m = new ScrambledMessage("alsf");

        assertEquals(4, m.length());
    }

    //Test the getString method with an empty string
    @Test
    public void testGetEmptyString() {
        ScrambledMessage m = new ScrambledMessage("");

        assertEquals("", m.toString());
    }

    //Test the getString method with an full string
    @Test
    public void testGetString() {
        ScrambledMessage m = new ScrambledMessage("gahn2");

        assertEquals("gahn2", m.toString());
    }

    //Test the getChar method with an empty string
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetCharEmptyString() {
        ScrambledMessage m = new ScrambledMessage("");

        m.getCharacter(0);
    }

    //Test the getChar method with a too low index
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetCharLowBound() {
        ScrambledMessage m = new ScrambledMessage("daslfj");

        m.getCharacter(-1);
    }

    //Test the getChar method with a too high index
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetCharHighBound() {
        ScrambledMessage m = new ScrambledMessage("daslfj");

        m.getCharacter(7);
    }

    //Test the getChar method on the first character
    @Test
    public void testGetFirstChar() {
        ScrambledMessage m = new ScrambledMessage("gahn2");

        assertTrue(m.getCharacter(0).equals('g'));
    }

    //Test the getChar method on the last character
    @Test
    public void testGetLastChar() {
        ScrambledMessage m = new ScrambledMessage("gahn2");

        assertTrue(m.getCharacter(4).equals('2'));
    }

    //Test the getChar method on one of the middle characters
    @Test
    public void testGetMiddleChar() {
        ScrambledMessage m = new ScrambledMessage("gahn2");

        assertTrue(m.getCharacter(2).equals('h'));
    }

    //Test the exportChanges() method when no changes have been made
    @Test
    public void testExportChangesWithNoChanges(){
        ScrambledMessage m = new ScrambledMessage("ytyj#jadf");
        assertEquals(new LinkedList<String>(), m.exportChangeStack());
    }

    //Test the exportChanges() method when lots of changes are made
    @Test
    public void testExportChangesWithSomeChanges(){
        ScrambledMessage m = new ScrambledMessage("abcdefghij%#*356");
        m.swapCharacters(9, 1);
        m.insertCharacter(5, 'r');
        m.replaceCharacter(3, '$');
        m.removeCharacter(0);
        m.insertCharacter(10, '9');

        //Build a LinkedList representing the changes made above
        LinkedList<Modification> expected = new LinkedList<>();
        expected.add(new Modification(ModificationType.DELETION, 9, 'j'));
        expected.add(new Modification(ModificationType.INSERTION, 9));
        expected.add(new Modification(ModificationType.DELETION, 1, 'b'));
        expected.add(new Modification(ModificationType.INSERTION, 1));
        expected.add(new Modification(ModificationType.INSERTION, 5));
        expected.add(new Modification(ModificationType.DELETION, 3, 'd'));
        expected.add(new Modification(ModificationType.INSERTION, 3));
        expected.add(new Modification(ModificationType.DELETION, 0, 'a'));
        expected.add(new Modification(ModificationType.INSERTION, 10));

        //Convert expected to a LinkedList of Strings
        LinkedList<String> expectedStrings = new LinkedList<>();
        for (int i = 0; i < expected.size(); i++){
            expectedStrings.add(expected.get(i).toString());
        }

        //Export the generated LinkedList from the message
        LinkedList<String> export = m.exportChangeStack();

        //Compare the generated one to the expected one
        assertEquals(expectedStrings, export);
    }

    //Test the exception throw from trying to parse invalid strings
    @Test(expected=IllegalArgumentException.class)
    public void testSetChangeStackWithInvalidChange(){
        ScrambledMessage m = new ScrambledMessage("");

        LinkedList<String> fakeChanges = new LinkedList<>();
        fakeChanges.add("DELETION 0 j");
        fakeChanges.add("INSERTION 0");

        //The bad change
        fakeChanges.add("INSERTIOsadflaslfd");

        //Throws the exception
        m.setChangeStackFromStrings(fakeChanges);
    }

    //Test the exception throw from trying to de-scramble invalid data
    @Test(expected=IllegalArgumentException.class)
    public void testDeScrambleWithInvalidChange(){
        ScrambledMessage m = new ScrambledMessage("");

        LinkedList<String> fakeChanges = new LinkedList<>();
        fakeChanges.add("DELETION 0 j");
        fakeChanges.add("INSERTION 0");

        //The bad change
        fakeChanges.add("INSERTION 0");

        //No exception is thrown here
        m.setChangeStackFromStrings(fakeChanges);

        //Throws the exception
        m.getDeScrambled();
    }

    //Test the lack of an exception throw from trying to parse valid
    //strings that are invalid changes
    @Test
    public void testParseWithInvalidChange() {
        ScrambledMessage m = new ScrambledMessage("");

        LinkedList<String> fakeChanges = new LinkedList<>();
        fakeChanges.add("DELETION 0 j");
        fakeChanges.add("INSERTION 0");

        //The bad change
        fakeChanges.add("INSERTION 0");

        //No exception is thrown here
        m.setChangeStackFromStrings(fakeChanges);
    }

    //Tests the behavior of the string parser and the de-scrambler with
    //an empty changelist
    @Test
    public void testParseNoChanges(){
        ScrambledMessage m = new ScrambledMessage("asdfdsanlj");

        LinkedList<String> fakeChanges = new LinkedList<>();
        m.setChangeStackFromStrings(fakeChanges);
        assertEquals("asdfdsanlj", m.getDeScrambled());
    }

    //Test the getDeScrambled() method with valid data
    @Test
    public void testDeScrambleWithLotsOfValidChanges(){
        ScrambledMessage m = new ScrambledMessage("abcdefghij%#*356");
        m.swapCharacters(9, 1);
        m.insertCharacter(5, 'r');
        m.replaceCharacter(3, '$');
        m.removeCharacter(0);
        m.insertCharacter(10, '9');

        assertEquals("abcdefghij%#*356", m.getDeScrambled());
        assertNotEquals("abcdefghij%#*356", m.toString());
    }

    //Test the getDeScrambled() method with no changes made
    @Test
    public void testDeScrambleWithNoChanges(){
        ScrambledMessage m = new ScrambledMessage("abcdefghij%#*356");
        assertEquals("abcdefghij%#*356", m.getDeScrambled());
    }

    //Test the getDeScrambled() method with no changes made and an empty string
    @Test
    public void testDeScrambleWithNoChangesEmptyString(){
        ScrambledMessage m = new ScrambledMessage("");
        assertEquals("", m.getDeScrambled());
    }

    //Test the toString(), length(), and exportChangeStack() methods when
    //ScrambledMessage.characterList and ScrambledMessage.changeStack are both null
    @Test
    public void testToStringLengthAndExportChangesWithNullInstanceVars(){
        ScrambledMessage m = new ScrambledMessage(new LinkedList<>(), null);

        assertEquals("", m.toString());
        assertEquals(0, m.length());
        assertEquals(new LinkedList<String>(), m.exportChangeStack());

        ScrambledMessage m2 = new ScrambledMessage(null, new LinkedList<>());

        assertEquals("", m2.toString());
        assertEquals(0, m2.length());
        assertEquals(new LinkedList<String>(), m2.exportChangeStack());
    }

    //Test the remove() method when
    //ScrambledMessage.characterList and ScrambledMessage.changeStack are both null
    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveWithNullInstanceVars1(){
        ScrambledMessage m = new ScrambledMessage(new LinkedList<>(), null);
        m.removeCharacter(0);
    }
    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveWithNullInstanceVars2(){
        ScrambledMessage m = new ScrambledMessage(null, new LinkedList<>());
        m.removeCharacter(0);
    }

    //Test the getCharacter() method when
    //ScrambledMessage.characterList and ScrambledMessage.changeStack are both null
    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetCharacterWithNullInstanceVars1(){
        ScrambledMessage m = new ScrambledMessage(new LinkedList<>(), null);
        m.getCharacter(0);
    }
    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetCharacterWithNullInstanceVars2(){
        ScrambledMessage m = new ScrambledMessage(null, new LinkedList<>());
        m.getCharacter(0);
    }

    //Test the swapCharacters() method when
    //ScrambledMessage.characterList and ScrambledMessage.changeStack are both null
    @Test(expected=IndexOutOfBoundsException.class)
    public void testSwapCharactersWithNullInstanceVars1(){
        ScrambledMessage m = new ScrambledMessage(new LinkedList<>(), null);
        m.swapCharacters(0, 1);
    }
    @Test(expected=IndexOutOfBoundsException.class)
    public void testSwapCharactersWithNullInstanceVars2(){
        ScrambledMessage m = new ScrambledMessage(null, new LinkedList<>());
        m.swapCharacters(0, 1);
    }

    //Test the insertCharacter() method when
    //ScrambledMessage.characterList and ScrambledMessage.changeStack are both null
    @Test
    public void testInsertCharacterWithNullInstanceVars(){
        ScrambledMessage m = new ScrambledMessage(new LinkedList<>(), null);
        m.insertCharacter(0, 'a');
    }

    //Test the replaceCharacter() method when
    //ScrambledMessage.characterList and ScrambledMessage.changeStack are both null
    @Test(expected=IndexOutOfBoundsException.class)
    public void testReplaceCharacterWithNullInstanceVars1(){
        ScrambledMessage m = new ScrambledMessage(new LinkedList<>(), null);
        m.replaceCharacter(0, 'a');
    }
    @Test(expected=IndexOutOfBoundsException.class)
    public void testReplaceCharacterWithNullInstanceVars2(){
        ScrambledMessage m = new ScrambledMessage(null, new LinkedList<>());
        m.replaceCharacter(0, 'a');
    }

    //Test the exception throw for insertCharacter(null)
    @Test(expected=IllegalArgumentException.class)
    public void testInsertNullCharacter(){
        ScrambledMessage m = new ScrambledMessage();
        m.insertCharacter(0, null);
    }

    //Test the exception throw for replaceCharacter(X, null)
    @Test(expected=IllegalArgumentException.class)
    public void testReplaceNullCharacter(){
        ScrambledMessage m = new ScrambledMessage("abd");
        m.replaceCharacter(0, null);
    }

    //Test Modification's toString() method for an insert modification
    @Test
    public void testToStringForModificationInsert(){
        Modification m
                = new Modification(ModificationType.INSERTION, 4);

        assertEquals("INSERTION 4", m.toString());
    }

    //Test Modification's toString() method for a deletion modification
    @Test
    public void testToStringForModificationDelete(){
        Modification m
                = new Modification(ModificationType.DELETION, 7, 'a');

        assertEquals("DELETION 7 a", m.toString());
    }

    //Confirm the exception throw for Modification.parseFromString(null)
    @Test(expected=IllegalArgumentException.class)
    public void testModificationParseWithNullString(){
        Modification m = new Modification(null);
    }

    //Confirm the exception throw for Modification.parseFromString("")
    @Test(expected=IllegalArgumentException.class)
    public void testModificationParseWithEmptyString(){
        Modification m = new Modification("");
    }

    //Confirm the exception throw for
    //Modification.parseFromString("pizza")
    @Test(expected=IllegalArgumentException.class)
    public void testModificationParseWithGibberish(){
        Modification m = new Modification("ajlkfsajldf;$32%");
    }

    //Confirm the exception throw for
    //Modification.parseFromString("piz za")
    @Test(expected=IllegalArgumentException.class)
    public void testModificationParseWithGibberishOneSpace(){
        Modification m = new Modification("ajlkfsa jldf;$32%");
    }

    //Confirm the exception throw for
    //Modification.parseFromString("pi z za")
    @Test(expected=IllegalArgumentException.class)
    public void testModificationParseWithGibberishTwoSpaces(){
        Modification m = new Modification("ajlkfs ajldf; $32%");
    }

    //Test Modification's ability to parse a string in the format:
    //"INSERTION N X"
    @Test
    public void testModificationParseInsertion(){
        Modification m = new Modification("INSERTION 3 &");
    }

    //Test Modification's ability to parse a string in the format:
    //"DELETION N"
    @Test
    public void testModificationParseDeletion(){
        Modification m = new Modification("DELETION 8 %");
    }

    //Test Modification's ability to parse output from its toString()
    //for an insertion
    @Test
    public void testModificationParseToStringInsertion(){
        Modification oldM
                = new Modification(ModificationType.INSERTION, 9);

        Modification newM = new Modification(oldM.toString());

        assertEquals(ModificationType.INSERTION, newM.getType());
        assertEquals(9, newM.getLocation());
    }

    //Test Modification's ability to parse output from its toString()
    //for a deletion
    @Test
    public void testModificationParseToStringDeletion(){
        Modification oldM
                = new Modification(ModificationType.DELETION, 11, '^');

        Modification newM = new Modification(oldM.toString());

        assertEquals(ModificationType.DELETION, newM.getType());
        assertEquals(11, newM.getLocation());
        assertEquals(new Character('^'), newM.getCharacter());
    }
}