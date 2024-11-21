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
		public boolean hasChild(){
			if(this.left==null&&this.right==null)return false;
			else return true;
		}
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
		Node<T> copy = parent;
		//The parent is, as we move down this function recursively over the BST
		//The parent of the node that we want to actually delete
		//if it's the very top root node
		if(root==null){return;}
		else if(t.compareTo(root.data)==0&&(t.compareTo(parent.data)==0)){
			deleteHelper(root, t);
		}
		//normal handling.
		//This looks to the root node left of the parent
		if(t.compareTo(parent.data)<0){
			//target is parent.left
			if(t.compareTo(parent.left.data)==0){
				if(!parent.left.hasChild()){
					parent.left=null;
				}else if(parent.left.left==null && parent.left.right!=null){
					parent.left=parent.left.right;
				}else if(parent.left.left!=null && parent.left.right==null){
					parent.left=parent.left.left;
				}else{
					Node<T> tmp = parent.left.left;
					parent.left=deleteHelper(parent.left.right, t);
					parent.left.left=tmp;
				}
			}else{
				//move to the left
				delete(parent.left, t);
			}
		//This looks to the root node right of the parent
		}else if(t.compareTo(parent.data)>0){
			if(t.compareTo(parent.right.data)==0){
				if(!parent.right.hasChild()){
					parent.right=null;
				}else if(parent.right.left==null && parent.right.right!=null){
					parent.right=parent.right.right;
				}else if(parent.right.left!=null && parent.right.right==null){
					parent.right=parent.right.left;
				}else{
					Node<T> tmp = parent.right.left;
					parent.right=deleteHelper(parent.right.right, t);
					parent.right.left=tmp;
				}
			}else{
				//move down to the right
				delete(parent.right, t);
			}
		}
	}
	//starts at Node to be deleted, returns replacement
	private Node<T> deleteHelper(Node<T> node, T t){
		Node<T> replacement = node;
		//Node<T> attach = node;
		//There's a node to the right, if it has children, move there, otherwise it is replacement

		if(node.left!=null){
			replacement=deleteHelper(node.left, t);
			
			/*replacement=node.right;
			replacement.left=node.left;
		}else if(node.right.left!=null){
			replacement=deleteHelper(node.right, t);*/
		}
		return replacement;
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
	private void inOrderHelper(Node<T> node){
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
	private void postOrderHelper(Node<T> node){
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
	private void preOrderHelper(Node<T> node){
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
