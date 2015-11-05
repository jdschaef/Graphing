import java.util.ArrayList;
import java.util.Scanner;
import java.awt.List;
import java.io.File;
import java.sql.*;
//JChart


import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;


public class GraphMain {
		 public static void main(String[] args) {
			 
		        int choice;
		        Scanner inInt = new Scanner(System.in);
			    Statement stmt = null;
			    Connection c = null;

			    try {
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\filmdb.db");
			    } catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			    System.out.println("\nOpened database successfully\n");
			    
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
			      Pie(c,stmt);
			      System.exit(0);
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
			   
			    	 
}// End Main

		 
public static void Pie(Connection c, Statement stmt) {
	
	Scanner inInt = new Scanner(System.in);
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Select a query for the Film Database");	    
    String query = inString.nextLine();
    
    

    
    System.out.println("============================");
    System.out.println("|    Creating Pie Graph    |");
    System.out.println("============================");
    System.out.println("\n\nEnter the first column name for X axis: ");	    
	String arg1 = scan1.nextLine();
	System.out.println("Enter the first column name for Y axis: ");	   
	String arg2 = scan2.nextLine();
    
	ArrayList<String> arg1List = new ArrayList<String>();
    ArrayList<Integer> arg2List = new ArrayList<Integer>();
    
    try {
    	stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int i = 1;

	    while(rs.next()){ 
	    	String newVal1 = rs.getString(arg1);
	        Integer newVal2 = rs.getInt(arg2);
	    	System.out.println(i+" "+ arg1 +": " +newVal1+" "+ arg2 +": "+newVal2+" \n");
	    	arg1List.add(newVal1);
	    	arg2List.add(newVal2);
	    	i++;
	    	}
    	}
	catch (Exception e) {
    	System.out.println("Could not execute query!\n");
    }
    
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    System.out.println("Outside of query"); 
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    System.out.println("test " + arg1List.get(j));
    System.out.println("test 2 " +arg2List.get(j));
    pieDataset.setValue(arg1List.get(j), arg2List.get(j));
    }
    
    JFreeChart chart = ChartFactory.createPieChart
        ("KICK FUCKING ASS ALI ;)",    // Title
         pieDataset,                   // Dataset
         true,                         // Show legend  
         true,                         // Use tooltips
         false                         // Configure chart to generate URLs?
         );
    try {
        ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\chart.png"), chart, 500, 300);
    } catch (Exception e) {
        System.out.println("Problem occurred creating chart.");
    }
	
	
} // End Pie


public static void Histogram(Connection c, Statement stmt) {
	Scanner inInt = new Scanner(System.in);
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Select a query for the Film Database");	    
    String query = inString.nextLine();
    
    

    
    System.out.println("============================");
    System.out.println("|    Creating Pie Graph    |");
    System.out.println("============================");
    System.out.println("\n\nEnter the first column name for X axis: ");	    
	String arg1 = scan1.nextLine();
	System.out.println("Enter the first column name for Y axis: ");	   
	String arg2 = scan2.nextLine();
    
	ArrayList<String> arg1List = new ArrayList<String>();
    ArrayList<Integer> arg2List = new ArrayList<Integer>();
    
    try {
    	stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int i = 1;

	    while(rs.next()){ 
	    	String newVal1 = rs.getString(arg1);
	        Integer newVal2 = rs.getInt(arg2);
	    	System.out.println(i+" "+ arg1 +": " +newVal1+" "+ arg2 +": "+newVal2+" \n");
	    	arg1List.add(newVal1);
	    	arg2List.add(newVal2);
	    	i++;
	    	}
    	}
	catch (Exception e) {
    	System.out.println("Could not execute query!\n");
    }
    
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    System.out.println("Outside of query"); 
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    System.out.println("test " + arg1List.get(j));
    System.out.println("test 2 " +arg2List.get(j));
    pieDataset.setValue(arg1List.get(j), arg2List.get(j));
    }
    
    JFreeChart chart = ChartFactory.createPieChart
        ("KICK FUCKING ASS ALI ;)",    // Title
         pieDataset,                   // Dataset
         true,                         // Show legend  
         true,                         // Use tooltips
         false                         // Configure chart to generate URLs?
         );
    try {
        ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\chart.png"), chart, 500, 300);
    } catch (Exception e) {
        System.out.println("Problem occurred creating chart.");
    }
    
    
    
    
    
    inInt.close();
    inString.close();
    scan1.close();
    scan2.close();
}





public static void Bar(Connection c, Statement stmt) {
	
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Select a query for the Film Database");	    
    String query = inString.nextLine();
    
    

    
    System.out.println("============================");
    System.out.println("|    Creating Bar Graph    |");
    System.out.println("============================");
    System.out.println("\n\nEnter the first column name for X axis: ");	    
	String arg1 = scan1.nextLine();
	System.out.println("Enter the first column name for Y axis: ");	   
	String arg2 = scan2.nextLine();
    
    
    try {
    	stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int i = 1;
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
    

    inString.close();
    scan1.close();
    scan2.close();
}
public static void Scatter(Connection c, Statement stmt) {
	Scanner inInt = new Scanner(System.in);
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Select a query for the Film Database");	    
    String query = inString.nextLine();
    
    

    
    System.out.println("============================");
    System.out.println("|  Creating Scatter Graph   ");
    System.out.println("============================");
    System.out.println("\n\nEnter the first column name for X axis: ");	    
	String arg1 = scan1.nextLine();
	System.out.println("Enter the first column name for Y axis: ");	   
	String arg2 = scan2.nextLine();
    
    
    try {
    	stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int i = 1;
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
	    
} //End Graph








