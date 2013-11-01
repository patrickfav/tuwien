
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 1-2
 * 
 */

public class RingBufferStatesTest {
	
	private RingBuffer<String> stringRingBuffer2; //2-Element String RingBuffer
	private RingBuffer<String> stringRingBuffer3; //3-Element String RingBuffer
	
	@Before
	public void setUp() throws Exception {
		/* instantiating */
		stringRingBuffer2 = new RingBuffer<String>(2);
		stringRingBuffer3 = new RingBuffer<String>(3);
	}

	@After
	public void tearDown() throws Exception {
		/* dereferencing */
		stringRingBuffer2=null;
		stringRingBuffer3=null;
	}
	
	
	@Test
	public void testSequence1_EmptyToFilledToEmpty() {
		assertTrue(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		stringRingBuffer2.enqueue("item1");
		assertEquals(1,stringRingBuffer2.size());
		assertFalse(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		assertEquals("item1",stringRingBuffer2.dequeue());
		
		assertTrue(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
	}
	
	@Test
	public void testSequence2_EmptyToFilledToFilled() {
		assertTrue(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
		
		stringRingBuffer3.enqueue("item1");
		assertEquals(1,stringRingBuffer3.size());
		assertFalse(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
		
		stringRingBuffer3.enqueue("item2");
		assertEquals(2,stringRingBuffer3.size());
		assertFalse(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
	}
	
	@Test
	public void testSequence3_EmptyToFilledToFullToFilled() {
		assertTrue(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		stringRingBuffer2.enqueue("item1");
		assertEquals(1,stringRingBuffer2.size());
		assertFalse(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		stringRingBuffer2.enqueue("item2");
		assertEquals(2,stringRingBuffer2.size());
		assertFalse(stringRingBuffer2.isEmpty());
		assertTrue(stringRingBuffer2.isFull());
		
		assertEquals("item1",stringRingBuffer2.dequeue());
		assertEquals(1,stringRingBuffer2.size());
		assertFalse(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
	}
	
	@Test
	public void testSequence4_EmptyToFilledToFilledToFilled() {
		assertTrue(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
		
		stringRingBuffer3.enqueue("item1");
		assertEquals(1,stringRingBuffer3.size());
		assertFalse(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
		
		stringRingBuffer3.enqueue("item2");
		assertEquals(2,stringRingBuffer3.size());
		assertFalse(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
		
		assertEquals("item1",stringRingBuffer3.dequeue());
		assertEquals(1,stringRingBuffer3.size());
		assertFalse(stringRingBuffer3.isEmpty());
		assertFalse(stringRingBuffer3.isFull());
	}
	
	@Test
	public void testSequence5_EmptyToFilledToFullToError() {
		assertTrue(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		stringRingBuffer2.enqueue("item1");
		assertEquals(1,stringRingBuffer2.size());
		assertFalse(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		stringRingBuffer2.enqueue("item2");
		assertEquals(2,stringRingBuffer2.size());
		assertFalse(stringRingBuffer2.isEmpty());
		assertTrue(stringRingBuffer2.isFull());
		
		try {
			stringRingBuffer2.enqueue("item3");
			fail();
		} catch(RuntimeException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSequence6_EmptyToError() {
		assertTrue(stringRingBuffer2.isEmpty());
		assertFalse(stringRingBuffer2.isFull());
		
		try {
			stringRingBuffer2.dequeue();
			fail();
		} catch(RuntimeException e) {
			assertTrue(true);
		}
	}
}
