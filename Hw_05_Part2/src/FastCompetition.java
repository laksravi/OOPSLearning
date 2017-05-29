/**
 * Binary Search Tree Implementation to store the Values
 * 
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 * 
 * @param <E>
 *            Type of the Element
 */
class BinarySearchNode<E> implements Comparable<E> {
	E value;
	int count;
	Integer[] indexes;
	BinarySearchNode<E> leftNode;
	BinarySearchNode<E> rightNode;

	public BinarySearchNode() {
		value = null;
		count = 0;
		indexes = null;
	}

	// Constructor to add Value and Index;
	public BinarySearchNode(E value, int index) {
		indexes = new Integer[100];
		indexes[count++] = index;
		this.value = value;
	}

	public BinarySearchNode(E e) {
		value = e;
		indexes = new Integer[100];
	}

	/**
	 * Set the element Value
	 * 
	 * @param elem
	 *            Element
	 */
	public void setValue(E elem) {
		value = elem;
	}

	/**
	 * 
	 * @return the element Value
	 */
	public E getValue() {
		return value;
	}

	/**
	 * 
	 * @param appends
	 *            the index to the available List
	 */
	public void addOneMoreIndex(int index) {
		indexes[count++] = index;
	}

	/**
	 * Compares the value and returns the difference
	 */
	public int compareTo(E arg0) {
		return this.value.toString().compareTo(arg0.toString());
	}

	/**
	 * sets the Node Value
	 * 
	 * @param leftNode
	 *            Left node to be set
	 */
	public void setLeftNode(BinarySearchNode<E> leftNode) {
		this.leftNode = leftNode;
	}

	/**
	 * @returns the Left Node
	 */
	public BinarySearchNode<E> getLeftNode() {
		return leftNode;
	}

	/**
	 * Returns the right node the current Element
	 * 
	 * @return the Right Node
	 */
	public BinarySearchNode<E> getRightNode() {
		return rightNode;
	}

	/**
	 * Sets the right node for the current element
	 * 
	 * @param rightNode
	 */
	public void setRightNode(BinarySearchNode<E> rightNode) {
		this.rightNode = rightNode;
	}

	/**
	 * To get the positions in which the current element is present
	 * 
	 * @returns the array of Positions in which current element was present
	 */
	public Integer[] getIndexes() {
		return this.indexes;
	}

	/**
	 * Sets the indexes for the Node
	 * 
	 * @param indexes2
	 */
	public void setIndexes(Integer[] indexes2) {
		this.indexes = indexes2;
	}

	/**
	 * @return the total occurances of this element
	 */
	public int getElementCount() {
		return count;
	}

	/**
	 * @return the First occurance of the Element
	 */
	public int getFirstIndex() {
		if (count <= 0) {
			return -1;
		} else {
			return indexes[0];
		}
	}
}

/**
 * 
 * @author Lakshmi Ravi
 * @author Aarthi Gorde
 *
 * @param <E>
 */
public class FastCompetition<E> implements Competition<E> {
	private BinarySearchNode<E> BstRootNode;
	private static int elementIndexCounter = 0;
	private static int elementCount = 0;

	public FastCompetition(int sizeRequired) {
		BstRootNode = null;
	}

