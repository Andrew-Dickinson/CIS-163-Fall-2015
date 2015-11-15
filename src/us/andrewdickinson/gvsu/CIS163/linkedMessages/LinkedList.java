package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * Contains a linked list representing a list of elements
 * Has ArrayList-like syntax
 * Created by Andrew on 11/9/15
 **********************************************************************/
public class LinkedList<E> implements Cloneable {
    /**
     * The top of the linked list
     */
    private Link<E> top;

    /**
     * The tail of the linked list
     */
    private Link<E> tail;

    public LinkedList(){
        //Initialize top and tail to null
        top = null;
        tail = null;
    }

    /*******************************************************************
     * Inserts a new node into the linked list
     * @param pos The location to insert to
     * @param element The element to insert
     * @throws IndexOutOfBoundsException if pos > length() or < 0
     ******************************************************************/
    public void add(int pos, E element){
        //If pos is invalid, thrown an exception
        if (pos > this.size() || pos < 0)
            throw new IndexOutOfBoundsException();

        //Handle the beginning/end cases with the appropriate methods
        if (pos == 0){
            addAtBegin(element);
        } else if (pos == this.size()) {
            addAtEnd(element);
        } else {
            //If pos is somewhere in the middle, insert appropriately
            int out = 0;
            Link<E> cur = top;
            while (cur != null){
                if (out + 1 == pos){
                    //Catch the spot one before the intended insert point
                    cur.setNext(new Link<>(element, cur.getNext()));
                }

                out++;
                cur = cur.getNext();
            }
        }
    }

    /*******************************************************************
     * Inserts a new node at the end of the linked list
     * @param element The element to insert
     ******************************************************************/
    public void add(E element){
        addAtEnd(element);
    }

    /*******************************************************************
     * Removes a node from the linked list
     * @param pos The index of the node to remove
     * @return The removed element
     * @throws IndexOutOfBoundsException if pos >= length() or < 0
     ******************************************************************/
    public E remove(int pos){
        //If pos is invalid, thrown an exception
        if (pos >= this.size() || pos < 0)
            throw new IndexOutOfBoundsException();

        //Handle the beginning case
        if (pos == 0){
            //Handle the size() == 1 case
            if (tail == top){
                tail = null;
            }

            //We're removing top, so we have to save it to return it's data
            Link<E> temp = top;

            //Remove the element in top
            top = top.getNext();

            //Return the data we removed
            return temp.getData();
        } else {
            //If pos is somewhere in the middle, remove appropriately

            int out = 0;
            Link<E> cur = top;
            while (cur != null){
                //Catch the spot one before the intended remove point
                if (out + 1 == pos){
                    //Save the Link we're gonna return
                    Link<E> temp = cur.getNext();

                    //Bypass the link, removing it from the list
                    cur.setNext(cur.getNext().getNext());

                    //Properly adjust the tail pointer
                    Link<E> newTail = cur;
                    while (newTail.getNext() != null){
                        newTail = newTail.getNext();
                    }
                    tail = newTail;

                    //Return the data from the link we removed
                    return temp.getData();
                }

                out++;
                cur = cur.getNext();
            }
        }

        //This should never occur
        return null;
    }

    /*******************************************************************
     * Set the element at a position in the linked list
     * @param pos The index of the node to modify
     * @param element The new element for this index
     * @throws IndexOutOfBoundsException if pos >= length() or < 0
     ******************************************************************/
    public void set(int pos, E element){
        //If pos is invalid, thrown an exception
        if (pos >= this.size() || pos < 0)
            throw new IndexOutOfBoundsException();

        int out = 0;
        Link<E> cur = top;
        while (cur != null){
            if (out == pos){
                cur.setData(element);
            }

            out++;
            cur = cur.getNext();
        }
    }

    /*******************************************************************
     * Swaps the elements at the given positions
     * @param pos1 The index of the first element to reference
     * @param pos2 The index of the second element to reference
     * @throws IndexOutOfBoundsException if pos1/pos2 >= length() or < 0
     ******************************************************************/
    public void swap(int pos1, int pos2){
        //If a pos is invalid, throw an exception
        if (pos1 >= this.size() || pos1 < 0 ||
            pos2 >= this.size() || pos2 < 0)
            throw new IndexOutOfBoundsException();

        //Save the element from pos1
        E temp = this.get(pos1);

        //Replace the pos1 element with the pos2 element
        this.set(pos1, this.get(pos2));

        //Replace the pos2 element with the saved pos1 element
        this.set(pos2, temp);

    }

    /*******************************************************************
     * Get the element at the provided index in the linked list
     * @param index The index of the element to get
     * @return The element at the provided location
     * @throws IndexOutOfBoundsException if index >= length() or < 0
     ******************************************************************/
    public E get(int index){
        //If index is invalid, thrown an exception
        if (index >= this.size() || index < 0)
            throw new IndexOutOfBoundsException();

        int out = 0;
        Link<E> cur = top;
        while (cur != null){
            if (out == index){
                return cur.getData();
            }

            out++;
            cur = cur.getNext();
        }

        return null;
    }

    /*******************************************************************
     * Get the number of elements in the linked list
     * @return The number of elements
     ******************************************************************/
    public int size(){
        int out = 0;
        Link<E> cur = top;
        while (cur != null){
            out++;
            cur = cur.getNext();
        }

        return out;
    }

    /*******************************************************************
     * Get a string representation of the linked list
     * @return The string representation of the list
     ******************************************************************/
    @Override
    public String toString(){
        String out = "[";
        Link<E> cur = top;
        while (cur != null){
            out += cur.getData().toString();

            if (cur.hasNext()) out += ", ";

            cur = cur.getNext();
        }

        out += "]";
        return out;
    }

    /*******************************************************************
     * Check if this is equal to another object
     * @param other The object to compare to
     * @return True if other is a LinkedList with identical data
     ******************************************************************/
    public boolean equals(Object other){
        if (other == null || other.getClass() != getClass())
            return false;

        LinkedList<?> linkedList = (LinkedList<?>) other;

        if (linkedList.size() != size())
            return false;

        for (int i = 0; i < size(); i++){
            if (!linkedList.get(i).equals(get(i)))
                return false;
        }

        return true;
    }

    /*******************************************************************
     * Returns a partially deep copy of the list. The Links are deep,
     * but the data objects are shallow :(
     * @return The created copy
     * @throws CloneNotSupportedException Never
     ******************************************************************/
    @Override
    @SuppressWarnings("unchecked")
    protected Object clone() throws CloneNotSupportedException {
        LinkedList<?> ll = (LinkedList<?>) super.clone();
        try {
            ll.top = (Link) top.clone();
            ll.tail = (Link) tail.clone();
        } catch (ClassCastException e){
            throw new CloneNotSupportedException();
        }

        return ll;
    }

    /*******************************************************************
     * Adds a new node to the beginning of the linked list
     * @param element The element to add in the new node
     ******************************************************************/
    private void addAtBegin(E element){
        top = new Link<>(element, top);

        //This is the case where the list is empty
        if (tail == null){
            tail = top;
        }
    }

    /*******************************************************************
     * Adds a new node to the end of the linked list
     * @param element The element to add in the new node
     ******************************************************************/
    private void addAtEnd(E element){
        //Handle a null top
        if (top == null) {
            top = new Link<>(element, null);
            tail = top;
        } else {
            //Append the new element
            tail.setNext(new Link<>(element, null));
            tail = tail.getNext();
        }
    }
}
