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
				    System.exit(0);
				    break;
			    default:
			      System.out.println("Invalid selection");
			      System.exit(0);
			      break; // This break is not really necessary
			    }
			   
			    
			    
			    System.out.println("Select a query for the Film Database");	    
			    query = inString.nextLine();
			    
			    System.out.println("You typed: " + query);
			    
			    
			    Statement stmt = null;
			    Connection c = null;
			    
			    Scanner scan1 = new Scanner(System.in);
			    Scanner scan2 = new Scanner(System.in);
			    System.out.println("\n\nEnter the first column name for X axis: ");	    
		    	String arg1 = scan1.nextLine();
		    	System.out.println("Enter the first column name for Y axis: ");	   
		    	String arg2 = scan2.nextLine();
		        System.out.println(arg1);
		        System.out.println(arg2);
			    try {
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\filmdb.db");
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
				    	String newVal1 = rs.getString(arg1);
				    	String newVal2 = rs.getString(arg2);
				    	System.out.println(i+" "+ arg1 +": " +newVal1+" "+ arg2 +": "+newVal2+" \n");
				    	i++;
				    	}
			    	}
				catch (Exception e ) {
			    	System.out.println("Could not execute query!\n");
			    }
			    
			    inInt.close();
			    inString.close();
			    scan1.close();
			    scan2.close();
			 }
	    
} //End Main
