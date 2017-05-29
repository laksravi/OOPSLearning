import java.util.*;

class customIterator implements Iterator{
	private List[] hashSetArray;
	private int visited=0;
	private int currentindex=0;
	private int MAX_INDEX=0;
	private List ObjectList;
	private HashSet myHashSet;
	
	customIterator(HashSet thisHashset, List[] hashSetArray, int maxIndex){
		this.hashSetArray=hashSetArray; 
		myHashSet= thisHashset;
		ObjectList= null;
		MAX_INDEX = maxIndex;
	}
	public boolean hasNext() {
		return !(visited== myHashSet.size());
	}

	public Object next() {
		visited++;
		while(ObjectList == null && currentindex < MAX_INDEX){
			ObjectList = hashSetArray[currentindex++];
		}
		
		Object object = ObjectList;
		ObjectList = ObjectList.nextNode;
		return object;
	}
	
}

class List {
	Object key;
	List nextNode;

	public List(Object k) {
		key = k;
		nextNode = null;
	}

	public boolean insert(Object k) {
		if (key.equals(k)) {
			return false;
		}
		if (nextNode == null) {
			List n = new List(k);
			nextNode = n;
			return true;
		}
		return nextNode.insert(k);
	}
	public boolean contains(Object arg0){
		if(key.equals(arg0)){
			return true;
		}
		//Element is not found!
		if(nextNode== null){
			return false;
		}
		return nextNode.contains(arg0);
	}

	public boolean remove(Object arg0) {
		//Delete that node
		if(nextNode == null)
			return false;
		if(nextNode.key.equals(arg0)){
			this.nextNode= nextNode.nextNode;
			return true;
		}
		return nextNode.remove(arg0);
	}
}

public class HashSetNew extends HashSet {
	private List arrayList[];
	private int maxElements = 100;
	private final int ATMOST_MAX_ELEMENTS=1000000;
	private int elementCount = 0;

	HashSetNew() {
		arrayList = new List[maxElements];
	}

	private void increaseSize(int lowerLimit) {
		int newSize = maxElements * 2;
		while (newSize <= lowerLimit) {
			newSize *= 2;
		}
		//
		List[] newArrayList = new List[newSize];
		for (int i = 0; i < maxElements; i++) {
			newArrayList[i] = arrayList[i];
		}
		maxElements= newSize;
		arrayList = newArrayList;
	}

	@Override
	public boolean add(Object arg0) {
		try {
			int hashcode = Math.abs((arg0.hashCode()% ATMOST_MAX_ELEMENTS));
			// Increase the array size to accommodate more elements
			while (hashcode >= maxElements) {
				increaseSize(hashcode);
			}
			// Get the Linked list to append more elements
			List head = arrayList[hashcode];
			if (head == null) {
				arrayList[hashcode] = new List(arg0);
				elementCount++;
			} else {
				if(head.insert(arg0)){
					elementCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean remove(Object arg0) {
		int hashCode = Math.abs((arg0.hashCode()%ATMOST_MAX_ELEMENTS));
		if(maxElements <= hashCode)
			return false;
		List listhead = arrayList[hashCode];
		if(listhead == null)
			return false;
		if(listhead.key.equals(arg0)){
			arrayList[hashCode]= listhead.nextNode;
			elementCount--;
			return true;
		}
		if(listhead.remove(arg0)){
			elementCount--;
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object arg0) {
		try {
			int hashCode = Math.abs((arg0.hashCode()%ATMOST_MAX_ELEMENTS));
			List head = arrayList[hashCode];
			if (head == null) {
				return false;
			}
			return head.contains(arg0);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void clear() {
		elementCount = 0;
		maxElements = 100;
		arrayList = new List[maxElements];

	}
	
	@Override
	public boolean isEmpty() {
		return elementCount==0;
	}
	
	@Override
	public int size() {
		return elementCount;
	}
	
	@Override
	public Iterator iterator() {
		return new customIterator(this, arrayList, maxElements);
	}
}
