class Node<T, V> implements Comparable<T> {
	private T value=null;
	private V reference=null;
	private Node<T,V> rightNode;
	private Node<T,V> leftNode;
	Node(T value, V ref) {		
		this.value = value;
		this.reference = ref;
		leftNode = null;
		rightNode = null;
	}

	public T getValue() {
		return value;
	}

	public V getReference() {
		return reference;
	}

	public Node<T,V> getRightNode(){
		return rightNode;
	}
	public Node<T, V> getLeftNode(){
		return leftNode;
	}
	public void setRightNode(Node<T,V> right){
		 rightNode=right;
	}
	public void setLeftNode(Node<T,V> left){
		 leftNode=left;
	}
	public int compareTo(T arg0) {
		if(arg0 instanceof Integer)
			return (Integer)value-(Integer)arg0;
		return value.toString().compareTo(arg0.toString());
	}

}

public class BinarySearchTree<T,V> {
	Node<T,V> BstRootNode;
	
	public BinarySearchTree(){
		BstRootNode = null;
	}
	
	public void addElement(T value, V reference){
		Node<T,V> node = new Node<T,V>(value, reference);
		Node<T,V> currentNode = BstRootNode;
		//Check if root is null and update root node
		if (BstRootNode == null) {
			BstRootNode = node;
			return;
		}
		//Compare the Element, if the element is less traverse left else right
		while (currentNode != null) {
			if (currentNode.compareTo(value) == 0) {
				System.out.println("Element Already exists "+value);
				return;
			} 
			
			else if (currentNode.compareTo(value) < 0) {
				if (currentNode.getRightNode() == null) {
					currentNode.setRightNode(node);
					return;
				}
				currentNode = currentNode.getRightNode();
			} 
			else {
				if (currentNode.getLeftNode() == null) {
					currentNode.setLeftNode(node);
					return;
				}
				currentNode = currentNode.getLeftNode();
			}
		}
	}
	
	/**
	 * Get the reference of the element
	 * @param element : element to look in the dictionary tree
	 * @return reference number in the dictionary
	 */
	public V getReference(T element) {
		Node<T,V> currentNode = BstRootNode;
		if (BstRootNode == null) {
			return null;
		}
		//Binary Search to find the Element position
		while (currentNode != null) {
			if (currentNode.compareTo(element) == 0) {
				return currentNode.getReference();
			} else if (currentNode.compareTo(element) < 0) {
				currentNode = currentNode.getRightNode();
			} else {
				currentNode = currentNode.getLeftNode();
			}
		}		
		return null;
	}
	
	

}
