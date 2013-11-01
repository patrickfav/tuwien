

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 0-1: Unit Testing with JUnit
 * 
 */

public class RingBufferTest {
	
	private RingBuffer<String> stringRingBuffer0; //0-Element String RingBuffer
	private RingBuffer<String> stringRingBuffer5; //5-Element String RingBuffer
	private RingBuffer<Integer> intRingBuffer3; //3-Element Int RingBuffer
	
	@Before
	public void setUp() throws Exception {
		/* instantiating */
		stringRingBuffer0 = new RingBuffer<String>(0);
		stringRingBuffer5 = new RingBuffer<String>(5);
		intRingBuffer3 = new RingBuffer<Integer>(3);
		
		/* check ring buffers, if they are empty */
		assertEquals(0,stringRingBuffer0.size());
		assertEquals(true,stringRingBuffer0.isEmpty());
		
		assertEquals(0,stringRingBuffer5.size());
		assertEquals(true,stringRingBuffer5.isEmpty());
		
		assertEquals(0,intRingBuffer3.size());
		assertEquals(true,intRingBuffer3.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		/* dereferencing */
		stringRingBuffer0=null;
		stringRingBuffer5=null;
		intRingBuffer3=null;
	}
	
	/**
	 * Enques until full, then deques and checks all elements.
	 */
	@Test
	public void shouldEnqueAndDequeAllItemsSuccessfullyForString() {
		stringRingBuffer5.enqueue("Item 1");
		stringRingBuffer5.enqueue("Item 2");
		stringRingBuffer5.enqueue("Item 3");
		
		assertEquals(3,stringRingBuffer5.size());
		
		stringRingBuffer5.enqueue("Item 4");
		stringRingBuffer5.enqueue("Item 5");
		
		assertEquals(5,stringRingBuffer5.size());
		
		assertEquals("Item 1", stringRingBuffer5.dequeue());
		assertEquals("Item 2", stringRingBuffer5.dequeue());
		assertEquals("Item 3", stringRingBuffer5.dequeue());
		assertEquals("Item 4", stringRingBuffer5.dequeue());
		assertEquals("Item 5", stringRingBuffer5.dequeue());
		
		assertEquals(0,stringRingBuffer5.size());
		assertEquals(true,stringRingBuffer5.isEmpty());
	}
	
	/**
	 * Enques until full, then deques and checks all elements.
	 * Test for different generic type than String.
	 */
	@Test
	public void shouldEnqueAndDequeAllItemsSuccessfullyForInt() {
		intRingBuffer3.enqueue(1);
		
		assertEquals(1,intRingBuffer3.size());
		
		intRingBuffer3.enqueue(8);
		intRingBuffer3.enqueue(0);
		
		assertEquals(3,intRingBuffer3.size());
		
		assertEquals(new Integer(1), (Integer) intRingBuffer3.dequeue());
		assertEquals(new Integer(8), (Integer) intRingBuffer3.dequeue());
		assertEquals(new Integer(0), (Integer) intRingBuffer3.dequeue());
		
		assertEquals(0,intRingBuffer3.size());
		assertEquals(true,intRingBuffer3.isEmpty());
	}
	
	/**
	 * A more complex case: combined en- and dequing while
	 * constantly checking for size.
	 */
	@Test
	public void combinedEnqueAndDequeSuccessfullForString() {	
		stringRingBuffer5.enqueue("Item 1");
		stringRingBuffer5.enqueue("Item 2");
		stringRingBuffer5.enqueue("Item 3");
		
		assertEquals(3,stringRingBuffer5.size());
		
		assertEquals("Item 1", stringRingBuffer5.dequeue());
		
		assertEquals(2,stringRingBuffer5.size());
		
		stringRingBuffer5.enqueue("Item 4");
		stringRingBuffer5.enqueue("Item 5");
		stringRingBuffer5.enqueue("Item 6");
		
		assertEquals(5,stringRingBuffer5.size());
		
		assertEquals("Item 2", stringRingBuffer5.dequeue());
		assertEquals("Item 3", stringRingBuffer5.dequeue());
		assertEquals("Item 4", stringRingBuffer5.dequeue());
		assertEquals("Item 5", stringRingBuffer5.dequeue());
		assertEquals("Item 6", stringRingBuffer5.dequeue());
		
		assertEquals(0,stringRingBuffer5.size());
		assertEquals(true,stringRingBuffer5.isEmpty());
	}
	
	/**
	 * Tests if overflow exception is thrown.
	 */
	@Test
	public void shoudThrowExceptionForOverflow() {

		intRingBuffer3.enqueue(45);
		intRingBuffer3.enqueue(7856);
		intRingBuffer3.enqueue(12);
		
		try {
			intRingBuffer3.enqueue(8365);
			fail("Should throw RuntimeException, but didn't");
		} catch(RuntimeException e) {
			//success: has thrown exception
			assertTrue(true);
		}
	}
	
	/**
	 * Tests if a zero capacity buffer throws overflow exception when
	 * enqueing.
	 */
	@Test(expected = RuntimeException.class)
	public void shoudThrowExceptionForOverflowWithZeroCapacity() {
		stringRingBuffer0.enqueue("Item 0");
	}
	
	@Test(expected = RuntimeException.class)
	public void shoudThrowExceptionForUnderflowForString() {
		stringRingBuffer5.dequeue();
	}
	
	@Test(expected = RuntimeException.class)
	public void shoudThrowExceptionForUnderflowForInt() {
		intRingBuffer3.dequeue();
	}
	
	/**
	 * Gathers an iterator after adding elements.
	 * Checks if all elements could be retrieved through
	 * the iterator
	 */
	@Test
	public void testIteratorShouldBeSuccessfull() {
		intRingBuffer3.enqueue(8375);
		intRingBuffer3.enqueue(1);
		intRingBuffer3.enqueue(245);
		
		Iterator<Integer> iter = intRingBuffer3.iterator();
		
		//item 1
		assertTrue(iter.hasNext());
		assertEquals(new Integer(8375),iter.next());
		//item 2
		assertTrue(iter.hasNext());
		assertEquals(new Integer(1),iter.next());
		//item 3
		assertTrue(iter.hasNext());
		assertEquals(new Integer(245),iter.next());
		
		assertFalse(iter.hasNext());
	}
	
	/**
	 * Checks if the UnsupportedOperationException is thrown
	 * when calling remove method.
	 */
	@Test
	public void testIteratorForUnsupportedRemoveMethodShouldThrowException() {
		intRingBuffer3.enqueue(8375);
		intRingBuffer3.enqueue(1);
		intRingBuffer3.enqueue(245);
		
		Iterator<Integer> iter = intRingBuffer3.iterator();
		
		try {
			iter.remove();
			fail("Should throw UnsupportedOperationException, but didn't");
		} catch(UnsupportedOperationException e) {
			//success: has thrown exception
			assertTrue(true);
		}
	}
	
	/**
	 * Checks if NoSuchElementException is thrown if 
	 * iterator is out of bounds when calling next().
	 */
	@Test
	public void testIteratorShouldThrowExceptionWhileNext() {
		intRingBuffer3.enqueue(63);
		intRingBuffer3.enqueue(1000);
		intRingBuffer3.enqueue(788);
		
		Iterator<Integer> iter = intRingBuffer3.iterator();
		
		iter.next();
		iter.next();
		iter.next();
		
		try {
			iter.next();
			fail("Should throw NoSuchElementException, but didn't");
		} catch(NoSuchElementException e) {
			//success: has thrown exception
			assertTrue(true);
		}
	}
}
