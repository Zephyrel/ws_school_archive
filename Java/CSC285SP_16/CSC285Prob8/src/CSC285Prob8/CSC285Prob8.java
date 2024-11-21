package CSC285Prob8;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import CSC285Prob8.LinkManager;
import CSC285Prob8.binaryTree;

public class CSC285Prob8 {
	public static void main(String[] args) throws Exception{
		/*	   ----78----
		   --39--	   ---105---
		 -28-   52	 -85-     110
	  -14-  33-		80  92-
	  4 16    35		 97-
		 				   99
		 */
		binaryTree<Integer> bst = new binaryTree<Integer>();
		//String s = "";
		//PrintWriter out;
		//out=new PrintWriter(new File("output.txt"));
		int[] input = {78, 39, 52, 28, 33, 14, 16, 4, 35, 105, 85, 92, 80, 97, 110, 99};
		for(int i=0;i<input.length;i++){
			bst.add(input[i]);
		}
		System.out.println("Printing Tree inOrder, preOrder, and then in postOrder");
		printAll(bst);
		System.out.println("\n\nDeleting 28\n\n");
		bst.delete(bst.root, 28);
		printAll(bst);
		System.out.println("\n\nDeleting 105\n\n");
		bst.delete(bst.root, 105);
		printAll(bst);
		System.out.println("\n\nDeleting 110\n\n");
		bst.delete(bst.root, 110);
		printAll(bst);
		
		
	}

	public static void printAll(binaryTree<Integer> bst){
		LinkManager<Integer> list = new LinkManager<Integer>();
		Iterator<Integer> ptr=list.iterator();
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
