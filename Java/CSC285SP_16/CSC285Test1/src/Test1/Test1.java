package Test1;
import Test1.Badmin;
import Test1.Bsupport;
import Test1.Bnurse;
import Test1.Bdoctor;
import Test1.Dadmin;
import Test1.Dsupport;
import Test1.Dnurse;
import Test1.Ddoctor;
import Test1.LAadmin;
import Test1.LAsupport;
import Test1.LAnurse;
import Test1.LAdoctor;
import Test1.SJadmin;
import Test1.SJsupport;
import Test1.SJnurse;
import Test1.SJdoctor;
import Test1.WLFUManager;
//Sweet Mother of mercy that took awhile
/*Author:	Russell Ferguson
 *Date:		February 29, 2016
 *Work:		CSC285 - Test 1
 *Professor:Kent Pickett
 */

public class Test1 {
	public static void main(String[] args){
		WLFUManager<Employees> manager = new WLFUManager<Employees>();
		
		manager.Add(new Bdoctor("IM Bones", "455657890", "AA", 100));
		manager.Add(new Bnurse("UR Temp", "789302345", "CA", 3));
		manager.Add(new Bdoctor("DVM Jordan", "786456712", "CA", 120));
		manager.Add(new Badmin("IM Boss", "543126787", "HS", 1));
		for(int i=0;i<manager.ManagedList.size();i++){
			manager.Get(i).print();
		}
		manager.SortSalary();
		for(int i=0;i<manager.ManagedList.size();i++){
			manager.Get(i).print();
		}
		

	}
}
