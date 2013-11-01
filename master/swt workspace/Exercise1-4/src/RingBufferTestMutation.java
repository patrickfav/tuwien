import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RingBufferTestMutation extends TestCase{
	
	private RingBuffer<Integer> intRingBuffer3;

	@Before
	public void setUp() throws Exception {
		/* instantiating */
		intRingBuffer3 = new RingBuffer<Integer>(3);
	}

	@After
	public void tearDown() throws Exception {
		/* dereferencing */
		intRingBuffer3 = null;

	}

	@Test
	public void testShouldThrowExceptionForOverflowMutationVersion() {

		intRingBuffer3.enqueue(45);
		intRingBuffer3.enqueue(7856);
		intRingBuffer3.enqueue(12);

		try {
			intRingBuffer3.enqueue(8365);
			fail("Should throw RuntimeException, but didn't");
		} catch (RuntimeException e) {
			// success: has thrown exception
			assertEquals("Ring buffer overflow", e.getMessage());
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testShouldThrowExceptionForUnderflowForIntMutationVersion() {
		try {
			intRingBuffer3.dequeue();
			fail();
		} catch(RuntimeException e) {
			//success: has thrown exception
			assertEquals("Ring buffer underflow", e.getMessage());
			assertTrue(true);
		} catch(Exception e) {
			fail();
		}
	}
}
