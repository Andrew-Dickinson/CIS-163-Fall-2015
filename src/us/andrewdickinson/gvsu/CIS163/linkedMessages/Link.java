package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * A link to be used in a linked list
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Link<E> implements Cloneable {
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

    /*******************************************************************
     * Compares this link to another object
     * @param o The other object to compare to
     * @return true if the other object is a link and has
     *         identical data and next
     ******************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link<?> link = (Link<?>) o;

        if (data == null || !data.equals(link.getData()))
            return false;

        return (next != null && next.equals(link.getNext()));
    }

    /*******************************************************************
     * Returns a shallow copy of the data in this link. But a deep copy
     * of the next link
     * @return The copied link
     * @throws CloneNotSupportedException Never
     ******************************************************************/
    @Override
    @SuppressWarnings("unchecked")
    protected Object clone() throws CloneNotSupportedException {
        Link<?> lnk = (Link<?>) super.clone();

        if (next != null)
            lnk.next = (Link) next.clone();

        return lnk;
    }

}
