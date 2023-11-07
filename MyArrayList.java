/**
 * A class that implements the MyList<E> interface using an array.
 *
 * @author Rommel Lantajo II
 * @version 1.0
 */
public class MyArrayList<E> implements MyList<E>{
    /* the array that holds the list's elements */
    private E[] earray;
    /* Keeps track of the list's size */
    private int size = 0;

    /* add any other relevant instance fields here */

    /**
     * Constructor used to create an empty list using 
     * an array with the specified capacity.
     * 
     * @param capacity initial array capacity.
     * @return an empty list.
     */
    public MyArrayList(int capacity){
        this.earray = (E[]) new Object[capacity];
    }

    /**
     * Constructor used to create an empty list using 
     * an array with the default capacity of 10.
     * 
     * @param capacity initial array capacity.
     * @return an empty list.
     */
    public MyArrayList(){
        this(10); // delegate to other constructor variant
    }

    /**
     * Returns the number of elements on the list.
     *
     * @return the number of elements on the list.
     */
    public int size(){
        return this.size; // immediately available
    }

    /**
     * Appends a new element to the end of the list.
     *
     * @param elem the element to add to the list.
     * @return always returns true.
     */
    public boolean add(E elem){
        add(this.size, elem); // delegate to general add
        return true;        
    }

    /**
     * Adds a new element to the list at the index specified as an argument.
     *
     * @param  idx  the index at which the new element should be found once it
     *              has been added to the list.
     * @param elem the element to add to the list.
     * @throws IndexOutOfBoundsException if index not in range [0,size()].
     */
    public void add(int idx, E elem) throws IndexOutOfBoundsException{

        // check whether idx is even valid
        if (idx < 0 || idx > this.size) 
            throw new IndexOutOfBoundsException("invalid index to add()");

        // make sure have enough space in array
        if(this.size == this.earray.length){
            E[] newEarray = (E[]) new Object[this.earray.length*2]; // create larger array
            for (int i=0; i < this.earray.length; i++) // copy contents over
                newEarray[i] = this.earray[i];
            this.earray = newEarray; // replace reference
        }

        // shift elements to the right to create space
        // must do it from the end of the list to the start, to prevent undue overwriting
        for(int i=this.size; i > idx; i--)
            this.earray[i] = this.earray[i-1];

        this.earray[idx] = elem; // write the new elem in the correct index
        this.size++; // now we have a larger list!
    }

    /**
     * Removes and returns the list element located at the index specified as an argument.
     *
     * @param idx the index at which the element to be removed resides.
     * @return the element removed.
     * @throws IndexOutOfBoundsException if index not in range [0,size()-1]
     */
    public E remove(int idx) throws IndexOutOfBoundsException{

        // check whether idx is even valid
        if (idx < 0 || idx >= this.size) 
            throw new IndexOutOfBoundsException("invalid index to remove()");

        E rem = this.earray[idx]; // save removed element 

        // shift elements at indices larger than the removed one left by one
        for (int i=idx; i < this.size-1; i++)
            this.earray[i] = this.earray[i+1];

        this.size--; // one fewer element
        this.earray[size] = null; // make sure there 

        return rem; // return removed element
    }

    /**
     * Returns the list element stored at the index specified as an argument.
     *
     * @param idx the index of the element to be retrieved.
     * @return the element at the specified index.
     * @throws IndexOutOfBoundsException if index not in range [0,size()-1].
     * 
     */
    public E get(int idx) throws IndexOutOfBoundsException{
        // check whether idx is even valid
        if (idx < 0 || idx >= this.size) 
            throw new IndexOutOfBoundsException("invalid index to get()");

        return this.earray[idx]; // this works because position in array matches position in list
    }

    /**
     * Returns the index at which the element passed as an argument can be found
     * on the list. Equality is determined by the equals(Object o) method,
     * defined in the Object class, and potentially overridden by the element's class.
     * If elem appears multiple times on the list, the lowest index at which it can
     * be found is returned. If the element is not present in the list, -1 is returned.
     *
     * @param elem the element we're trying to find.
     * @return the index of the first ocurrence of elemement elem, or -1 if not found.
     */
    public int indexOf(E elem){

        // traverse array looking for a match
        for (int i = 0; i < this.size; i++)
            if (this.earray[i].equals(elem))
                return i; // found the right index

        return -1; // default if not found
    }

    /**
     * Returns a textual representation of the list elements in the format "{elem1, elem2, .., elemn}".
     * E.g. A list with elements 1, 2 and 3 should return "{1, 2, 3}".
     * 
     * @return the string representation of the list.
     */
    public String toString(){
        String s = "{";
        final int maxIdx = this.size-1;
        for(int i=0; i < maxIdx; i++){
            s += this.earray[i] + ", ";
        }
        // add last item without a comma
        if(this.size > 0) s += this.earray[maxIdx];

        return s + "}";
    }

    // MyCollection methods from here on out

    /**
     * Removes an element from the collection.
     *
     * @param elem the element to be removed from the collection.
     * @return true if the collection changed as a result of this addition, false otherwise.
     */
    public boolean remove(E elem){

        if(indexOf(elem) == -1) return false;
        remove(indexOf(elem));
        return true;
    }

    /**
     * Checks whether the element passed as an argument is present in the collection.
     *
     * @param elem the element whose presence is to be tested.
     * @return true if the element is present in the collection; false otherwise.
     */
    public boolean contains(E elem){
        if(indexOf(elem) == -1) return false;       
        return true;
    }

    /**
     * Returns an iterator that lets traverse the collection.
     *
     * @return an iterator object associated with the collection.
     */
    public MyIterator<E> iterator(){
        return new MyArrayListItr();
    }

    public class MyArrayListItr implements MyIterator<E>
    {

        private int nextIdx = 0; // the next idx to visit

        public MyArrayListItr(){ 
        }

        /**
         * Indicates whether there are any unvisited elements in the
         * associated collection.
         * 
         * @return true if there are unvisited elements, false otherwise.
         */
        public boolean hasNext(){
            return this.nextIdx < MyArrayList.this.size();
        }

        /**
         * Returns the next element to be visited in the associated collection.
         * 
         * @return a previously unvisited element from the associated 
         * collection, or null if there are none. 
         */
        public E next(){
            E retval = null; // default value
            if (this.hasNext()) retval = MyArrayList.this.get(nextIdx++);
            return retval;

        }
    }

}