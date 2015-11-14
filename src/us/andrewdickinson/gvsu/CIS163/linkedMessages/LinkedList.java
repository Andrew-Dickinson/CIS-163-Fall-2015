package us.andrewdickinson.gvsu.CIS163.linkedMessages;

/***********************************************************************
 * Contains a linked list representing a list of elements
 * Has ArrayList-like syntax
 * Created by Andrew on 11/9/15
 **********************************************************************/
public class LinkedList<E> {
    /**
     * The top of the linked list
     */
    private Link<E> top;

    public LinkedList(){
        //Initialize top to null
        top = null;
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
     * @return The removed element or null if not in the list
     * @throws IndexOutOfBoundsException if pos >= length() or < 0
     ******************************************************************/
    public E remove(int pos){
        //If pos is invalid, thrown an exception
        if (pos >= this.size() || pos < 0)
            throw new IndexOutOfBoundsException();

        //Handle the beginning case
        if (pos == 0){
            top = top.getNext();
        } else {
            //If pos is somewhere in the middle, remove appropriately
            int out = 0;
            Link<E> cur = top;
            while (cur != null){
                if (out + 1 == pos){
                    //Catch the spot one before the intended remove point
                    Link<E> temp = cur.getNext();
                    cur.setNext(cur.getNext().getNext());
                    return temp.getData();
                }

                out++;
                cur = cur.getNext();
            }
        }

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
     * Adds a new node to the beginning of the linked list
     * @param element The element to add in the new node
     ******************************************************************/
    private void addAtBegin(E element){
        top = new Link<>(element, top);
    }

    /*******************************************************************
     * Adds a new node to the end of the linked list
     * @param element The element to add in the new node
     ******************************************************************/
    private void addAtEnd(E element){
        //Handle a null top
        if (top == null) {
            top = new Link<>(element, null);
        } else {
            //Set cur to the current final link
            Link<E> cur = top;
            while (cur.hasNext()) {
                cur = cur.getNext();
            }

            //Append the new element
            cur.setNext(new Link<>(element, null));
        }
    }

}
