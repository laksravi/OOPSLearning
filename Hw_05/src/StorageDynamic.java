class Node<T1,T2> {
	private T1 primaryvalue;
	private T2 secondaryValue;
	private Node<T1,T2> nextElement;

	Node() {
		primaryvalue = null;
		secondaryValue=null;
		nextElement = null;
	}

	Node(T1 value) {
		this.primaryvalue = value;
		this.secondaryValue=null;
	}

	Node(T1 value, T2 elem) {
		this.primaryvalue = value;
		this.secondaryValue = elem;
	}

	public void setNextElement(Node<T1,T2> nextElement) {
		this.nextElement = nextElement;
	}

	public T1 getValue() {
		return primaryvalue;

	}

	public Node<T1,T2> getNextElement() {
		return nextElement;
	}

}

public class StorageDynamic<E, V> implements Storage<E, V> {

	private Node<E,V> mystorageListhead;
	private Node<E,V> myStorageListCurrentElem;
	private int storageCount;

	StorageDynamic() {
		mystorageListhead = null;
		storageCount = 0;
		myStorageListCurrentElem = null;
	}

	public boolean add(E e) {
		try {
			Node<E,V> newNode = new Node<E,V>(e);
			if (myStorageListCurrentElem == null) {
				mystorageListhead = newNode;
				myStorageListCurrentElem = newNode;
				storageCount = 1;
			} else {
				myStorageListCurrentElem.setNextElement(newNode);
				myStorageListCurrentElem = newNode;
				storageCount++;
			}
			return true;
		} catch (Exception excep) {
			System.err.println("Some Error while Adding the element");
			return false;
		}
	}

	public void add(int index, E element) {
		if (index > storageCount) {
			System.err
					.println("Given Index is greater than total items stored");
			return;
		}
		Node<E,V> newNode = new Node<E,V>(element);
		int initialIndex=0;
		Node<E,V>prevIndex =null, currentIndex= mystorageListhead;
		//Getting Previous and current Pointers
		while(initialIndex++ < index && currentIndex != null){
			prevIndex = currentIndex;
			currentIndex = currentIndex.getNextElement();
		}
		//Insert when the List is Empty
		if((prevIndex == null) &&(currentIndex == null)){
			mystorageListhead=newNode;
			myStorageListCurrentElem=newNode;
		}
		else if(prevIndex == null){
			newNode.setNextElement(mystorageListhead);
			mystorageListhead = newNode;
			prevIndex=mystorageListhead;
		} 
		else if(currentIndex == null){
			prevIndex.setNextElement(newNode);
			myStorageListCurrentElem=newNode;
		}else{
			prevIndex.setNextElement(newNode);
			newNode.setNextElement(currentIndex);
		}
		storageCount++;
	}

	public void addElement(E obj) {
		add(obj);
	}

	public void addElement(E obj, V elem) {
		try {
			Node<E,V> newNode = new Node<E,V>(obj, elem);
			if (myStorageListCurrentElem == null) {
				mystorageListhead = newNode;
				myStorageListCurrentElem = newNode;
				storageCount = 1;
			} else {
				myStorageListCurrentElem.setNextElement(newNode);
				myStorageListCurrentElem = newNode;
				storageCount++;
			}
		} catch (Exception excep) {
			System.err.println("Some Error while Adding the element");
		}
	}

	public int capacity() {
		return storageCount;
	}

	public void clear() {
		storageCount = 0;
		mystorageListhead = null;
		myStorageListCurrentElem = null;
	}

	public E firstElement() {
		if (mystorageListhead != null) {
			return mystorageListhead.getValue();
		}
		return null;
	}

	public E get(int index) {
		if(index >= storageCount){
			System.err.println("There are only %s elements"+storageCount);
			return null;
		}
		if(mystorageListhead == null){
			System.err.println("No element Inserted");
		}
		Node<E,V> currentNode = mystorageListhead;
		int currentIndex=0;
		while(currentIndex++ < index &&(currentNode!=null)){
			currentNode = currentNode.getNextElement();
		}
		return currentNode.getValue();
	}

	public E lastElement() {
		if (myStorageListCurrentElem != null) {
			return myStorageListCurrentElem.getValue();
		}
		return null;
	}

	public Object clone() {
		StorageDynamic<E,V> cloneMyStorage = new StorageDynamic<E, V>();
		int index=0;
		Node<E,V> currentNode = mystorageListhead;
		while(index < storageCount && ( currentNode!=null)){
			cloneMyStorage.add(currentNode.getValue());
			currentNode = currentNode.getNextElement();
			}
		return cloneMyStorage;
	}

}
