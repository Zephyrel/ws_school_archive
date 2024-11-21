package sortAlgorithms;

import java.util.ArrayList;

public class sortAlgorithms {
	public static void SortAsc( ArrayList<T> x){	//DONE
		//Bubble Sort Ascending (Greatest to Least)
		for(int i=0;i<T.size()-1;i++){
			for(int j=1;j<AcademicClass.size()-i;j++){
				Student tmp = AcademicClass.get(j-1);
				Student tmp2 = AcademicClass.get(j);
				if(tmp.getAverage()<tmp2.getAverage()){
					AcademicClass.set(j-1,tmp2);
					AcademicClass.set(j, tmp);
				}
			}				
		}
	}
}
