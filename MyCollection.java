/**
 * An interface specifying the operations that should be supported
 * by a variable-size collection abstract data type.
 *
 * @author Rui Meireles
 * @version 1.1
 */
public interface MyCollection<E>{

    /**
     * Returns the number of elements currently in the collection.
     *
     * @return the number of elements in the collection.
     */
    public int size();

    /**
     * Adds an element to the collection.
     *
     * @param elem the element to be added.
     * @return true if the collection changed as a result of this addition; 
     * false otherwise.
     */
    public boolean add(E elem);

    /**
     * Removes an element from the collection.
     *
     * @param elem the element to be removed from the collection.
     * @return true if the collection changed as a result of this addition; 
     * false otherwise.
     */
    public boolean remove(E elem);

    /**
     * Checks whether the element passed as an argument is present in the collection.
     *
     * @param elem the element whose presence is to be tested.
     * @return true if the element is present in the collection; false otherwise.
     */
    public boolean contains(E elem);
    

    /**
     * Returns an iterator that lets us traverse the collection.
     *
     * @return an iterator object associated with the collection.
     */
    public MyIterator<E> iterator();
}
