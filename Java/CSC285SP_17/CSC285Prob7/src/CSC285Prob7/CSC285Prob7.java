package CSC285Prob7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import CSC285Prob7.LinkManager;
import CSC285Prob7.binaryTree;

public class CSC285Prob7 {
	public static void main(String[] args) throws Exception{
		
		binaryTree<Integer> bst = new binaryTree<Integer>();
		PrintWriter out = new PrintWriter(new File("output.txt"));
		int[] in = {78, 39, 33, 93, 28,-23, 40, 5, 33, 14, 19};
		double[] db = {78.3, 15.7, 105.2, 55.8, 18.7, 42.7, 16.9, 26.7, 42.7, 88.4, 95.8};
		char[] ch = {'D', 'Z', 'A', 'Q', 'M', '$', '3', 'U', 'A', '&', '('};
		
		System.out.println("Printing Tree inOrder, preOrder, and then in postOrder");
		System.out.println("\n\nForming and Printing Integer BST:\n\n");
		for(int i=0;i<in.length;i++){
			bst.add(in[i]);
		}
		bst.printAll();
		System.out.println("\n\nForming and Printing Double BST:\n\n");
		binaryTree<Double> bst2 = new binaryTree<Double>();
		for(int i=0;i<db.length;i++){
			bst2.add(db[i]);
		}
		bst2.printAll();
		System.out.println("\n\nForming and Printing Char BST:\n\n");
		binaryTree<Character> bst3 = new binaryTree<Character>();
		for(int i=0;i<ch.length;i++){
			bst3.add(ch[i]);
		}
		printAll(bst3);
		
		
	}
	
	public static void printAll(binaryTree<Character> bst){
		LinkManager<Character> list = new LinkManager<Character>();
		Iterator<Character> ptr=list.iterator();
		list=bst.inOrder(bst.root);
		ptr=list.iterator();
		System.out.println("InOrder:");
		while(ptr.hasNext()){
			System.out.print(ptr.next()+" ");
		}
		list=bst.preOrder(bst.root);
		ptr=list.iterator();
		System.out.println("\nPreOrder:");
		while(ptr.hasNext()){
			System.out.print(ptr.next()+" ");
		}
		list=bst.postOrder(bst.root);
		ptr=list.iterator();
		System.out.println("\nPostOrder:");
		while(ptr.hasNext()){
			System.out.print(ptr.next()+" ");
		}
	}

}
