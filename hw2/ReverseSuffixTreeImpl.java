
public class ReverseSuffixTreeImpl extends ReverseSuffixTree{
	
	public ReverseSuffixTreeImpl(String word) {
		super(word);
	}

	public String longestPalindrome() {
		SuffixTreeNode A,B,C,D;
		String newWord,newWord2,maxeven = "",maxodd = "";
		for(int i=1;i<word.length;i++) {
			A = root.findSuffixLeaf(new String(reverseWord).substring(word.length-i-1, word.length).toCharArray(), 0);
			B = root.findSuffixLeaf(new String(word).substring(i, word.length).toCharArray(), 0);
			if(A != null && B != null)
				newWord = A.findLCA(B).restoreStringAlongPath();
			else
				newWord = "";
			if(newWord.length()*2>maxeven.length()*2)
				maxeven = newWord;
			C = root.findSuffixLeaf(new String(reverseWord).substring(word.length-i, word.length).toCharArray(), 0);
			D = root.findSuffixLeaf(new String(word).substring(i, word.length).toCharArray(), 0);
			if(C != null && D != null)
				newWord2 = C.findLCA(D).restoreStringAlongPath();
			else
				newWord2 = "";
			if((newWord2.length()*2) + 1 > (maxodd.length()*2) + 1)
				maxodd = newWord2;
		}
		StringBuilder s1 = new StringBuilder(maxeven).reverse(),s2 = new StringBuilder(maxodd).reverse();
		if((maxodd.length()*2) + 1 > maxeven.length()*2)
			return s2.toString() + "X" + maxodd;
		return s1.toString() + maxeven;
	}
}