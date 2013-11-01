package tdd;

public class PriorityQueueImpl<E> implements PriorityQueue<E>
{
	private static Integer initialSize = 10;
	private Integer actualSize;
	private Integer[] priorities;
	private Object[] elements;
	
	
	public PriorityQueueImpl() {
		priorities = new Integer[initialSize];
		elements = new Object[initialSize];
		actualSize = initialSize;
		
		//initialize with null
		for(int i=0;i<actualSize;i++) {
			priorities[i] = null;
			elements[i] = null;
		}
	}

	public void add(int priority, E elem) {
		int i;
		//has the array reached max size?
		if(priorities[actualSize-1] != null) {
			//increase array size
			Integer[] temp_priorities = new Integer[actualSize+initialSize];
			Object[] temp_elements = new Object[actualSize+initialSize];
			
			//initialize with null
			for(i=0;i<(actualSize+initialSize);i++) {
				temp_priorities[i] = null;
				temp_elements[i] = null;
			}
			
			//copying the old array to the new bigger ones
			System.arraycopy(priorities,0,temp_priorities,0,actualSize);
			System.arraycopy(elements,0,temp_elements,0,actualSize);
			
			priorities = temp_priorities;
			elements = temp_elements;
			
			actualSize += initialSize;
		}
		
		for(i=0;i<actualSize;i++) {
			//decrement the priorities
			if(priorities[i] != null)
				priorities[i]--;
		}
		
		//find the first free spot and add the elem
		for(i=0;i<actualSize;i++) {
			if(priorities[i] == null) {
				priorities[i] = new Integer(priority);
				elements[i] = elem;
				break;
			}
		}
	}

	public E head() {
		int max = -8096;
		int maxPos =0;
		
		// array is searched from smallest to biggest index ("left to right") so 
		// it will also return the first (with same priority) inserted (=the eldest element with same
		// priority)
		for(int i=0;i<actualSize;i++) {
			if(priorities[i] != null && priorities[i] > max) {
				max = priorities[i];
				maxPos = i;
			}
		}
		
		return (E) elements[maxPos];
	}

	public boolean isEmpty() {

		for(int i=0;i<actualSize;i++) {
			if(priorities[i] != null)
				return false;
		}
		
		return true;
	}

	public E peek() {
		if(isEmpty())
			return null;
		
		return head();
	}

	public E poll() {
		if(isEmpty())
			return null;
		
		return remove();
	}

	public E remove() {
		
		if(isEmpty())
			throw new NoElementException();
		
		int max = -8096;
		int maxPos =0;
		E elem;
		
		for(int i=0;i<actualSize;i++) {
			if(priorities[i] != null && priorities[i] > max) {
				max = priorities[i];
				maxPos = i;
			}
		}
		
		elem = (E) elements[maxPos];
		
		//delete elemen
		priorities[maxPos] = null;
		elements[maxPos] = null;
		
		//if a hole is created in the array: fill it
		if(maxPos != (actualSize-1)) {
			
			Integer[] temp_priorities = priorities;
			Object[] temp_elements = elements;
			
			for(int i=maxPos+1;i<actualSize;i++) {
				priorities[i-1] = temp_priorities[i];
				elements[i-1] = temp_elements[i];
			}
		}
		
		return elem;
	}

	public int size() {
		int size=0;
		
		for(int i=0;i<actualSize;i++) {
			if(priorities[i] != null)
				size++;
		}
		
		return size;
	}
}
