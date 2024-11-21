package linkedLists;

import java.util.Iterator;
import java.util.NoSuchElementException;

//
//	Author:			Russell Ferguson, Jr.
//	Date:			October 11, 2016
//	Description:	A simple and full-featured SingleLinkedList implementation.
//

public class SingleLinkedList<E> implements Iterable<E> {
	//base
	private Node head;
	private int size;

	//Node class and constructors
	public class Node{
		E data;
		Node next;
		public Node(){
			data=null;
			next=null;
		}
		public Node(E data){
			this.data=data;
			this.next=null;
		}
		public Node(E data, Node next){
			this.data=data;
			this.next=next;
		}
	}
	
	//List constructors
	public SingleLinkedList(){
		head = new Node();
		size=0;
	}
	public SingleLinkedList(E data){
		this.head=new Node(data);
		size=1;
	}
	public SingleLinkedList(Node head){
		this.head=head;
		size=1;
	}
	
	//getters
	public E getFirst(){	//DONE
		return head.data;
	}
	//get is with an index
	public E get(int index){
		if(index>size()||index<0) throw new IllegalStateException("Index value out of acceptable bounds for get() call!");
		Iterator<E> pointer = iterator();
		int i=0;
		while(i!=index){pointer.next();i++;}
		return pointer.next();
	}

	public E getLast(){
		Iterator<E> pointer = iterator();
		if(size==1)	{return head.data;}
		int i=1;
		E ptr = pointer.next();
		while(i<size){ptr=pointer.next();i++;}
		return ptr;
	}
	
	//add
	public void addFirst(E data){
		Node add = new Node(data);
		this.head=add;
		size++;
	}
	
	public void addInOrder(E data){
		if(size==0){
			addFirst(data);
		}else{
			Node tmp = head;
			while(tmp.next!=null){
				tmp=tmp.next;
			}
			tmp.next = new Node(data);
			size++;
		}
	}
	//delete Head
	public E remove()	{return removeFirst();}
	//3
	// NODE1 NODE2 NODE3
	public void remove(int index){ //use Index from findIndex!
		// ^Cursor^ NODE0 NODE1 NODE2(TARGET)
		Iterator<E> pointer = iterator();
		int i = 0;
		do{
			pointer.next();
			i++;
		}while(i!=index);
		pointer.remove();
		size--;
	}
	public E removeFirst(){
		E x = head.data;
		head=head.next;
		size--;
		return x;
	}
	//find is with a target
	public E find(E target){
		Iterator<E> pointer = iterator();
		int i=0;
		if(target==head.data)	{return head.data;}
		E ptr = pointer.next();
		while(i!=size&&target!=ptr)	{ptr=pointer.next();i++;}
		return ptr;
	}
	//Index of LinkedList always begins at 0
	public int findIndex(E target){
		Iterator<E> pointer = iterator();
		int i=0;
		if(target==head.data)	{return 0;}
		while(i!=size&&target!=pointer.next())	{i++;}
		return i;
	}

	public boolean isEmpty(){	return size==0;	}
	public int size()		{	return size;	}
	public Iterator<E> iterator(){	return new SingleLinkIterator();	}
	public String toString(){
		String a = "";
		for(int i=0;i<size();i++){
			a += (get(i)+" --> ");
		}
		a+="\n";
		return a;
	}

	private class SingleLinkIterator implements Iterator<E>{
		//we place our "cursor" at the head of the node; from top to bottom, current node at head, aka right in front of the cursor
		// (CURSOR@index 0) head0 (index1) node1 (index2) node2...
		private Node nextNode = head;
		private Node lastAccessed = null;
		private int index = 0;

		public boolean hasNext()	{return index<size;	}
		
		// (Note that alternating calls to next and previous will return the same element repeatedly.)
		public E next(){	//Returns the next element in the list and advances the cursor position.
			if(!hasNext()) throw new NoSuchElementException();
			E data = nextNode.data;
			lastAccessed = nextNode;
			nextNode = nextNode.next;
			index++;
			return data;
		}
		public void remove(){
			if(lastAccessed==null) throw new IllegalStateException();
			lastAccessed.next=nextNode.next;
			nextNode=lastAccessed.next;
		}
	}
}

/* Example Test Code
 * public static void main(String[] args){
		SingleLinkedList<String> list = new SingleLinkedList<String>("C");
		list.addFirst("B");
		list.addFirst("A");
		list.addInOrder("D");
		list.addInOrder("E");
		list.addInOrder("F");
		int j =list.findIndex("B");
		list.remove(2);
		list.remove(2);
		list.remove(3);
		for(int i=0;i<list.size();i++){
			String a = list.get(i);
			System.out.print(a);
		}
		System.out.print(list.find("B"));
		System.out.print(" "+list.getLast());
		System.out.print(list.get(1));
		System.out.print(list.getFirst());
	}
*/
