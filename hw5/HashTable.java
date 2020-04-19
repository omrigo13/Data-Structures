

import java.util.ArrayList;

public class HashTable {
	private LinkedList[] hash;
	private int size;

	/**
     * Constructs a new empty passengers HashTable
     */
	public HashTable(int size) {
		this.hash = new LinkedList[size/3];
		this.size = size/3;
		for(int i = 0; i < this.size; i++) {
			this.hash[i] = new LinkedList();
		}
	}
	
	/**
     * represents the size of the HashTable
     * @return The size of the HashTable to store all the passengers
     */
	public int getSize() {
		return size;
	}
	
	/**
     * represents the LinkedList array
     * @return array of LinkedLists represents all the passengers
     */
	public LinkedList[] getHash() {
		return hash;
	}

	/**
     * adds a new passenger (node) to the right place in the HashTable
     * @param node The Node to be appended at the end of a LinkedList in the
     * array of LinkedLists
     */
	public void add(Node node) {
		if(hash[node.getId()%this.getSize()] == null)
			hash[node.getId()%this.getSize()] = new LinkedList();
		hash[node.getId()%this.getSize()].add(node);
	}
	
	/**
	 * Calculates the moves that takes to search a passenger in the HashTable
	 * @return The number of moves
	 * @param node the passenger to be searched in the HashTable
	 */
	public int moves(Node node) {
		LinkedList data = hash[node.getId()%this.getSize()];
		int count = 1;
		Node first = data.getHead();
		while(first != null) {
			if(first.getId() != node.getId())
				count++;
			else {
				break;
			}
			first = first.getNext();
		}
		return count;
	}
	
	/**
     * checks if a passenger registered to the flight
     * @return boolean true or false depends on searching of a passenger in the HashTable
     * @param node the passenger to be searched in the HashTable
     */
	public boolean register(Node node) {
		LinkedList data = hash[node.getId()%this.getSize()];
		Node first = data.getHead();
		while(first != null) {
			if(first.getId() == node.getId())
				return true;
			first = first.getNext();
		}
		return false;
	}
}