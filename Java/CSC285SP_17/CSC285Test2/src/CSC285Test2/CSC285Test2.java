package CSC285Test2;

class Rocket{
	public int thr;
	public double ang;
	public Rocket(){}
	public Rocket(int a, double b){thr=a;ang=b;}
}	

public class CSC285Test2 {

	static Rocket a = new Rocket(0,12.3);
	static Rocket b = new Rocket(15000,0);
	static Rocket c = new Rocket(-2000,5.0);
	static Rocket d = new Rocket(0,-14.5);
	
	public static void main(String[] args){
		stack<Character> oprtrs = new stack<Character>();
		stack<Rocket> oprnds = new stack<Rocket>();
		
		char[] expr = "a+(d%(c+A*b))".toCharArray();
		Rocket A = new Rocket(30000,75.2);
		for(int i=0;i<expr.length;i++){
			if(Character.isAlphabetic(expr[i])){
				Rocket p = new Rocket();
				switch(expr[i]){
					case 'A': p=A; break;
					case 'a': p=a; break;
					case 'b': p=b; break;
					case 'c': p=c; break;
					case 'd': p=d; break;
				}
				oprnds.push(p);
			}else if(expr[i]=='('){oprtrs.push(expr[i]);
			}else if (expr[i]==')'){
				while(oprtrs.peek()!='('){
					evalExpr(oprnds, oprtrs);
				}
				oprtrs.pop();
			}else{
				int oprior = getPrio(expr[i]);
				while(oprtrs.peek()!=null && oprior<=getPrio(oprtrs.peek())){
					evalExpr(oprnds, oprtrs);
				}
				oprtrs.push(expr[i]);
			}
		}
		while(oprnds.size()>1){evalExpr(oprnds,oprtrs);}
		Rocket r = oprnds.peek();
		System.out.printf("Result - Thrust: %d\tAngle: %.2f", r.thr, r.ang);
	}
	public static int getPrio(char c){
		int r=-9999;
		switch(c){
			case ')': r=99; break;
			case '%': r=3; break;
			case '*': r=2; break;
			case '+': r=1; break;
			case '(': r=-99; break;
		}
		return r;
	}
	public static void evalExpr(stack<Rocket> oprnds, stack<Character> oprtrs){
		Rocket x, y, z = new Rocket();
		char oprtr = oprtrs.pop();
		x=oprnds.pop();
		y=oprnds.pop();
		z=eval(oprtr, y, x);
		oprnds.push(z);
	}
	public static Rocket eval(char z, Rocket op1, Rocket op2){
		int x=0;
		double y=0;
		Rocket r=new Rocket();
		switch(z){
		case '%': x=op2.thr;	y=op2.ang+op1.ang; break;
		case '*': x=op2.thr+op1.thr;	y=op2.ang; break;
		case '+': x=op2.thr+op1.thr;	y=op2.ang+op1.ang; break;
		}
		r = new Rocket(x,y);
		return r;
	}
}

class stack<T>{
	protected int size;
	protected Node head;
	private class Node{
		protected T data;
		protected Node next;
		public Node(){data=null;next=null;}
		public Node(T t){data=t;next=null;}
	}
	public void push(T t){
		Node first = head;
		Node newNode = new Node(t);
		newNode.next = first;
		head = newNode;
		size++;
	}
	public T peek(){if(size()==0){return null;} else return head.data;}
	public T pop(){
		Node x = head;
		head = head.next;
		size--;
		return x.data;
	}
	public int size(){return size;}
}