/**
 * This is a testing framework. Use it extensively to verify that your code is working
 * properly.
 */
public class Tester {

	private static boolean testPassed = true;
	private static int testNum = 0;
	
	/**
	 * This entry function will test all classes created in this assignment.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
	
		// Each function here should test a different class.

		// CharLinkedListNode
		testCharLinkedListNode();
		
		// CharLinkedList
		testCharLinkedList();

		// SuffixTreeNode
		testSuffixTreeNode();
		
		// SuffixTree
		testSuffixTree();

		// ReverseSuffixTree
		testReverseSuffixTree();
		
		/* TODO - continue writing a function for each class */

		
		// Notifying the user that the code have passed all tests. 
		if (testPassed) {
			System.out.println("All " + testNum + " tests passed!");
		}
	}

	/**
	 * This utility function will count the number of times it was invoked. 
	 * In addition, if a test fails the function will print the error message.  
	 * @param exp The actual test condition
	 * @param msg An error message, will be printed to the screen in case the test fails.
	 */
	private static void test(boolean exp, String msg) {
		testNum++;
		
		if (!exp) {
			testPassed = false;
			System.out.println("Test " + testNum + " failed: "  + msg);
		}
	}

	/**
	 * Checks the CharLinkedList class.
	 */
	
	private static void testCharLinkedListNode(){
		ICharLinkedListNode word = new CharLinkedListNodeImpl('a');
		word.setNext(new CharLinkedListNodeImpl('b'));
		test(word.getChar() == 'a', "first char should be 'a'");
		test(word.getNext().getChar() == 'b', "next char should be 'b'");
	}
	
	private static void testCharLinkedList(){
		CharLinkedList list = new CharLinkedListImpl();
		test(list.size() == 0, "The size of the list should be 0");
		list.add('a');
		test(list.size() == 1, "The size of the list should be 1");
		test(list.firstChar() == 'a', "The first char should be 'a'");
		list.add('b');
		test(list.size() == 2, "The size of the list should be 2");
		test(list.getLast().getChar() == 'b', "The first char should be 'b'");
		CharLinkedList list2 = new CharLinkedListImpl();
		list2.add('c');
		list2.add('d');
		list.append(list2);
		test(list.size() == 4 , "The size of the list should be 4");
		test(list.getLast().getChar() == 'd', "the last char should be 'd'");
	}

	/**
	 * Checks the SuffixTreeNode class.
	 */
	private static void testSuffixTreeNode() {
		// test empty root
		SuffixTreeNode node = new SuffixTreeNodeImpl();
		test(node.getTotalWordLength() == 0, "node word length should be 0");
		test(node.getNumOfChildren() == 0, "node num of children should be 0");

		// test search, binary search, shiftChildren and addChild
		SuffixTreeNode child1 = new SuffixTreeNodeImpl(CharLinkedList.from("abc"), node);
		SuffixTreeNode child2 = new SuffixTreeNodeImpl(CharLinkedList.from("bcd"), node);
		SuffixTreeNode child3 = new SuffixTreeNodeImpl(CharLinkedList.from("hello"), node);
		SuffixTreeNode child4 = new SuffixTreeNodeImpl(CharLinkedList.from("mmmmm"), node);
		node.setChildren(new SuffixTreeNode[]{child1, child2, child3, child4, null, null, null, null}, 4);

		// binary search
		test(node.binarySearch('b', 0, 3) == child2, "search for 'b' should find child2");

		// search
		test(node.search('a') == child1, "search for 'a' should return child1");
		test(node.search('x') == null, "search for 'x' should fail");

		// shift children
		SuffixTreeNode node2 = new SuffixTreeNodeImpl();
		SuffixTreeNode child7 = new SuffixTreeNodeImpl(CharLinkedList.from("hello"), node2);
		SuffixTreeNode child8 = new SuffixTreeNodeImpl(CharLinkedList.from("abc"), node2);
		SuffixTreeNode child9 = new SuffixTreeNodeImpl(CharLinkedList.from("bcd"), node2);
		node2.setChildren(new SuffixTreeNode[]{child7,child8,child9,null,null,null},3);
		node2.shiftChildren(0);
		test(node2.getChildren()[2] == child8, "child 8 should move one step to the right");
		
		// add child
		SuffixTreeNode child5 = new SuffixTreeNodeImpl(CharLinkedList.from("dog"), node);
		SuffixTreeNode child10 = new SuffixTreeNodeImpl(CharLinkedList.from("cat"), node);
		node.addChild(child5);
		node.addChild(child10);
		test(node.getChildren()[3] == child5, "4th child should be child5");
		test(node.getChildren()[2] == child10, "3rd child should be child10");
		
		// add suffix
		char[] word = {'w','o','r','l','d'};
		node2.addSuffix(word, 0);
		CharLinkedList temp = new CharLinkedListImpl();
		temp = temp.from("world");
		node2.getChildren()[4].compress();
		String b = temp.toString();
		String a = node2.getChildren()[4].getChars().toString();
		test(a.compareTo(b) == 0, "should work");
		
		// compress
		char[] word1 = {'a','a'};
		char[] word2 = {'a','d','a','b'};
		char[] word3 = {'b'};
		char[] word4 = {'c','b','b','c'};
		char[] word5 = {'c','b','d','a'};
		SuffixTreeNode node3 = new SuffixTreeNodeImpl();
		node3.addSuffix(word1, 0);
		node3.addSuffix(word2, 0);
		node3.addSuffix(word3, 0);
		node3.addSuffix(word4, 0);
		node3.addSuffix(word5, 0);
		node3.compress();
		String c = node3.getChildren()[0].getChildren()[1].getChars().toString();
		String d = node3.getChildren()[2].getChildren()[0].getChars().toString();
		String e = node3.getChildren()[2].getChildren()[1].getChars().toString();
		test(c.compareTo("dab") == 0, "should return dab");
		test(d.compareTo("bc") == 0, "should return bc");
		test(e.compareTo("da") == 0, "should return da");
		
		//num of occurrences
		test(node3.numOfOccurrences(new char[] {'a'},0) == 2, "number of occurrences should be 2");
		SuffixTree word6 = new SuffixTreeImpl("mississippi");
		word6.compressTree();
		test(word6.getRoot().numOfOccurrences(new char[] {'s','s','i'}, 0) == 2, "number of occurrences should be 2");
		test(word6.getRoot().numOfOccurrences(new char[] {'i'}, 0) == 4, "number of occurrences should be 4");
		test(word6.getRoot().numOfOccurrences(new char[] {'s'}, 0) == 4, "number of occurrences should be 4");
		test(word6.getRoot().numOfOccurrences(new char[] {'m','s'}, 0) == 0, "number of occurrences should be 0");
		
		//find suffix leaf
		SuffixTreeNode f = word6.getRoot().children[0].children[1].children[1];
		SuffixTreeNode g = word6.getRoot().children[0].children[1].children[0];
		SuffixTreeNode h = word6.getRoot().children[3].children[1].children[0];
		SuffixTreeNode i = word6.getRoot().children[3].children[0].children[1];
		SuffixTreeNode j = word6.getRoot().children[2].children[1];
		SuffixTreeNode k = word6.getRoot().children[2].children[0];
		test(f.getChars().toString().compareTo("ssippi$") == 0, "should return the leaf ssippi$");
		test(g.getChars().toString().compareTo("ppi$") == 0, "should return the leaf ppi$");
		test(h.getChars().toString().compareTo("ppi$") == 0, "should return the leaf ppi$");
		test(i.getChars().toString().compareTo("ssippi$") == 0, "should return the leaf ssippi$");
		test(j.getChars().toString().compareTo("pi$") == 0, "should return the leaf pi$");
		test(k.getChars().toString().compareTo("i$") == 0, "should return the leaf i$");
		
		//find Lowest Common Ancestor
		test(f.findLCA(g).getChars().toString().compareTo("ssi") == 0, "should return the parent ssi");
		test(h.findLCA(i).getChars().toString().compareTo("s") == 0, "should return the parent s");
		test(j.findLCA(k).getChars().toString().compareTo("p") == 0, "should return the parent p");
	}
	
	private static void testSuffixTree() {
		SuffixTree word = new SuffixTreeImpl("mississippi");
		word.compressTree();
		test(word.contains("ssi") == true, "the word should contain ssi");
		test(word.contains("ms") == false, "the word shouldn't contain ms");
		test(word.numOfOccurrences("ssi") == 2, "number of occurrences of ssi in the word should be 2");
		test(word.numOfOccurrences("s") == 4, "number of occurrences of ssi in the word should be 4");
		test(word.numOfOccurrences("ms") == 0, "number of occurrences of ssi in the word should be 0");
	}
	private static void testReverseSuffixTree(){
		testPalindrome("mississippi", "issXssi");
		testPalindrome("abc", "X");
		testPalindrome("abbc", "bb");
		testPalindrome("xabccbay", "abccba");
		testPalindrome("abcd", "X");
		testPalindrome("a", "X");
		testPalindrome("aabbc", "aa");
		ReverseSuffixTree word = new ReverseSuffixTreeImpl("mississippi");
		test(word.getRoot().children[0].children[2].children[2].children[0].findLCA(word.getRoot().children[0].children[2].children[0]).getChars().toString().compareTo("ssi") == 0, "should return the parent ssi");
		test(word.getRoot().numOfOccurrences(new char[] {'s','s','i'}, 0) == 4, "number of occurrences of ssi should be 4");
		test(word.getRoot().getTotalWordLength() == 24, "the word length should be 24");
	}

	private static void testPalindrome(String word, String expected){
		test(new ReverseSuffixTreeImpl(word).longestPalindrome().equals(expected), "Longest palindrome should be " + expected);
	}

}