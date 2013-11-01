package tdd;

public interface PriorityQueue<E>
{
	/**
	 * Inserts the specified element to this queue. Before inserting the
	 * element, decrements the priorities of all other elements already added to
	 * the queue.
	 * <p>
	 * If the specified new element has the same priority as an element in the
	 * queue, it should be located "before" the element in the queue. I.e.
	 * {@code head()} or any other queue access method returns the element
	 * already in the queue after {@code elem}, the specified element.
	 * <p>
	 * Note, the specified element may be the null value.
	 * 
	 * @param priority
	 *            the priority of the specified element
	 * @param elem
	 *            the element to insert, may be the null value
	 */
	void add(int priority, E elem);

	/**
	 * Returns, but does not remove, the element with the highest priority from
	 * the queue.
	 *
	 * @throws NoElementException
	 *             if there's no element in the queue
	 */
	E head();

	/**
	 * Returns, but does not remove, the element with the highest priority or
	 * returns {@code null} if the queue is empty.
	 */
	E peek();

	/**
	 * Removes and returns the element with the highest priority from the queue.
	 *
	 * @throws NoElementException
	 *             if there's no element in the queue
	 */
	E remove();

	/**
	 * Removes and returns the element with the highest priority or returns
	 * {@code null} if the queue is empty.
	 */
	E poll();

	/**
	 * Returns the number of elements.
	 */
	int size();

	/**
	 * Returns true if this queue contains no elements.
	 */
	boolean isEmpty();
}
