/**
 * An interface specifying the operations that should be supported
 * by a MyCollection iterator.
 *
 * @author Rui Meireles
 * @version 1.0
 */
public interface MyIterator<E>{

    /**
     * Indicates whether there are any unvisited elements in the
     * associated collection.
     * 
     * @return true if there are unvisited elements, false otherwise.
     */
    public boolean hasNext();
    
    /**
     * Returns the next element to be visited in the associated collection.
     * 
     * @return a previously unvisited element from the associated 
     * collection, or null if there are none. 
     */
    public E next();
    
}
