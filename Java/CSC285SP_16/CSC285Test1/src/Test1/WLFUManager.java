package Test1;
import Test1.Employees;
import java.util.ArrayList;

public class WLFUManager<T> implements Comparable<Employees> {
	ArrayList<Employees> ManagedList = new ArrayList<Employees>();
	public int Add(Employees x){
		ManagedList.add(x);
		return ManagedList.size();
	}
	Employees Get(int x){
		return ManagedList.get(x);
	}
	void SortSalary(){
		System.out.println("---------------------------------------------");
		System.out.println("WLFU Manager is sorting by Base Salary now...");
		System.out.println("---------------------------------------------");
		for(int i=0;i<ManagedList.size()-1;i++){
			for(int j=1;j<ManagedList.size()-i;j++){
				Employees tmp = ManagedList.get(j-1);
				Employees tmp2 = ManagedList.get(j);
				if((tmp2.getSalary()-tmp.getSalary())>0){
					ManagedList.set(j-1,tmp2);
					ManagedList.set(j, tmp);
				}
			}				
		}
	}
	@Override
	public int compareTo(Employees o) {
		
		return 0;
	}

}
