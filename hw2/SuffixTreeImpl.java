
public class SuffixTreeImpl extends SuffixTree{
	
	public SuffixTreeImpl(String word) {
		super(word);
	}

	public boolean contains(String subword){
		return root.numOfOccurrences(subword.toCharArray(), 0) != 0;
	}
	
	public int numOfOccurrences(String subword){
		return root.numOfOccurrences(subword.toCharArray(), 0);
	}

}