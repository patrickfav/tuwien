package tdd;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


import org.junit.Test;

public class PriorityQueueTest
{
	private static class Pair
	{
		public final int fst;
		public final String snd;

		public Pair(int fst, String snd) {
			this.fst = fst;
			this.snd = snd;
		}
	}

	// you may define your own constants here
	private static final String ELEM1 = "ELEM1";
	private static final String ELEM2 = "ELEM2";
	private static final String ELEM3 = "ELEM3";
	private static final String ELEM4 = "ELEM4";
	private static final String ELEM5 = "ELEM5";
	

	private static Pair pair(int priority, String elem) {
		return new Pair(priority, elem);
	}

	/**
	 * Creation Method that fully resembles the constructor of the
	 * PriorityQueueImpl class. At no time create your own object by calling
	 * {@code new PriorityQueueImpl(..)}, use this method instead.
	 * <p>
	 * You may create your own creation method, but use this one to create the
	 * queue, i.e. {@code createQueue()} for an empty queue.
	 */
	private PriorityQueue<String> createQueue(Pair... elems) {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		for (Pair p : elems) {
			q.add(p.fst, p.snd);
		}
		return q;
	}

	@Test
	public void isEmpty_shouldReturnFalseIfQueueContainsElements() {
		assertThat(createQueue(pair(1, ELEM1)).isEmpty(), is(false));
		assertThat(createQueue(pair(1, ELEM1), pair(2, ELEM2)).isEmpty(), is(false));
	}
	
	@Test
	public void head_shouldReturnMiddleElement() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(2, ELEM1);
		q.add(8, ELEM2);
		q.add(5, ELEM3);
		
		assertEquals(ELEM2, q.head());
	}
	
	@Test
	public void peek_shouldReturnNull() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		assertEquals(null, q.peek());
	}
	
	@Test
	public void peek_shouldReturnFirstElem() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(4, ELEM1);
		q.add(3, ELEM2);
		q.add(2, ELEM3);
		
		assertEquals(ELEM1, q.peek());
	}
	
	@Test
	public void size_ShouldReturnCorrect() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(4, ELEM1);
		q.add(3, ELEM2);
		q.add(2, ELEM3);
		
		assertEquals(3, q.size());
	}
	
	@Test
	public void removeNadd_ShouldRemoveAndAddCorrectly() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(7, ELEM1);
		q.add(3, ELEM2);
		q.add(1, ELEM3);
		
		// 6 2 1
		assertEquals(3, q.size());
		assertEquals(ELEM1,q.head());
		assertEquals(ELEM1,q.remove());
		//2 1
		assertEquals(ELEM2,q.head());
		
		q.add(1, ELEM4);
		
		//1 0 1
		assertEquals(ELEM2,q.head());
	}
	
	@Test
	public void removeTillEmpty() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(7, ELEM1);
		q.add(3, ELEM2);
		q.add(1, ELEM3);
		try {
			assertEquals(ELEM1,q.remove());
			assertEquals(ELEM2,q.remove());
			assertEquals(ELEM3,q.remove());
		} catch(NoElementException e) {
			assertTrue(false);
		}
		
		assertTrue(q.isEmpty());
	}
	
	@Test
	public void removeShouldThroughException() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(7, ELEM1);
		assertEquals(ELEM1,q.remove());
		
		try {
			q.remove();
			assertTrue(false);
		} catch(NoElementException e) {
			assertTrue(true);
		}
	}
	
	@Test 
	public void pollShouldRemoveCorrectly() {
		PriorityQueue<String> q = new PriorityQueueImpl<String>();
		q.add(7, ELEM1);
		q.add(3, ELEM2);
		q.add(1, ELEM3);
		
		assertEquals(ELEM1,q.poll());
		assertEquals(ELEM2,q.poll());
		assertEquals(ELEM3,q.poll());
		
		assertTrue(q.isEmpty());
		
		assertEquals(null, q.poll());
	}
}
