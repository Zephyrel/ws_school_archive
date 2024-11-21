import cards.*;
import linkedLists.*;

public class Hmwk4 {
	public static void main(String[] args){
		SingleLinkedList<Integer> list = new SingleLinkedList<Integer>(3);
		list.addFirst(2);
		list.addFirst(1);
		list.addInOrder(4);
		list.addInOrder(5);
		list.addInOrder(6);
		int j =list.findIndex(2);
		list.remove(2);
		list.remove(2);
		list.remove(3);
		for(int i=0;i<list.size();i++){
			int a = list.get(i);
			System.out.print(a);
		}
		System.out.print(" "+list.find(2));
		System.out.print(" "+list.getLast());
		System.out.print(list.get(1));
		System.out.println(list.getFirst());
		System.out.println(list.toString());
	}
}
