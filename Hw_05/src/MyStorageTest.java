
public class MyStorageTest {
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		/*StorageFixed<String, String> myStringStorage = new StorageFixed<String, String>();
		StorageFixed<Integer, String> myIntegerStorage = new StorageFixed<Integer, String>();

		//Adding element
		for(int index=0;index<100;index++){
			myStringStorage.add("Hi.."+index);
		}
		//Print Capacity
		System.out.println("\n"+myStringStorage.capacity());
		
		//Try to add when the storage is full
		System.out.println(myStringStorage.add("FULL_STRING"));
		//Cloning the Elements
		StorageFixed<String, Integer> myStringStorage2 = (StorageFixed<String, Integer>) myStringStorage.clone();
		
		System.out.println("Capacity "+myStringStorage.capacity());
		System.out.println("FirstElement "+myStringStorage.firstElement());
		System.out.println("Last Element "+myStringStorage.lastElement());
		System.out.println("23rd Element "+myStringStorage.get(23));
		System.out.println("Getting an Element at index > MaxSize");
		System.out.println(myStringStorage.get(100));
		//Clear it and try to get an Element
		myStringStorage.clear();
		System.out.println();
		System.out.println(myStringStorage.get(20));*/
		testDynamic();
	}

	public static void testDynamic(){
		StorageDynamic<String, String> dynamicstorage = new StorageDynamic<String, String>();
		dynamicstorage.add(0, "WHEN_ZERO");
		for(int index=0;index<3;index++){
		dynamicstorage.add("_"+index);
		//System.out.println(dynamicstorage.lastElement());
		}
		dynamicstorage.add(0, "Initial");
		dynamicstorage.add(dynamicstorage.capacity(),"LastNode");
		dynamicstorage.add(3,"THREE");
		dynamicstorage.addElement("EXTRA");
		
		//Print the Storage
		for(int index=0;index<dynamicstorage.capacity();index++){
		System.out.println(dynamicstorage.get(index));
		}
		System.out.println();
	}
}
