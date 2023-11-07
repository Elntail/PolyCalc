import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
* A class with unit tests for a MyList list implementation.
* It tests the MyArrayList class but is designed to be able to
* be extended for testing other MyList implementations.
* For that, subclasses need only override the createEmptyLongList() 
* method.
*
* The tests try to test all methods and corner cases.
* We recommend you tackle them (i.e. try to make them pass) in the 
* order they appear in this file.
*
* @author  Rui Meireles
* @version 1.2
*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyArrayListTest extends AMyCollectionTest{
    
    /**
     * Overrides collection creation factory method.
     * 
     * @return an empty list of long integers.
     */
    @Override
    protected MyCollection<Long> createEmptyLongCollection(){
        return createEmptyLongList();
    }
    
    /**
     * A factory method to create an empty list of integers using the 
     * implementation of MyList we're trying to test.
     * 
     * @return an empty list of long integers.
     */
    protected MyList<Long> createEmptyLongList(){
        return new MyArrayList<Long>();
    }

    /**
     * Tests whether adding nodes at the tail of the list is reflected on the list's size.
     */
    @Test
    public void testList1AddTailAndSize(){
        MyList<Long> list = createEmptyLongList(); // new empty list
        assertEquals("Initial list size not zero", 0, list.size());

        for (long i=1; i <= NINTS; i++){ // add an element at a time, and check list size increases
            list.add(new Long(i));
            assertEquals("List size doesn't match number of added elements", i, list.size());
        }
    }

    /**
     * Tests adding at the tail of the list and checking the elements are at the proper indices.
     */
    @Test
    public void testList2AddTailAndIndexOf(){
        MyList<Long> list = createEmptyLongList();; // new empty list
        Long one = new Long(1);
        assertEquals(-1, list.indexOf(one)); // element that that doesn't exist

        for (long i=0; i < NINTS; i++){ // add an element at a time, and check that it's in the right place
            list.add(new Long(i));
            assertEquals(i, list.indexOf(i));
        }

        for (long i=0; i < NINTS; i++){ // are they still in the right place?
            assertEquals(i, list.indexOf(i)); 
        }        
        assertTrue(list.indexOf((long)NINTS+2) == -1);  // element that doesn't exist
        list.add(one); // repeat element
        assertEquals(1, list.indexOf(one));  // index of first occurrence should be returned
    }

    /**
     * Tests adding at the tail of the list and then retrieving the elements that have been added.
     */
    @Test
    public void testList3AddTailAndGet(){
        MyList<Long> list = createEmptyLongList(); // new empty list

        for (int i=0; i < NINTS; i++){ // add an element at a time, and check that it's there
            long li = i;
            list.add(new Long(li));
            assertEquals(i, (long)list.get(i));
        }

        for (int i=0; i < NINTS; i++){ // are they still there?
            long li = i;
            assertEquals(i, (long)list.get(i));
        }

        // test index boundaries
        String fmsg = "Invalid index to get() should throw IndexOutOfBoundsException";
        try{
            list.get(-1); // invalid index (too small)
            fail(fmsg);
        } catch(IndexOutOfBoundsException e){}

        try{
            list.get(NINTS); // invalid index (too large)
            fail(fmsg);
        } catch(IndexOutOfBoundsException e){}
    }


    /**
     * Checks list contains integers from low+offset to high+offset, in order, at indices low to high.
     * @param list The list we're checking.
     * @param low The smallest index we're checking.
     * @param high The largest index we're checking.
     * @param offset The values' offset relative to the index they're stored at. 
     */
    private static void checkListContents(MyList<Long> list, int low, int high, int offset){
        // (hint) debugging techniques for when things go wrong:
        //    - set up a breakpoint and use the debugger
        //    - print the list's contents

        // check that all elements are there and in the proper order
        for (int i=low; i <= high; i++){
            final long val = i + offset;
            assertEquals(i, list.indexOf(val));
            assertEquals(val, (long)list.get(i));
        }        
    }

    /**
     * A helper to avoid code duplication. 
     * Removes item at index idx (expected value val)
     * from a list that is supposed to contain integers
     * 0 through the list's size -1.
     * 
     * @param list the list to remove from.
     * @param idx the index of the item that should be removed.
     * @param vale the expected value of the item to be removed.
     */
    private void testRemoveSingle(MyList<Long> list, int idx, int val){
        int n = list.size();
        assertEquals(50, (long)list.remove(idx)); // element at index 50 is no longer
        assertEquals(n-1, list.size()); // list now has length n- 1        
        checkListContents(list, 0, idx-1, 0); // check contents
        checkListContents(list, idx, n-2, 1); // check contents
    }

    /**
     * Tests element removal.
     * Test assumes add() (tail version), size(), get(), and indexOf() are working properly.
     */
    @Test
    public void testList4Remove(){
        MyList<Long> list = createEmptyLongList(); // new empty list

        // test removing from the tail
        this.addNints(list, NINTS);
        for (int i=NINTS-1; i >= 0; i--){
            assertEquals(i, (long)list.remove(i));
            assertEquals(i, list.size()); // list size should decrease
            checkListContents(list, 0, i-1, 0); // is everything else ok?
        }

        // test removing from the head
        this.addNints(list, NINTS);
        for (int i=0; i < NINTS; i++){
            assertEquals(i, (long)list.remove(0));
            int nleft = NINTS-i-1;
            assertEquals(nleft, list.size()); // list size should decrease
            checkListContents(list, 0, nleft-1, i+1); // is everything else ok?
        }

        // test removing from the middle
        this.addNints(list, NINTS);
        testRemoveSingle(list, 50, 50);
        int nleft = NINTS - 1;

        // test index boundaries
        String fmsg = "Invalid index to remove() should throw IndexOutOfBoundsException";
        try{
            list.remove(-1); // invalid index (too small)
            fail(fmsg);
        } catch(IndexOutOfBoundsException e){}

        try{
            list.remove(nleft); // invalid index (too large)
            fail(fmsg);
        } catch(IndexOutOfBoundsException e){}

        // random removal until list becomes empty
        for(int i=nleft; i > 0; i--){
            int rndIdx = (int) (Math.random()*i);
            list.remove(rndIdx);
            assertEquals(i-1, list.size());
        }   
    }
    
    /**
     * Tests element removal on special case where the array is at capacity and the element 
     * being removed is the one at the list's tail.
     * Test assumes add() (tail version), size(), get(), and indexOf() are working properly.
     */
    @Test
    public void test4bRemoveAtCapacity(){ 
        final int acap = 16; // array capacity
        MyList<Long> list = new MyArrayList(acap); // new empty list using array of specified capacity

        this.addNints(list, acap);
        assertEquals(acap, list.size()); // list size should be equal to array capacity
        final int lastIdx = acap-1;
        assertEquals(new Long(lastIdx), list.remove(lastIdx));
        checkListContents(list, 0, lastIdx-1, 0); // is everything left ok?
        
        // just in case the capacity argument isn't being respected, kind of "brute-force" search for it
        final int maxCap = 1000000; // one million dollars!
        list = new MyArrayList(); // new empty array list of default capacity
        for (int i=0; i < maxCap; i++){
            final Long li = new Long(i);
            assertEquals(true, list.add(new Long(li)));
            assertEquals(li, list.remove(i));
            list.add(li);
        }
    }

    /**
     * Tests element addition (indexed version).
     * Test assumes size(), get(), indexOf(), and remove() are working properly.
     */
    @Test
    public void testList5AddIndexed(){
        MyList<Long> list = createEmptyLongList(); // new empty list

        // test adding at the tail
        for (int i=0; i < NINTS; i++){

            list.add(i, new Long(i)); // tail add
            assertEquals(i+1, list.size()); // list size should increase
            checkListContents(list, 0, i, 0); // everything in its right place?
        }

        // test adding at the head
        list = createEmptyLongList(); // hard reset
        for (int i=0; i < NINTS; i++){
            final int val = NINTS-1-i;
            list.add(0, new Long(val)); // head add
            assertEquals(i+1, list.size()); // list size should increase
            checkListContents(list, 0, i, val); // everything in its right place?
        }

        // test adding in the middle
        testRemoveSingle(list, 50, 50); // remove element at index 50
        list.add(50, new Long(50)); // add it back where it was originally
        assertEquals(NINTS, list.size()); // list noe has length NINTS again
        checkListContents(list, 0, NINTS-1, 0); // check contents

        // test adding at odd indices
        list = createEmptyLongList(); // hard reset
        for (int i=0; i < NINTS; i+=2) // add all the evens
            list.add(i/2, new Long(i)); // tail add
        for (int i=1; i < NINTS; i+=2){ // add all the odds
            list.add(i, new Long(i)); // inserts in the middle
            checkListContents(list, 0, i, 0); // everything in its right place?
        }

        // test random-index insertion
        list = createEmptyLongList(); // hard reset
        for (int i=0; i < NINTS; i++){
            final int rndIdx = (int) (Math.random()*i);
            final long li = i;
            list.add(rndIdx, li);
            assertEquals(i+1, list.size());
        }
        int[] carr = new int[NINTS]; // counts array
        for (int i=0; i < NINTS; i++){ // how many times does each elem appear on the list?
            int elem = (int) list.get(i).longValue();
            carr[elem]++; 
        }
        for (int c : carr) assertEquals(1, c); // each element must appear exactly once

        // test index boundaries
        String fmsg = "Invalid index to add() should throw IndexOutOfBoundsException";
        long zero = 0;
        try{
            list.add(-1, zero); // invalid index (too small)
            fail(fmsg);
        } catch(IndexOutOfBoundsException e){}

        try{
            list.add(NINTS+1, zero); // invalid index (too large)
            fail(fmsg);
        } catch(IndexOutOfBoundsException e){}
    }

    /**
     * Tests the list's toString() to see if it conforms with the specification.
     */
    @Test
    public void testList6ToString(){
        MyList<Long> list = createEmptyLongList(); // new empty list
        this.addNints(list, NINTS);
        String listStr = list.toString();

        // create a reference string "{elem1, elem2, ..., elemn}"
        String refStr = "{";
        for (int i = 0; i < NINTS-1; i++)
            refStr += i + ", ";
        refStr += NINTS-1 + "}";        

        assertEquals(refStr, listStr); // compare the two strings
    }

  
}