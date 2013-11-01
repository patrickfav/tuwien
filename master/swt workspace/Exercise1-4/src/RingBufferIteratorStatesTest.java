

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 1-3
 * 
 */

public class RingBufferIteratorStatesTest extends TestCase{
	
	private RingBuffer<String> stringRingBuffer0; //2-Element String RingBuffer
	private RingBuffer<String> stringRingBuffer1; //2-Element String RingBuffer
	private RingBuffer<String> stringRingBuffer3; //3-Element String RingBuffer
	private Iterator<String> stringRingBuffer0Iter;
	private Iterator<String> stringRingBuffer1Iter;
	private Iterator<String> stringRingBuffer3Iter;
	
	@Before
	public void setUp() throws Exception {
		/* instantiating */
		stringRingBuffer0 = new RingBuffer<String>(0);
		stringRingBuffer1 = new RingBuffer<String>(1);
		stringRingBuffer3 = new RingBuffer<String>(3);
		
		/* filling */
		stringRingBuffer1.enqueue("item1_1");
		stringRingBuffer3.enqueue("item1_3");
		stringRingBuffer3.enqueue("item2_3");
		stringRingBuffer3.enqueue("item3_3");
		
		stringRingBuffer0Iter = stringRingBuffer0.iterator();
		stringRingBuffer1Iter = stringRingBuffer1.iterator();
		stringRingBuffer3Iter = stringRingBuffer3.iterator();
	}

	@After
	public void tearDown() throws Exception {
		/* dereferencing */
		stringRingBuffer0=null;
		stringRingBuffer1=null;
		stringRingBuffer3=null;
		
		stringRingBuffer0Iter = null;
		stringRingBuffer1Iter = null;
		stringRingBuffer3Iter = null;
	}
	
	
	@Test
	public void testSequence1_IntialToHasNextToHasNextToFullCycle() {
		assertTrue(stringRingBuffer3Iter.hasNext());
		assertEquals("item1_3",stringRingBuffer3Iter.next());
		
		assertTrue(stringRingBuffer3Iter.hasNext());
		assertEquals("item2_3",stringRingBuffer3Iter.next());
		
		assertTrue(stringRingBuffer3Iter.hasNext());
		assertEquals("item3_3",stringRingBuffer3Iter.next());
		
		assertFalse(stringRingBuffer3Iter.hasNext());
	}
	
	@Test
	public void testSequence2_IntialToFullCycle() {
		assertTrue(stringRingBuffer1Iter.hasNext());
		assertEquals("item1_1",stringRingBuffer1Iter.next());

		assertFalse(stringRingBuffer1Iter.hasNext());
	}
	
	@Test
	public void testSequence3_FullCycle() {
		assertFalse(stringRingBuffer0Iter.hasNext());
	}
	
	@Test
	public void testSequence4_IntialToFullCycleToError() {
		assertTrue(stringRingBuffer1Iter.hasNext());
		assertEquals("item1_1",stringRingBuffer1Iter.next());
		
		try {
			stringRingBuffer1Iter.next();
			fail();
		} catch(NoSuchElementException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSequence5_IntialToFullCycleToError() {
		assertTrue(stringRingBuffer1Iter.hasNext());
		assertEquals("item1_1",stringRingBuffer1Iter.next());
		
		try {
			stringRingBuffer1Iter.remove();
			fail();
		} catch(UnsupportedOperationException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSequence6_IntialToError() {
		assertTrue(stringRingBuffer3Iter.hasNext());
		
		try {
			stringRingBuffer3Iter.remove();
			fail();
		} catch(UnsupportedOperationException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSequence7_IntialToHasNextToError() {
		assertTrue(stringRingBuffer3Iter.hasNext());
		assertEquals("item1_3",stringRingBuffer3Iter.next());
		
		try {
			stringRingBuffer3Iter.remove();
			fail();
		} catch(UnsupportedOperationException e) {
			assertTrue(true);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

