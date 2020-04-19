
public class SuffixTreeNodeImpl extends SuffixTreeNode{
    
	public SuffixTreeNodeImpl() {
	        this.chars = null;
	        this.children = new SuffixTreeNode[ALPHABET_LENGTH];
	        this.numOfChildren = 0;

	        this.descendantLeaves = 0;
	        this.parent = null;
	        this.totalWordLength = 0;
	    }
	public SuffixTreeNodeImpl(CharLinkedList chars, SuffixTreeNode parent) {
	        this.chars = chars;
	        this.descendantLeaves = 0;
	        this.parent = parent;
	        this.totalWordLength = parent.totalWordLength + 1;
	    }
	
	public SuffixTreeNode search(char c) {
		return binarySearch(c, 0, children.length);
	}
	
	public SuffixTreeNode binarySearch(char target, int left, int right) {
		if(target != '$' && target != '#') {
			if (left>right)
				return null;
			int middle = (left+right)/2;
			if (children[middle]==null)
				return binarySearch(target, left, middle-1);
			if(children[middle].getChars().firstChar() == '$' || children[middle].getChars().firstChar() == '#')
				return binarySearch(target, left, middle-1);
			if (target == children[middle].getChars().firstChar())
				return children[middle];
			else {
				if (target < children[middle].getChars().firstChar())
					return binarySearch(target, left, middle-1);
				else
					return binarySearch(target, middle+1, right);
			}
		}
		else {
			if (left == right)
				return null;
			if(children[left] != null)
				if((target == '$' && children[left].getChars().firstChar() == '$') || (target == '#' && children[left].getChars().firstChar() == '#'))
					return children[left]; 
			return binarySearch(target, left+1, right);
		}
	}
	
	public void shiftChildren(int until) {
		SuffixTreeNode temp = children[until], newTemp = children[until];
		int m = 0;
		for(int i=0;i<=this.children.length;i++) {
			if(children[i]==null){
				m = i;
				break;
			}
		}
		if(until+1 < this.numOfChildren)
			newTemp = children[until+1];
		for(int i=until;i<m;i++){
			children[i+1] = temp;
			temp = newTemp;
			newTemp = children[i+2];
		}
	}
	
	public void addChild(SuffixTreeNode node) {
		int m = 0;
		for (int i=0;i<this.numOfChildren;i++) {
			if(children[i]!=null) {
				if(node.getChars().firstChar() == '$' || node.getChars().firstChar() == '#')
					m = i+1;
				else
					if(children[i].getChars().firstChar() != '$' && children[i].getChars().firstChar() != '#')
						if (node.getChars().firstChar() > children[i].getChars().firstChar())
							m = i+1;
			}
		}
		shiftChildren(m);
		children[m] = node;
		node.parent = this;
	}
	
	public void addSuffix(char[] word, int from) {
		if(from == word.length)
			return;
		char c = word[from];
		SuffixTreeNode temp = search(c);
		CharLinkedList temp2 = new CharLinkedListImpl().from(c);
		SuffixTreeNode temp3 = new SuffixTreeNodeImpl();
		temp3.chars = temp2;
		if(temp == null) {
			this.numOfChildren++;
			addChild(temp3);
			if(temp3.parent!=null) {
				temp3.parent.descendantLeaves++;
				temp3.parent.totalWordLength++;
			}
			temp3.addSuffix(word, from+1);
		}
		else {
			temp.parent = this;
			if(temp.parent!=null) {
				temp.parent.descendantLeaves++;
				temp.parent.totalWordLength++;
			}
			temp.addSuffix(word, from+1);
		}
	}
	
	public void compress() {
		for(int i=0;i<this.numOfChildren;i++) {
			if(this.numOfChildren != 0)
				this.children[i].compress();
			if(this.numOfChildren == 1) {
				this.chars.append(this.children[0].getChars());
				this.totalWordLength = this.children[0].totalWordLength;
				if(this.children[0] == null) {
					this.numOfChildren = 0;
				}
				else
					this.numOfChildren = this.children[0].numOfChildren;
				for(int j=0; j<this.children[0].numOfChildren;j++)
					this.children[0].children[j].parent = this;
				this.children = this.children[0].children;
			}
		}
	}
	
	public int numOfOccurrences(char[] subword, int from) {
		char c = subword[from];
		SuffixTreeNode temp = search(c);
		if(from+1 == subword.length && temp != null)
			return temp.descendantLeaves;
		else
			if(this.getChars() != null && this.getChars().size() > 1 && this.getChars().getLast().getChar() == c)
				return this.descendantLeaves;
			if(temp == null)
				return 0;
		if(from+1 <= subword.length-1)
			return temp.numOfOccurrences(subword, from+1);
		return this.descendantLeaves;
	}
	
	public SuffixTreeNode findSuffixLeaf(char[] word, int from) {
		char c = word[from];
		SuffixTreeNode temp = search(c);
		if(word.length == 1)
			return temp;
		if(word.length>=2)
			if (temp.getChars().getLast().getChar() == word[word.length-2] && word.length <= from+temp.getChars().size())
				return temp;
		else
			if(word.length <= from+temp.getChars().size())
				return temp;
		return temp.findSuffixLeaf(word, from+temp.getChars().size());
	}
	
	public SuffixTreeNode findLCA(SuffixTreeNode node2) {
		if(this == node2)
			return this;
		SuffixTreeNode temp = this.parent;
		SuffixTreeNode temp2 = node2.parent;
		if(temp == null && node2.parent == null)
			return null;
		if(temp == node2)
			return temp;
		if(temp2 == this || temp2 == temp)
			return temp2;
		if(temp.descendantLeaves < temp2.descendantLeaves)
			return this.parent.findLCA(node2);
		return findLCA(node2.parent);
	}
}
