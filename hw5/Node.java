

import java.util.Comparator;

public class Node implements Comparable<Node>{
	private int id;
	private String First_Name;
	private String Last_Name;
	private String Ticket_Type;
	private Node next;
	
	
	/**
     * Constructs a new node with all of his attributes: first name, last name, ticket type
     */
	public Node(int id, String first_Name, String last_Name, String ticket_Type) {
		this.id = id;
		First_Name = first_Name;
		Last_Name = last_Name;
		Ticket_Type = ticket_Type;
		this.next = null;
	}
	
	/**
     * Constructs a new node with all of his attributes: first name, last name, ticket type, pointer to the next node
     */
	public Node(int id, String first_Name, String last_Name, String ticket_Type, Node next) {
		this.id = id;
		First_Name = first_Name;
		Last_Name = last_Name;
		Ticket_Type = ticket_Type;
		this.next = next;
	}
	
	/**
     * Getter for the Id number inside the node
     * @return Integer stored in the node
     */
	public int getId() {
		return id;
	}
	
	/**
     * Setter for the Id number inside the node
     */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
     * Getter for the First Name inside the node
     * @return String stored in the node
     */
	public String getFirst_Name() {
		return First_Name;
	}
	
	/**
     * Setter for the First Name inside the node
     */
	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}

	/**
     * Getter for the Last Name inside the node
     * @return String stored in the node
     */
	public String getLast_Name() {
		return Last_Name;
	}

	/**
     * Setter for the Last Name inside the node
     */
	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}

	/**
     * Getter for the Ticket Type inside the node
     * @return String stored in the node
     */
	public String getTicket_Type() {
		return Ticket_Type;
	}

	/**
     * Setter for the Ticket Type inside the node
     */
	public void setTicket_Type(String ticket_Type) {
		Ticket_Type = ticket_Type;
	}

	/**
     * Getter for the next node in the list
     * @return next node in the list
     */
	public Node getNext() {
		return next;
	}

	/**
     * Setter for the next node
     * @param next New next node
     */
	public void setNext(Node next) {
		this.next = next;
	}
	
	/**
     * Comparable method to compare between nodes in order to
     * arrange the nodes in the correct order
     * @param 2 nodes in a list
     * @return integer value represents which node is bigger
     */
	@Override
	public int compareTo(Node node) {
		int type1 = 0,type2 = 0;
		if(this.getTicket_Type().compareTo("VIP") == 0)
			type1 = 0;
		if(this.getTicket_Type().compareTo("BUSINESS") == 0)
			type1 = 1;
		if(this.getTicket_Type().compareTo("ECONOMY") == 0)
			type1 = 2;
		if(this.getTicket_Type().compareTo("LOW_COST") == 0)
			type1 = 3;
		if(node.getTicket_Type().compareTo("VIP") == 0)
			type2 = 0;
		if(node.getTicket_Type().compareTo("BUSINESS") == 0)
			type2 = 1;
		if(node.getTicket_Type().compareTo("ECONOMY") == 0)
			type2 = 2;
		if(node.getTicket_Type().compareTo("LOW_COST") == 0)
			type2 = 3;
		return type1 - type2;
	}
}
