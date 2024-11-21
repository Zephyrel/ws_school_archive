import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import school.*;
import linkedLists.SingleLinkedList;

public class Hmwk5 {
	public static void main(String[] args){
		Connection conn = null;
		SingleLinkedList<School> linkList = new SingleLinkedList<School>();
		String state = "'WY'";
		String find = "";
		if(args.length>=1){	state="'"+args[0]+"'";	}
		if(args.length>=2){
			find=args[1];
			System.out.println("Target:\t"+find);
		}
		try{
			conn = DriverManager.getConnection("jdbc:mysql://REDACTED/REDACTED?" +
		             "user=REDACTED&password=REDACTED");
			String query = "SELECT instnm, city, stabbr FROM post WHERE stabbr LIKE "+state+" ORDER BY instnm ASC";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				String instnm = rs.getString("instnm");
				String city = rs.getString("city");
				String stabbr = rs.getString("stabbr");
				School n = new School(instnm, city, stabbr);
				linkList.addInOrder(n);
			}
			
			for(int i=0;i<linkList.size();i++){
				System.out.print(linkList.get(i).toString());
			}
			
			if(!find.isEmpty()){
				Iterator<School> pointer = linkList.iterator();
				System.out.println("\nAttempting to find target "+find+" . . .");
				for(int i=0;i<linkList.size();i++){
					if(pointer.hasNext()){
						School tmp = pointer.next();
						if(tmp.getSchool().equals(find)){
							System.out.println("\nTarget found!\n"+tmp.toString());
							i=linkList.size()+1;
						}else{
							pointer.next();
						}
					}else{
						System.out.println("\nAll rows exhausted. Target not found.");
					}
				}
				
			}
			st.close();
			conn.close();
		}catch(SQLException err){
			System.out.println("SQLException: " + err.getMessage());
			System.out.println("SQLState: " + err.getSQLState());
			System.out.println("VendorError: " + err.getErrorCode());
		}
	}
}
