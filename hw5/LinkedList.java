
public class LinkedList {
	private Node head;
	private int size;
	
    /**
     * Constructs a new empty passengers linked list
     */
	public LinkedList() {
		this.head = null;
		this.size = 0;
	}
	
	 /**
     * Getter for the first passenger
     * @return The first Node (passenger) in the list
     */
	public Node getHead() {
		return head;
	}

	/**
     * Setter for the first passenger in the list
     */
	public void setHead(Node head) {
		this.head = head;
	}

	/**
     * represents the size of the list
     * @return The number of passengers in the list
     */
	public int getSize() {
		return size;
	}

	/**
     * Setter for the size of passengers in the list
     */
	public void setSize(int size) {
		this.size = size;
	}

	 /**
     * adds a new passenger (node) to the end of this list
     * @param node The Node to be appended at the end of this list
     */
	public void add(Node node) {
		if(this.head == null) {
			this.head = node;
			this.size = 1;
		}
		else {
			Node last = this.head;
			int count = 2;
			while(last.getNext() != null) {
				last = last.getNext();
				count++;
			}
			last.setNext(node);
			this.size = count;
		}
	}
}
