package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * A link to be used in a linked list
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Link<E> {
    /**
     * The data represented by this link in the linked list
     */
    private E data;

    /**
     * The next element in the linked list
     */
    private Link<E> next;

    public Link(E data, Link<E> next) {
        this.next = next;
        this.data = data;
    }

    public Link(E data) {
        this.data = data;
    }

    /*******************************************************************
     * Get the next link in the linked list
     * @return The next link
     ******************************************************************/
    public Link<E> getNext() {
        return next;
    }

    /*******************************************************************
     * Set the next link in the linked list
     * @param next The next link
     ******************************************************************/
    public void setNext(Link<E> next) {
        this.next = next;
    }

    /*******************************************************************
     * Determine if there is another link following this one in the
     * linked list
     * @return True if there is another link, false if not
     ******************************************************************/
    public boolean hasNext(){
        return getNext() != null;
    }

    /*******************************************************************
     * Get the data represented by this link in the linked list
     * @return The data represented
     ******************************************************************/
    public E getData() {
        return data;
    }

    /*******************************************************************
     * Set the data represented by this link in the linked list
     * @param data The data to represent
     ******************************************************************/
    public void setData(E data) {
        this.data = data;
    }
}