	/**
	 * Add an Element to the Storage
	 */
	public boolean add(E e) {
		BinarySearchNode<E> node = new BinarySearchNode<E>(e);
		BinarySearchNode<E> currentNode = BstRootNode;
		//Check if root is null and update root node
		if (BstRootNode == null) {
			BstRootNode = node;
			node.addOneMoreIndex(elementCount++);
			return true;
		}
		//Compare the Element, if the element is less traverse left else right
		while (currentNode != null) {
			if (currentNode.compareTo(e) == 0) {
				currentNode.addOneMoreIndex(elementCount++);
				return true;
			} else if (currentNode.compareTo(e) < 0) {
				if (currentNode.getRightNode() == null) {
					currentNode.setRightNode(node);
					node.addOneMoreIndex(elementCount++);
					return true;
				}
				currentNode = currentNode.getRightNode();
			} else {
				if (currentNode.getLeftNode() == null) {
					currentNode.setLeftNode(node);
					node.addOneMoreIndex(elementCount++);
					return true;
				}
				currentNode = currentNode.getLeftNode();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		E element = (E) o;
		BinarySearchNode<E> currentNode = BstRootNode;
		if (BstRootNode == null) {
			return false;
		}
		//Binary Search to find the Element position
		while (currentNode != null) {
			if (currentNode.compareTo(element) == 0) {
				return true;
			} else if (currentNode.compareTo(element) < 0) {
				currentNode = currentNode.getRightNode();
			} else {
				currentNode = currentNode.getLeftNode();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {
		E element = (E) o;
		BinarySearchNode<E> currentNode = BstRootNode;
		if (BstRootNode == null) {
			return false;
		}
		//Binary search - if element is found, remove the element;
		while (currentNode != null) {
			if (currentNode.compareTo(element) == 0) {
				removeElement(currentNode);
				return true;
			} else if (currentNode.compareTo(element) < 0) {
				currentNode = currentNode.getRightNode();
			} else {
				currentNode = currentNode.getLeftNode();
			}
		}
		return false;
	}
	
	/**
	 * Removes the current Node
	 * Updates the position with Maximum element from left sub-tree or
	 * Minimum element from right sub-tree.
	 * @param currentNode
	 */
	private void removeElement(BinarySearchNode<E> currentNode) {
		if (currentNode.getLeftNode() == null
				&& currentNode.getRightNode() == null) {
			currentNode = null;
			elementCount--;
			return;
		} else if (currentNode.getRightNode() != null) {
			BinarySearchNode<E> minRightMostNode = currentNode.getRightNode();
			while (minRightMostNode.getLeftNode() != null) {
				minRightMostNode = minRightMostNode.getLeftNode();
			}
			currentNode.setValue(minRightMostNode.getValue());
			currentNode.setIndexes(minRightMostNode.getIndexes());
			removeElement(minRightMostNode);

		} else if (currentNode.getLeftNode() != null) {
			BinarySearchNode<E> maxLeftMostNode = currentNode.getLeftNode();
			while (maxLeftMostNode.getRightNode() != null) {
				maxLeftMostNode = maxLeftMostNode.getRightNode();
			}
			currentNode.setValue(maxLeftMostNode.getValue());
			currentNode.setIndexes(maxLeftMostNode.getIndexes());
			removeElement(maxLeftMostNode);

		}
	}

	/**
	 * Get element at index
	 * @param index : position of the element
	 */
	public E elementAt(int index) {
		return elementAt(BstRootNode, index);
	}

	/**
	 * Element of an element in the given tree.
	 * Recursively left, right- binary search is done appropriately.
	 * @param currentNode ; current node from which search has to be done
	 * @param index
	 * @return
	 */
	public E elementAt(BinarySearchNode<E> currentNode, int index) {
		if (currentNode != null) {

			Integer[] indexes = currentNode.getIndexes();
			for (int elemPos = 0; elemPos < currentNode.count; elemPos++) {
				if (indexes[elemPos] == index) {
					return (E)currentNode.getValue();
				}
			}
		
			if (currentNode.getFirstIndex() > index) {
				E left = elementAt(currentNode.getLeftNode(), index);
				if (left != null)
					return left;
				return elementAt(currentNode.getRightNode(), index);
			} else {
				E right = elementAt(currentNode.getRightNode(), index);
				if (right != null)
					return right;
				return elementAt(currentNode.getLeftNode(), index);
			}
		}

		return null;
	}

	public Competition<E> sort() {
		mySortLogic(BstRootNode);
		elementIndexCounter = 0;
		return this;
	}

	/**
	 *  Change the Indexes of the variables
	 * @param currentNode : node to start index from
	 * @return : Tree with index corresponding to it's element position after sorting
	 */
	private Competition<E> mySortLogic(BinarySearchNode<E> currentNode) {
		if (currentNode == null) {
			return this;
		}
		mySortLogic(currentNode.getLeftNode());
		Integer[] positions = currentNode.getIndexes();
		for (int index = 0; index < currentNode.count; index++) {
			positions[index] = elementIndexCounter++;
		}
		mySortLogic(currentNode.getRightNode());
		return null;
	}

	/**
	 * returns the size of the Storage
	 */
	public int size() {
		return elementCount;
	}

}
