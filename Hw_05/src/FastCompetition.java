//List of Integers
class MyList {
	private int index;
	MyList nextElementIndex;
	static MyList head=null;
	static MyList current;

	MyList() {
		index = -1;
		nextElementIndex = null;
		if (head == null) {
			head = this;
		}
		current = this;
	}

	//Create a list with index
	MyList(int index) {
		if (head == null) {
			this.index = index;
		}
		current = this;
	}

	//Add another Node
	public void add(int index) {
		MyList in1 = new MyList(index);
		current.nextElementIndex = in1;
		current = in1;
	}

	//Get Index of current Node
	int getIndex() {
		return index;
	}

	//Over write the current index
	void overWriteIndex(int index) {
		this.index = index;
	}
}

/**
 * Storage Row in the Hash-Table
 * 
 * @author Lakshmi Ravi
 * @Author Aarti Gorde
 * @param <E>
 */
class MyStorage<E> implements Comparable<E> {
	private E element;
	private MyList indexes;

	public MyStorage() {
		element = null;
		indexes = null;
	}

	public MyStorage(E element, int index) {
		this.element = element;
		indexes = new MyList(index);
	}

	public MyStorage(Object object) {
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public void addIndex(int index) {
		this.indexes.add(index);
	}

	public MyList getallIndex() {
		return indexes;
	}

	public void printAllIndex() {
		MyList currentIndex = indexes;
		while (currentIndex.nextElementIndex != null) {
			System.out.print(currentIndex.getIndex() + " ");
			currentIndex = currentIndex.nextElementIndex;
		}
	}

	public int compareTo(E arg0) {
		return this.element.toString().compareTo(arg0.toString());
	}
}

/**
 * Uses a Hash Table to store the Elements
 * @author Lakshmi Ravi
 *
 * @param <E> Parameter Type E
 */
public class FastCompetition<E> implements Competition<E> {

	private static final int MAX_SIZE = 10000000;
	private MyStorage<E>[] storageTable;
	private static Integer[] INDEXES_HASH_TABLE;
	private static int storageCount = 0;

	@SuppressWarnings("unchecked")
	public FastCompetition(int i) {
		INDEXES_HASH_TABLE = new Integer[100000];
		storageTable = new MyStorage[MAX_SIZE];

	}

	public boolean add(E e) {
		try {
			int hashIndex = e.hashCode();
			hashIndex = Math.abs(hashIndex) % MAX_SIZE;
			storageTable[hashIndex] = new MyStorage<E>(e, storageCount);
			INDEXES_HASH_TABLE[storageCount++]=hashIndex;
			return true;
		} catch (Exception excep) {
			System.err.println("Exception While adding an Element" + excep);
			return false;
		}
	}

	public boolean contains(Object o) {
		E element = (E) o;
		int hashIndex = element.hashCode();
		hashIndex = Math.abs(hashIndex) % MAX_SIZE;
		if (storageTable[hashIndex] != null
				&& storageTable[hashIndex].getElement().equals(element)) {
			return true;
		}
		return false;
	}

	public boolean remove(Object o) {
		E element = (E) o;
		try {
			int hashIndex = Math.abs(element.hashCode()) % MAX_SIZE;

			if (storageTable[hashIndex] != null) {
				storageTable[hashIndex] = null;
				storageCount--;
				return true;
			}
			System.out.println("\n\tNo such Element  " + element + "\n");
			return false;
		} catch (Exception e) {
			System.err.println("Exception while Removing");
			return false;
		}
	}

	public E elementAt(int index) {
		for (int index1 = 0; index1 < storageCount; index1++) {
			if (storageTable[INDEXES_HASH_TABLE[index1]] != null) {
				MyList indexList = storageTable[INDEXES_HASH_TABLE[index1]].getallIndex();
				// Iterate through the indexes and check if the Element is
				// present at that index.
				while (indexList != null) {
					if (indexList.getIndex() == index) {
						return storageTable[INDEXES_HASH_TABLE[index1]].getElement();
					}
					indexList = indexList.nextElementIndex;
				}
			}
		}
		return null;
	}


	public Competition<E> sort() {
		for (int pointer1 = 0; pointer1 < storageCount; pointer1++) {
			MyStorage<E> currentStorageRow = storageTable[INDEXES_HASH_TABLE[pointer1]];
			if (currentStorageRow == null) {
				continue;
			}
			for (int pointer2 = 0; pointer2 < storageCount; pointer2++) {
				MyStorage<E> nextStorageRow = storageTable[INDEXES_HASH_TABLE[pointer2]];
				if (nextStorageRow == null) {
					continue;
				}
				
				if (currentStorageRow.compareTo(nextStorageRow.getElement()) < 0) 
				{
					if (currentStorageRow.getallIndex().getIndex() > nextStorageRow.getallIndex().getIndex()) {
						int tempElement = currentStorageRow.getallIndex().getIndex();
						currentStorageRow.getallIndex().overWriteIndex(nextStorageRow.getallIndex().getIndex());
						nextStorageRow.getallIndex().overWriteIndex(tempElement);
						pointer2=0;
					}	
				}
				
				else if (currentStorageRow.compareTo(nextStorageRow.getElement()) > 0) 
				{
					if (currentStorageRow.getallIndex().getIndex() < nextStorageRow.getallIndex().getIndex()) {
						int tempElement = currentStorageRow.getallIndex().getIndex();
						currentStorageRow.getallIndex().overWriteIndex(nextStorageRow.getallIndex().getIndex());
						nextStorageRow.getallIndex().overWriteIndex(tempElement);	
						pointer2=0;
					}
				}
				}
		}
		return this;
	}

	public int size() {
		return storageCount;
	}

}
