
public class CharLinkedListNodeImpl implements ICharLinkedListNode{
	private char value;
	private ICharLinkedListNode next;
	
	public CharLinkedListNodeImpl(char value) {
		this.value = value;
		this.next = null;
	}
	
	public CharLinkedListNodeImpl(char value, ICharLinkedListNode next) {
		this.value = value;
		this.next = next;
	}

	public char getChar() {
		return this.value;
	}

	public ICharLinkedListNode getNext() {
		return this.next;
	}
	
	public void setNext(ICharLinkedListNode next) {
		this.next = next;
	}
}
