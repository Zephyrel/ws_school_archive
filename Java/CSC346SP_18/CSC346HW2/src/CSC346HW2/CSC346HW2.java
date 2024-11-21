package CSC346HW2;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*	Author: Russell Ferguson
 *	Class:	CSC346 - Data Exchange Technologies
 *	Date:	February 9th, 2018
 */

public class CSC346HW2 {

	static double centerLat;
	static double centerLon;
	static double mileRadius;
	
    public static void main(String[] args) {
	    String host = "jdbc:mysql://REDACTED:port/REDACTED";
	    String user = "REDACTED";
	    String password="REDACTED";

	    Connection conn;
	    Statement st;
	    ResultSet rs;
	    
	    Scanner scanner = new Scanner(System.in);
	    String zip = "";
		String radiusString = "";
		//The center from which we set our radius on, based on the zip code.
        
		System.out.println("Enter a 5-digit zip code:\n");
		zip = Integer.toString(scanner.nextInt());
		System.out.println("Enter the mile radius of this location you wish to search for locations in:\n");
		radiusString = Integer.toString(scanner.nextInt());
		mileRadius = Double.parseDouble(radiusString);
		double kiloRadius = miToKilo(mileRadius);
		String latString = "";
		String lonString = "";
		scanner.close();
	    
        try {
            conn = DriverManager.getConnection(host, user, password);
            String queryString = "SELECT location, city, state_prefix, zip_code, lat, lon FROM zips WHERE zip_code='"+zip+"'";
            st = (Statement) conn.createStatement();
            st.execute(queryString);
            rs = st.getResultSet();

            while(rs.next()){
                latString = rs.getString("lat");
                lonString = rs.getString("lon");
            }
            
            st = (Statement) conn.createStatement();
            //math to figure out the search radius range out from the radius
            centerLat = Double.parseDouble(latString);
        	centerLon = Double.parseDouble(lonString);
        	queryString = "SELECT location, city, state_prefix, zip_code, lat, lon FROM zips";
        	System.out.println(queryString);
        	
			st.execute(queryString);
			rs = st.getResultSet();
			
			double d = 0;
            while(rs.next()){
            	latString = rs.getString("lat");
                lonString = rs.getString("lon");
                String city = rs.getString("city");
                String state = rs.getString("state_prefix");
                zip = rs.getString("zip_code");
                d = haversineDistance(Double.parseDouble(latString), Double.parseDouble(lonString));
                if(d < kiloRadius) {
	                	
	                System.out.printf("%20s", city);
	                System.out.printf("     \t%4s", state);
	                System.out.printf(", %10s", zip);
	                System.out.printf("\t%s", latString);
	                System.out.printf("\t%s\n", lonString);
                }
            }


            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Oops.  Failed to connect to " + host);
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    static double miToKilo (double mi) {
    	return (mi*1.609344);
    }
    static double kiloToMi (double kilo) {
    	return (kilo*0.62137119223733);
    }
    static double degToRad (double deg) {
    	return (deg*(Math.PI/180));
    }
    static double radToDeg (double rad) {
    	return (rad*(180/Math.PI));
    }
    static double haversineDistance(double lat2, double lon2) {
    	//centerLat = 39.76;
    	//centerLon = -94.84;
    	
    	double R = 6371e3; // metres
    	double φ1 = degToRad(centerLat);
    	double φ2 = degToRad(lat2);
    	double deltaLat = degToRad(lat2-centerLat);
    	double deltaLon = degToRad(lon2-centerLon);

    	double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
    	        Math.cos(φ1) * Math.cos(φ2) *
    	        Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    	double d = R * c;	//this distance is in meters
    	return d/1000;		//return in kilometers
    }
    
}