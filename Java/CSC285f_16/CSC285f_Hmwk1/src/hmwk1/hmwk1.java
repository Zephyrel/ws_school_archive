package hmwk1;

import java.sql.*;

/*
 * Author: Russell Ferguson
 * September 5, 2016
 * Missouri Western State Uni. CSC285 Fall 2016
 */

public class hmwk1 {
	public static void main(String[] args){
		Connection conn = null;
		

		try{
			//Class.forName(com.mysql.jdbc);
			conn = DriverManager.getConnection("jdbc:mysql://REDACTED/REDACTED?" +
		             "user=REDACTED&password=REDACTED");
			String query = "SELECT givenname, surname, city, zip FROM people WHERE zip LIKE '6450%' AND city LIKE '%Joseph' ORDER BY surname";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			System.out.println("\tName\t\t\tCity\tZip\n---------------------------------------------");
			while(rs.next()){
				String givenname = rs.getString("givenname");
				String surname = rs.getString("surname");
				String city = rs.getString("city");
				int zip = rs.getInt("zip");
				
				System.out.format("%12s %10s,%14s,%6d\n", givenname, surname, city, zip);
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