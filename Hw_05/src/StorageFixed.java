

public class StorageFixed<E, V> implements Storage<E, V> {

	private static final int MAX_ARRAY_SIZE = 100;
	private E[] storageArray;
	private V[] secondaryElementStorage;
	private int storageSize;

	
	@SuppressWarnings("unchecked")
	public StorageFixed() {
		storageArray = (E[]) new Object[MAX_ARRAY_SIZE];
		secondaryElementStorage =(V[]) new Object[MAX_ARRAY_SIZE];
		storageSize = 0;
	}

	public boolean add(E e) {
		if (storageSize < MAX_ARRAY_SIZE) {
			storageArray[storageSize]=(e);
			storageSize++;
			return true;
		}
		System.err.println("Size Exceeded. Can't add the Element "+e);
		return false;
	}

	public void add(int index, E element) {
		if(storageSize == MAX_ARRAY_SIZE){
			System.err.println("The storage is already Full");
			return;
		}
		if (index == storageSize) {
			storageArray[storageSize++]=element;
		} else if (index < storageSize) {
			E tempElement = null;
			while (index++ < storageSize) {
				tempElement = storageArray[index];
				storageArray[index]=(element);
				element = tempElement;
			}
		} else {
			System.out.println("Storage is already full");
			//throw new ArrayIndexOutOfBoundsException();
		}
	}

	public Object clone() {
		StorageFixed<E, V> newfixedStorage = new StorageFixed<E, V>();
		for (int index = 0; index < this.storageSize; index++) {
			newfixedStorage.storageArray[index] = this.storageArray[index];
		}
		newfixedStorage.storageSize=storageSize;
		return newfixedStorage;
	}

	public void addElement(E obj) {
		addElement(obj, null);
	}

	public void addElement(E obj, V elem) {
		if (storageSize < MAX_ARRAY_SIZE) {
			storageArray[storageSize]=obj;
			secondaryElementStorage[storageSize++] = elem;
		}
		else{
			throw new ArrayIndexOutOfBoundsException();
		}	}

	public int capacity() {
		return storageSize;
	}

	public void clear() {
		storageSize=0;
	}

	public E firstElement() {
		if(storageSize >0){
			return storageArray[0];
		}
		return null;
	}

	public E get(int index) {
		if(index < storageSize){
			return storageArray[index];
		}
		System.err.println("Index is greater than available Elements");
		return null;
	}

	public E lastElement() {
		if (storageSize > 0) {
			return storageArray[storageSize - 1];
		}
		return null;
	}

}
