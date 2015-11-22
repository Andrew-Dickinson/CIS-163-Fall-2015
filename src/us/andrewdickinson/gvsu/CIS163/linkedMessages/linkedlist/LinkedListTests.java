package us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist;

import org.junit.Test;

import static org.junit.Assert.*;

/***********************************************************************
 * Comprehensive testing for the LinkedList class
 * Created by Andrew on 11/13/15.
 **********************************************************************/
public class LinkedListTests {

    //Tests the add method in the case where the list is empty
    @Test
    public void testAddToBeginningOfEmptyTwoArg() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);

        assertEquals(new Integer(4), ll.get(0));
    }

    //Tests the add method in the case where the list has data
    @Test
    public void testAddToEndOfPreviousTwoArg() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(5), ll.get(1));
        assertEquals(new Integer(6), ll.get(2));
    }

    //Tests the add method in the case where the list is empty
    @Test
    public void testAddToBeginningOfEmptyOneArg() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(4);

        assertEquals(new Integer(4), ll.get(0));
    }

    //Tests the add method in the case where the list has data
    @Test
    public void testAddToEndOfPreviousOneArg() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(4);
        ll.add(5);
        ll.add(2, 6);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(5), ll.get(1));
        assertEquals(new Integer(6), ll.get(2));
    }

    //Tests the add method after the LinedList has been cloned
    @Test
    public void testAddToEndAfterClone() throws CloneNotSupportedException {
        LinkedList<Integer> llOld = new LinkedList<>();
        llOld.add(4);
        llOld.add(5);

        LinkedList<Integer> ll = (LinkedList) llOld.clone();
        ll.add(2, 6);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(5), ll.get(1));
        assertEquals(new Integer(6), ll.get(2));
    }

    //Tests the add method's insert functionality
    @Test
    public void testAddToMiddleOfPreviousTwoArg() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.add(1, 9);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(9), ll.get(1));
        assertEquals(new Integer(5), ll.get(2));
        assertEquals(new Integer(6), ll.get(3));
    }

    //Tests the upper bound checking of the add method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testAddToTopOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.add(4, 3);
    }

    //Tests the lower bound checking of the add method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testAddToBottomOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.add(-1, 3);
    }

    //Tests the remove method from the end of the list
    @Test
    public void testRemoveEnd() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.remove(2);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(5), ll.get(1));
        assertEquals(2, ll.size());
    }

    //Tests the remove method on the middle of a list
    @Test
    public void testRemoveFromMiddle() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.remove(1);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(6), ll.get(1));
        assertEquals(2, ll.size());
    }

    //Tests the remove method on the beginning of a list
    @Test
    public void testRemoveFromBeginning() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.remove(0);

        assertEquals(new Integer(5), ll.get(0));
        assertEquals(new Integer(6), ll.get(1));
        assertEquals(2, ll.size());
    }

    //Tests the upper bound checking of the remove method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveUpperOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.remove(3);
    }

    //Tests the lower bound checking of the remove method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testRemoveLowerOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.remove(-1);
    }

    //Tests the set method at the end of the list
    @Test
    public void testSetAtEnd() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.set(2, 8);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(5), ll.get(1));
        assertEquals(new Integer(8), ll.get(2));
    }

    //Tests the set method in the middle of a list
    @Test
    public void testSetInMiddle() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.set(1, 99);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(99), ll.get(1));
        assertEquals(new Integer(6), ll.get(2));
    }

    //Tests the set method at the beginning of a list
    @Test
    public void testSetAtBeginning() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.set(0, 55);

        assertEquals(new Integer(55), ll.get(0));
        assertEquals(new Integer(5), ll.get(1));
        assertEquals(new Integer(6), ll.get(2));
    }

    //Tests the upper bound checking of the set method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testSetUpperOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.set(3, 77);
    }

    //Tests the lower bound checking of the set method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testSetLowerOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.set(-1, 55);
    }

    @Test
    public void testSwap() {
        //TODO: Create these tests
    }


    //Tests the set method at the end of the list
    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetOnEmptyList() {
        LinkedList<Integer> ll = new LinkedList<>();

        assertEquals(new Integer(4), ll.get(0));
    }

    //Tests the get method when the data exists
    @Test
    public void testGet() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);
        ll.set(1, 99);

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(99), ll.get(1));
        assertEquals(new Integer(6), ll.get(2));
    }

    //Tests the upper bound checking of the get method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetUpperOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.get(3);
    }

    //Tests the lower bound checking of the get method
    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetLowerOutOfBounds() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(0, 4);
        ll.add(1, 5);
        ll.add(2, 6);

        //Triggers error
        ll.get(-1);
    }

    //Tests the size() method for all N, 0 through 1024
    @Test
    public void testSizeNObjects() {
        for (int i = 0; i < 1024; i++) {
            LinkedList<Object> ll = new LinkedList<>();

            for (int tmp = 0; tmp < i; tmp++) {
                ll.add(new Object());
            }

            assertEquals(i, ll.size());
        }
    }

    //Tests the remove() method on the first element
    //for all N, 1 through 1024
    @Test
    public void testRemoveNSizeObjects() {
        for (int i = 1; i < 1024; i++) {
            LinkedList<Object> ll = new LinkedList<>();

            for (int tmp = 0; tmp < i; tmp++) {
                ll.add(new Object());
            }

            ll.remove(0);

            assertEquals(i - 1, ll.size());
        }
    }

    //Test the functionality of the reverse() method when the number of
    //elements is even
    @Test
    public void testReverseEven(){
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);

        ll.reverse();

        assertEquals(new Integer(4), ll.get(0));
        assertEquals(new Integer(3), ll.get(1));
        assertEquals(new Integer(2), ll.get(2));
        assertEquals(new Integer(1), ll.get(3));
    }

    //Test the functionality of the reverse() method when the number of
    //elements is odd
    @Test
    public void testReverseOdd(){
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);

        ll.reverse();

        assertEquals(new Integer(5), ll.get(0));
        assertEquals(new Integer(4), ll.get(1));
        assertEquals(new Integer(3), ll.get(2));
        assertEquals(new Integer(2), ll.get(3));
        assertEquals(new Integer(1), ll.get(4));
    }

    //Test the functionality of the reverse() method with an empty list
    @Test
    public void testReverseEmpty(){
        LinkedList<Integer> ll = new LinkedList<>();
        ll.reverse();

        assertEquals(0, ll.size());
    }

    //Test the toString() output for validity
    @Test
    public void testToString() {
        LinkedList<Integer> ll = new LinkedList<>();
        assertEquals("[]", ll.toString());

        ll.add(5);
        assertEquals("[5]", ll.toString());

        ll.add(6);
        assertEquals("[5, 6]", ll.toString());

        ll.add(8);
        assertEquals("[5, 6, 8]", ll.toString());
    }

    //Test LinkedList.equals(null)
    @Test
    public void testEqualsNull(){
        LinkedList<Object> ll = new LinkedList<>();
        assertNotEquals(ll, null);
    }

    //Test LinkedList.equals(new Object())
    @Test
    public void testEqualsOtherObject(){
        LinkedList<Object> ll = new LinkedList<>();
        assertNotEquals(ll, new Object());
    }

    //Test LinkedList.equals() for two different types of an
    //empty linked list. They should be equal, because all
    //empty lists are equal
    @Test
    public void testEqualsOtherTypeEmpty(){
        LinkedList<String> ll = new LinkedList<>();
        LinkedList<Integer> ll2 = new LinkedList<>();
        assertEquals(ll, ll2);
    }

    //Test LinkedList.equals() for two different types of linked list
    @Test
    public void testEqualsOtherType(){
        LinkedList<String> ll = new LinkedList<>();
        ll.add("a");
        LinkedList<Integer> ll2 = new LinkedList<>();
        ll2.add(5);
        assertNotEquals(ll, ll2);
    }

    //Test Linkedlist.equals() for two non-identical lists of the same type
    @Test
    public void testEqualsFalse(){
        LinkedList<String> ll = new LinkedList<>();
        ll.add("pie");
        ll.add("cake");
        LinkedList<String> ll2 = new LinkedList<>();
        ll2.add("cake");
        ll2.add("pie");
        assertNotEquals(ll, ll2);
    }

    //Test LinkedList.equals() for two identical lists
    @Test
    public void testEqualsTrue(){
        LinkedList<String> ll = new LinkedList<>();
        ll.add("pie");
        ll.add("cake");
        LinkedList<String> ll2 = new LinkedList<>();
        ll2.add("pie");
        ll2.add("cake");
        assertEquals(ll, ll2);
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