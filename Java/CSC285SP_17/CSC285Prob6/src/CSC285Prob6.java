
// Author:	Russell Ferguson
// CSC285 Prob6 - Kent Pickett

public class CSC285Prob6{
	public static void main(String[] args){
		int in[] = {13,-8,29,15,75,-88,19,21,17,85,12};
		bubbleSort(in);
		double db[] = {14.3,29.3,55.2,-17.8,42.1,-88.3,94.2,33.5,25.3};
		bubbleSort(db);
		
		int findIn[] = {21,75,-88,85,49};
		double findDb[] = {55.2,-17.8,94.2,25.3};
		for(int i=0;i<3;i++){
			int tmp[]={12,25,-5};
			System.out.printf("The NFactorial of the absolute value of %d is %d.\n", tmp[i], NFactorial(tmp[i]));
		}
		System.out.println();
		for(int i=0;i<4;i++){
			int tmp[] = {5,12,1,8};
			System.out.printf("The Fibonacci of the absolute value of %d is %d.\n", tmp[i], Fib(tmp[i]));
		}
		System.out.println("\nThe integer and double arrays have been sorted in ascending order.\n");
		for(int i=0;i<findIn.length;i++){
			System.out.printf("The integer %d is located at %d in Integer Array.\n", findIn[i], binarySearch(in ,findIn[i]));
		}
		System.out.println();
		for(int i=0;i<findDb.length;i++){
			System.out.printf("The integer %.1f is located at %d in Double Array.\n", findDb[i], binarySearch(db ,findDb[i]));
		}
	}
	
	public static int NFactorial(int n){
		int a=Math.abs(n);
		if(a==0 || a==1){return 1;}
		else{return a*NFactorial(a-1);}
	}
	public static int Fib(int n){
		int a=Math.abs(n);
		if(a==0){return 0;}
		else if(a==1){return 1;}
		else{return Fib(a-1)+Fib(a-2);}
	}
	
	//a is array being fed, x is target
	public static int binarySearch(double[] a, double x){ 
		return binarySearch(a, x, 0, a.length - 1);
	}
	
	//need extra low and high parameters
	private static int binarySearch(double[] a, double x, int lo, int hi){
		if(lo > hi){ return -1; }
		int mid = (lo + hi)/2;
		if(a[mid] == x){ return mid; }
		else if(a[mid] > x){ return binarySearch(a, x, mid+1, hi);}
		else{ return binarySearch(a, x, lo, mid-1); }
	}
	public static int binarySearch(int[] a, int x){ 
		return binarySearch(a, x, 0, a.length - 1);
	}
	
	//need extra low and high parameters
	private static int binarySearch(int[] a, int x, int lo, int hi){
		if(lo > hi){ return -1; }
		int mid = (lo + hi)/2;
		if(a[mid] == x){ return mid; }
		else if(a[mid] > x){ return binarySearch(a, x, mid+1, hi);}
		else{ return binarySearch(a, x, lo, mid-1); }
	}
	public static void bubbleSort(double[] t){
		for(int i=0;i<t.length-1;i++){
			for(int j=1;j<t.length-i;j++){
				if(t[j-1]<t[j]){
					double tmp=t[j-1];
					t[j-1]=t[j];
					t[j]=tmp;
				}
			}
		}
	}public static void bubbleSort(int[] t){
		for(int i=0;i<t.length-1;i++){
			for(int j=1;j<t.length-i;j++){
				if(t[j-1]<t[j]){
					int tmp=t[j-1];
					t[j-1]=t[j];
					t[j]=tmp;
				}
			}
		}
	}
}
