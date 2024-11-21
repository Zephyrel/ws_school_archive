package CSC285Prob8;


public class binaryTree<T extends Comparable<T>> {
	//basic Node class structure
	private static class Node<T extends Comparable<T>>{
		private T data;
		private Node<T> left;
		private Node<T> right;
		public Node()	{data=null;left=null;right=null;}
		public Node(T t){data=t;left=null;right=null;}
		public Node(T t,Node<T> l, Node<T> r){data=t;left=l;right=r;}
	}
	
	protected Node<T> root;	//the topmost node, the root.
	LinkManager<T> orderedList = new LinkManager<T>();
	//Constructors
	public binaryTree(){
		root=null;
	}
	public binaryTree(T t){
		root.data=t;
	}
	//Methods
	//Insert. DONE!
	public void insert(Node<T> rootNode, T t){
		if(t.compareTo(rootNode.data)<0){
			if(rootNode.left != null){
				insert(rootNode.left, t);
			}else{
				rootNode.left = new Node<T>(t);
			}
		}else if(t.compareTo(rootNode.data)>0){
			if(rootNode.right != null){
				insert(rootNode.right,t);
			}else{
				rootNode.right = new Node<T>(t);
			}
		}else if(t.compareTo(rootNode.data)==0)System.out.println("HEY. NO DUPLICATES ALLOWED IN A BST, PUNK!");
	}
	//Add. DONE!
	public void add(T t){
		if(root==null){
			root=new Node<T>(t);
		}
		else insert(root, t);
	}
	//Delete
	public void delete(Node<T> parent, T t){
		//if it's the very top root node
		if(root==null){return;}
		else if(t.compareTo(root.data)==0&&(t.compareTo(parent.data)==0)){
			deleteHelper(root, t);
		}
		//normal handling.
		//This looks to the root node left of the parent
		if(t.compareTo(parent.data)<0){
			if(t.compareTo(parent.left.data)==0){
				parent.left=deleteHelper(parent.left, t);
			}else{
				delete(parent.left, t);
			}
		//This looks to the root node right of the parent
		}else if(t.compareTo(parent.data)>0){
			if(t.compareTo(parent.right.data)==0){
				parent.right=deleteHelper(parent.right, t);
			}else{
				delete(parent.right, t);
			}
		}
	}
	//starts at Node to be deleted, returns replacement
	private Node<T> deleteHelper(Node<T> node, T t){
		Node<T> replacement = node;
		if(node.right==null){
			replacement=node.left;
		}else{
			while(node.right!=null){
				replacement=deleteHelper();
				if(replacement.right==null&&replacement.left!=null){
					
				}
			}
		}
		return replacement;
	}
	private Node<T> findMinRight(Node<T> node){

		return node;
	}
	private Node<T> findMinLeft(Node<T> node){

		return node;
	}
	//Recursive Find method. Done!
	public Node<T> find(Node<T> scan, T t){
		int comparator=t.compareTo(scan.data);
		//found node, return node
		if(comparator == 0)return scan;
		
		if(comparator<0){	//t < Node, go left
			if(scan.left==null)return null;
			else return find(scan.left, t);
		}else{	//else, t > Node, go right
			if(scan.right==null)return null;
			else return find(scan.right, t);
		}
	}	
	//Contains. Done!
	public boolean contains(T t){
		if(root==null) return false;
		else return (find(root, t) != null);
	}
	//Traversal and Representation
	//InOrder (left, root, right)
	public void inOrderHelper(Node<T> node){
		if(node.left!=null){
			inOrderHelper(node.left);
		}
		orderedList.push(node.data);
		if(node.right!=null){
			inOrderHelper(node.right);
		}
	}
	public LinkManager<T> inOrder(Node<T> node){
		orderedList=new LinkManager<T>();
		if(node!=null){
			inOrderHelper(node);
		}
		return orderedList;
	}
	//PostOrder (left, right, root)
	public void postOrderHelper(Node<T> node){
		if(node.left!=null){
			postOrderHelper(node.left);
		}
		if(node.right!=null){
			postOrderHelper(node.right);
		}
		orderedList.push(node.data);
	}
	public LinkManager<T> postOrder(Node<T> node){
		orderedList=new LinkManager<T>();
		if(node!=null){
			postOrderHelper(node);
		}
		return orderedList;
	}

	//PreOrder (root, left, right)
	public void preOrderHelper(Node<T> node){
		orderedList.push(node.data);
		if(node.left!=null){
			preOrderHelper(node.left);
		}
		if(node.right!=null){
			preOrderHelper(node.right);
		}
	}
	public LinkManager<T> preOrder(Node<T> node){
		orderedList=new LinkManager<T>();
		if(node!=null){
			preOrderHelper(node);
		}
		return orderedList;
	}
}
