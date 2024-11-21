package CSC346Midterm;

import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

/*	Author: Russell Ferguson
 *	Class:	CSC346 - Data Exchange Technologies
 *	Date:	March 4th, 2018
 */

public class CSC346Midterm{
	
	static String host = "https://REDACTED/schedule/Default.asp?tck=201910";
	static Response response;
	static Document doc = new Document("https://REDACTED/schedule/Default.asp?tck=201910");
	static String[] departments;
	static String[] subjects;
	static Connection conn = null;
	static ResultSet rs;
	static Statement st;
	
	public static void main (String[] args) {
		
		try{
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
            System.err.println("Oops. Class sqlite-jdbc not found. How could you let this happen?");
            System.err.println(e.getMessage());
            System.exit(0);
		}
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:CSC346Midterm_RussellFerguson.db");
            st = (Statement) conn.createStatement();
		} catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Oops. Failed to connect to database!");
            System.err.println(e.getMessage());
            System.exit(1);
        }
		try {
			response =
                Jsoup.connect(host)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .method(Method.POST)
                .data("course_number", "")
                .data("subject", "ALL")
                .data("department", "ALL")
                .data("display_closed", "YES")
                .data("course_type", "ALL")
                .followRedirects(true)
                .execute();
			doc = response.parse();
			//System.out.println("JSoup sucessfully connected and parsed!");
		}catch(IOException e) {
			e.printStackTrace();
            System.err.println("Oops.  Failed to connect to host:" + host);
            System.err.println(e.getMessage());
            System.exit(1);
		}
        
        //parse the document from response
		Elements department = doc.getElementById("department").children();
		Elements subject = doc.getElementById("subject").children();
		departments = new String[department.size()];
		subjects = new String[subject.size()];
		
		for(int i=0; i<department.size();i++) {
			departments[i] = department.get(i).val();
		}
		for(int i=0; i<subject.size();i++) {
			subjects[i] = subject.get(i).val();
		}
		
		//clean the databases of preexisting rows, remake the database with new rows (excluding the ALL value for both), and print report!
		clean();
		remake();
		report(0);
		shell();
		try {
			conn.close();
		} catch (SQLException e) {
			 e.printStackTrace();
	            System.err.println("Oops. Failed to connect to and close database. Why. How?");
	            System.err.println(e.getMessage());
	            System.exit(-1);
		}
	
	}
	//Super basic shell
	public static void shell() {
		Scanner shell = new Scanner(System.in);
		boolean exit = false;
		System.out.println("\n\nAnything else more that you'd like to do? (y/n)");
		while(!exit) {
			System.out.print("\n> ");
			String input = shell.next();
			if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) exit = true;
			else if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
				System.out.println("OK. Entering Shell. Commands are \".exit\", \".report\", \".remake\", and \".clean\".\n");
				while(!exit) {
					System.out.print("\n> ");
					input = shell.next();
					if(input.equalsIgnoreCase(".exit")) exit = true;
					if(input.equalsIgnoreCase(".report")) report(0);
					if(input.equalsIgnoreCase(".report 1")) report(1);
					if(input.equalsIgnoreCase(".report 2")) report(2);
					if(input.equalsIgnoreCase(".remake")) remake();
					if(input.equalsIgnoreCase(".clean")) clean();
				}
			}
		}
		
		shell.close();
	}
	public static void clean() {
		 //clean database of old values, optimize space
        try {
			st.executeUpdate
			(
				"DELETE FROM departments;"+
				"DELETE FROM subjects;"+
				"VACUUM;"
			);
		} catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Oops. Failed to connect to and clean database!");
            System.err.println(e.getMessage());
            System.exit(1);
        }
	}
	public static void remake() {
		try {
            //set database with all new rows
            for(int i=1; i<departments.length;i++) {
            	 st.executeUpdate
                 (
                 	"INSERT INTO departments ( "+
                 			"ID, "+
                 			"NAME"+
                 	") VALUES ("+
                 		i+","+
                 		"'"+departments[i]+"'"+
                 	");"
                 );
            }
            for(int i=1; i<subjects.length;i++) {
            	 st.executeUpdate
                 (
                      	"INSERT INTO subjects ( "+
                     			"ID, "+
                     			"NAME"+
                     	") VALUES ("+
                     		i+","+
                     		"'"+subjects[i]+"'"+
                     	");"
                 );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Oops. Failed to connect to database!");
            System.err.println(e.getMessage());
            System.exit(1);
        }
	}
	//Print report on databases
	public static void report(int which) {
		try {
			System.out.println("*******************************\nReport\n*******************************");
            
			
            if (which == 0 || which == 1) {
				st.execute("SELECT * FROM departments");
				System.out.println("===============================\nDepartments\n===============================");
				System.out.printf("%3s\t\t%4s\n", "ID", "NAME");
				rs = st.getResultSet();
				while (rs.next()) {
					System.out.printf("\n%3s", rs.getString("id"));
					System.out.printf("\t\t%4s", rs.getString("name"));
				} 
			}
            
			if (which == 0 || which == 2) {
				st.execute("SELECT * FROM subjects");
				System.out.println("\n\n===============================\nSubjects\n===============================");
				System.out.printf("%3s\t\t%4s\n", "ID", "NAME");
				rs = st.getResultSet();
				while (rs.next()) {
					System.out.printf("\n%3s", rs.getString("id"));
					System.out.printf("\t\t%4s", rs.getString("name"));
				} 
			}
			System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Oops. Failed to connect to and print report on database!");
            System.err.println(e.getMessage());
            System.exit(1);
        }
	}
}

/*
Notes
All dropboxes and their values are within form id="scheduleform"
DEPARTMENT values
<select id="department" name="department">
	<option value="ALL">All Departments</option>
	...
</select>

SUBJECT VALUES
<select name="subject" id="subject">
	<option value="ALL">All Subjects</option>
	...
</select>
*/