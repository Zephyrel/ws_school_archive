package Test1;
import Test1.Employees;
import java.util.ArrayList;

public class WLFUManager<T extends Comparable<T>>{
	ArrayList<T> ManagedList = new ArrayList<T>();
	public int Add(T x){
		ManagedList.add(x);
		return ManagedList.size();
	}
	T Get(int x){
		return ManagedList.get(x);
	}
	void Sort(){
		//System.out.println("---------------------------------------------");
		//System.out.println("WLFU Manager is sorting by Base Salary now...");
		//System.out.println("---------------------------------------------");
		for(int i=0;i<ManagedList.size()-1;i++){
			for(int j=1;j<ManagedList.size()-i;j++){
				T tmp = ManagedList.get(j-1);
				T tmp2 = ManagedList.get(j);
				if(tmp2.compareTo(tmp)>0){
					ManagedList.set(j-1,tmp2);
					ManagedList.set(j, tmp);
				}
			}				
		}
	}
	void Print(int i){
		System.out.println(Get(i));
	}
}
