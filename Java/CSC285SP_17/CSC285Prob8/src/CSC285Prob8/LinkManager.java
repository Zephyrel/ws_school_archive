package CSC285Prob8;


import java.io.Closeable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

//Doubly Linked List Manager of Generic Type T
//A look at Java's implemntation structure below
//https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
public class LinkManager<T> implements Iterable<T>{
	//basic LinkedList data needed
	private int size;
	private Node head;
	private Node tail;
	
	private class Node{	//basic Node class structure
		private T data;
		private Node next;
		private Node prev;
		public Node()	{data=null;next=null;prev=null;	}
		public Node(T t){data=t;next=null;prev=null;	}
	}
	
	public LinkManager(){ //Constructor
		head = new Node();
		tail = new Node();
		head.next=tail;
		tail.prev=head;
	}
	//LinkManager methods, isEmpty, size, add, and @Override iterator
	//adds Node to end of list, becoming new Tail
	public void add(T t){	//DONE
		Node last = tail;
		Node newNode = new Node(t);
		newNode.prev=last;
		tail=newNode;
		last.next=tail;
		size++;
	}
	public void addIn(int index, T t){	//DONE
		if(index>size()||index<0) throw new IllegalStateException("Index value out of acceptable bounds for addIn() call!");
		ListIterator<T> pointer = iterator();
		int i=0;
		while(pointer.hasNext() && i!=pointer.nextIndex()){
			pointer.next();
		}
	}
	public void addFirst(T t){	//DONE
		Node first = head;
		Node newNode = new Node(t);
		first.prev=newNode;
		newNode.next=first;
		head=newNode;
		size++;
	}
	public T get(int index){
		if(index>size()||index<0) throw new IllegalStateException("Index value out of acceptable bounds for get() call!");
		ListIterator<T> pointer = iterator();
		int i=0;
		while(i!=pointer.nextIndex()&&i<size()){pointer.next();i++;}
		return pointer.next();
	}
	public T getFirst(){return head.data;}
	public T getLast(){return tail.data;}
	
	public T poll(){//retrieve and removes head
		return removeFirst();
	}
	public T pollFirst(){if(size==0)return null;else{T tmp=removeFirst();return tmp;}}
	public T pollLast()	{if(size==0)return null;else{T tmp=removeLast();return tmp;}}
	public T pop()		{return removeFirst();}	//equivalent to removeFirst();
	public void push(T t){addFirst(t);} //equivalent to addFirst(T)
	public T peek()		{return head.data;}
	public T peek(int index){
		if(index>size()||index<0) throw new IllegalStateException("Index value out of acceptable bounds for remove() call!");
		ListIterator<T> pointer = iterator();
		do{
			pointer.next();
		}while(index!=pointer.previousIndex());
		return pointer.previous();
		}
	public T peekFirst(){if(size==0)return null;else return head.data;}
	public T peekLast()	{if(size==0)return null;else return tail.data;}
	public T remove()	{return removeFirst();}
	public T remove(int index){
		if(index>size()||index<0) throw new IllegalStateException("Index value out of acceptable bounds for remove() call!");
		ListIterator<T> pointer = iterator();
		do{
			pointer.next();
		}while(index!=pointer.previousIndex());
		T data=pointer.previous();
		pointer.remove();
		return data;
	}
	public T removeFirst(){
		Node x = head;
		head=head.next;
		head.prev=null;
		size--;
		return x.data;
	}
	public T removeLast(){
		Node x = tail;
		tail=tail.prev;
		tail.next=null;
		size--;
		return x.data;
	}
	public void set(int index, T newData){
		if(index>size()) throw new IllegalStateException();
		ListIterator<T> pointer = iterator();
		do{//we need to ensure that pointer.next() is ran at least once so we can use pointer.set()
			//this covers cases of index==0 and index>0, thus all cases
			pointer.next();
		}while(index!=pointer.previousIndex());
		pointer.set(newData);
	}
	public boolean isEmpty(){	return size==0;	}
	public int size()		{	return size;	}
	public ListIterator<T> iterator(){	return new LinkIterator();	}
	
	//subClass ListIterator for traversing back and forth through the list
	//https://docs.oracle.com/javase/7/docs/api/java/util/ListIterator.html
	//Implement LAST
	private class LinkIterator implements ListIterator<T>{
		//we place our "cursor" at the head of the node; from top to bottom, current node at head, aka right in front of the cursor
		// (CURSOR@index 0) head0 (index1) node1 (index2) node2...
		private Node current = head;
		private Node lastAccessed = null;	//node last accessed and returned through next or previous. Reset to null after remove or add
		private int index = 0;

		public boolean hasNext()	{return index<size;	}
		public boolean hasPrevious(){return index>0;	}
		public int nextIndex()		{return index;		}
		public int previousIndex()	{return index-1;	}
		
		// (Note that alternating calls to next and previous will return the same element repeatedly.)
		public T next(){	//Returns the next element in the list and advances the cursor position.
			if(!hasNext()) throw new NoSuchElementException();
			//cursor position
			//nodeX ^current^ nodeZ nodeY goes to
			//nodeX ^lastAccessed^ nodeZ ^current^ nodeY
			//nodeZ is returned
			lastAccessed = current;
			current = current.next;
			index++;
			return lastAccessed.data;
		}

		public T previous(){	//Returns the previous element in the list and moves the cursor position backwards.
			if(!hasPrevious()) throw new NoSuchElementException();
			//cursor position
			//nodeX ^lastAccessed^^current^ nodeZ nodeY
			//nodeZ is returned
			lastAccessed = current.prev;
			current = current.prev;
			index--;
			return lastAccessed.data;
		}

		//Removes from the list the last element that was returned by next() or previous() (optional operation).
		//no calls to remove() or add() after last call to next() or previous()
		public void remove(){
			if(lastAccessed == null) throw new IllegalStateException();
			Node x = lastAccessed.prev;
			Node y = lastAccessed.next;
			x.next = y;
			y.prev = x;
			size--;
			//if used after previous(), where the current and lastAccessed position/node is equal
			//shift the current position up from the deleted node to nodeY
			if(current == lastAccessed)
				current = y;
			else	//used for next(), the deleted node is gone and the node at current is shifted back an index
				index--;
			lastAccessed = null;
		}
		//Replaces the last element returned by next() or previous() with the specified element (optional operation).
		public void set(T newData){
			if(lastAccessed == null) throw new IllegalStateException();
			lastAccessed.data = newData;
		}
		//no calls to remove() or add() after last call to next() or previous()
		public void add(T newData){
			Node x = current.prev;
			Node y = new Node();
			Node z = current;
			y.data = newData;
			x.next = y;
			y.next = z;
			z.prev = y;
			y.prev = x;
			size++;
			index++;
			lastAccessed = null;
		}
	}
}
