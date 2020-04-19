
public class CharLinkedListImpl extends CharLinkedList{
    
	public void add(char c) {
		ICharLinkedListNode temp = new CharLinkedListNodeImpl(c);
		if (this.first == null){
			this.first = temp;
			this.last = temp;
		}
		else {
			this.last.setNext(temp);
			this.last = temp;
		}
	}
	
	public char firstChar() {
		return this.first.getChar();
	}
	
	public int size() {
		int count = 0;
		ICharLinkedListNode current = first;
		while(current!=null) {
			current = current.getNext();
			count++;
		}
		return count;
	}
	
	public void append(CharLinkedList toAppend) {
		if (this.first == null){
			this.first = toAppend.getFirst();
		}
		else {
			if (this.last == null) {
				this.last = toAppend.getFirst();
				this.first.setNext(this.last);
				this.last = toAppend.getLast();
			}
			else {
				this.last.setNext(toAppend.getFirst());
				this.last = toAppend.getLast();
			}
		}
	}
}
