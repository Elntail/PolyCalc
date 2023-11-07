import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * A class with unit tests for a MyCollection collection implementation.
 * It is abstract, thus designed to be extended for testing concrete implementations.
 * For that, subclasses need only override the createEmptyLongCollection() method.
 *
 * The tests try to test all methods and corner cases.
 * 
 * @author  Rui Meireles
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AMyCollectionTest{

    // # integers to use in testing. should be 10 * 2^i to test array at capacity.
    protected static final int NINTS = 160; 

    /**
     * A factory method to create an empty collection of longs using the MyCollection 
     * implementation we're trying to test.
     * It is to be overridden by concrete subclasses of this class.
     * 
     * @return an empty collection of long integers.
     */
    protected abstract MyCollection<Long> createEmptyLongCollection();

    /**
     * Tests whether adding nodes to the collection is reflected on its size.
     */
    @Test
    public void testCollection1AddAndSize(){
        MyCollection<Long> col = createEmptyLongCollection(); // new empty collection
        assertEquals("Initial set size not zero", 0, col.size());

        for (long i=1; i <= NINTS; i++){ // add an element at a time, and check set size increases
            assertEquals("Collection add returned false when trying to add new element",  true, col.add(i));
            assertEquals("Collection size doesn't match number of added elements", i, col.size());
        }        
    }

    /**
     * Tests whether adding nodes to the collection then means the collection contains them.
     */
    @Test
    public void testCollection2AddAndContains(){
        MyCollection<Long> col = createEmptyLongCollection(); // new empty collection
        
        for (long i=1; i <= NINTS; i++){ // add an element at a time, and check list size increases
            assertEquals("Collection contains element it shouldn't",  false, col.contains(i));
            assertEquals(col.add(i), true);
            assertEquals("Collection does not contains added element", true, col.contains(i));
        }

        for (long i=1; i <= NINTS; i++){ // are all the elements still there?
            assertEquals("Collection does not contains previously added element", true, col.contains(i));
        }
    }

    /**
     * Tests whether adding nodes to the set then means the set contains them.
     * Assumes the following operations work correctly: size(), contains().
     */
    @Test
    public void testCollection3AddAndRemove(){
        MyCollection<Long> col = createEmptyLongCollection(); // new empty collection

        for (long i=1; i <= NINTS; i++){ // add an element at a time, remove and add it back
            assertEquals("Collection remove returns true when called on non-existing element", false, col.remove(i));
            assertEquals(true, col.add(i));
            assertEquals("Collection remove returns false when called on existing element", true, col.remove(i));
            assertEquals("Collection contains previously removed element",  false, col.contains(i));
            assertEquals("Collection remove does not decrease set size", i-1, col.size());
            assertEquals(true, col.add(i));
        }

        for (long i=NINTS; i > 0; i--){ // remove all the elements we added previously
            assertEquals("Collection size incorrect prior to removal", i, col.size());
            assertEquals("Collection does not contains previously added element", true, col.contains(i));
            assertEquals("Set remove returns false when called on existing element", true, col.remove(i));
            assertEquals("Set contains previously removed element", false, col.contains(i));
            assertEquals("Set remove didn't decrease set size",  i-1, col.size());
        }
    }
    
    /**
     * A helper to avoid code duplication. Appends integers 0 through n-1 to list.
     * 
     * @param list the list to append to.
     * @param n the number of integers to append.
     */
    protected void addNints(MyCollection<Long> list, int n){
        for (long i=0; i < n; i++){ // add n elements
            list.add(i);
        }
    }
    
    /**
     * Tests whether iterating over the collection returns each element exactly once.
     */
    @Test
    public void testCollection4Iterator(){
        MyCollection<Long> col = createEmptyLongCollection(); // new empty collection
        this.addNints(col, NINTS); // add some values

        // an iterator should return each element exactly once
        int[] carr = new int[NINTS]; // counts array
        MyIterator<Long> itr = col.iterator();
        for (long i=0; i < NINTS; i++){
            assertTrue(itr.hasNext());
            Long elem = itr.next();
            assertNotEquals(null, elem);
            int idx = (int) elem.longValue();
            carr[idx]++;
        }
        
        assertFalse(itr.hasNext()); // we should be at the end 
        assertEquals(null, itr.next()); // next() should return null when we're at the end 
        
        for (int c : carr) assertEquals(1, c); // each element must have been returned exactly once
    }

}