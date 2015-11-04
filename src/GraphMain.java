import java.util.Scanner;
import java.sql.*;

public class GraphMain {
		 public static void main(String[] args) {
			 
		        int choice;
		        String query = "";
		        
		        Scanner inInt = new Scanner(System.in);
		        Scanner inString = new Scanner(System.in);
			    System.out.println("============================");
			    System.out.println("|   Graphing SQL Data      |");
			    System.out.println("============================");
			    System.out.println("| Options:                 |");
			    System.out.println("|        1. Pie            |");
			    System.out.println("|        2. Histogram      |");
			    System.out.println("|        3. Bar            |");
			    System.out.println("|        4. Scatter        |");
			    System.out.println("|        5. Exit           |");
			    System.out.println("============================");
			    
			    System.out.println("Selected Option: ");
			    choice = inInt.nextInt();
			    
			    switch (choice) {
			    case 1:
			      System.out.println("Pie selected");
			      break;
			    case 2:
			      System.out.println("Histogram selected");
			      break;
			    case 3:
			      System.out.println("Bar selected");
			      break;
			    case 4:
			    	System.out.println("Scatter selected");
				    break;
			    case 5:
				    System.out.println("Exit selected");
				    break;
			    default:
			      System.out.println("Invalid selection");
			      break; // This break is not really necessary
			    }
			   
			    
			    
			    System.out.println("Select a query for the Film Database");
			    
			    query = inString.nextLine();
			    
			    System.out.println("You typed: " + query);
			    inInt.close();
			    inString.close();
			    
			    Statement stmt = null;
			    Connection c = null;
			    try {
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\film.db");
			    } catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			    System.out.println("Opened database successfully");
			  //  c.setAutoCommit(false);
			    try {
			    	stmt = c.createStatement();
				    ResultSet rs = stmt.executeQuery(query);
				    int i = 0;
				    while(rs.next()){
				    	String countryid = rs.getString("country_code");
				    	String countryName = rs.getString("country_name");
				    	String continent = rs.getString("continent");   	
				    	System.out.println(i+" Country Code: "+countryid+" Country Name: "+countryName+" Continent: "+continent+"\n");
				    	i++;
				    	}
			    	}
				catch (Exception e ) {
			    	System.out.println("Could not execute query!\n");
			    }
			    
			 }
			    
			    

			    			    
		    
}
