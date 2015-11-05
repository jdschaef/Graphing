import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.sql.*;
//JChart
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities;



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
			    System.out.println("|        5. Line           |");
			    System.out.println("|        6. Exit           |");
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
			      Histogram(c,stmt);
			      System.exit(0);
			    case 3:
			      System.out.println("Bar selected");
			      Bar(c,stmt);
			      System.exit(0);
			    case 4:
			    	System.out.println("Scatter selected");
			    	Scatter(c,stmt);
				    System.exit(0);
			    case 5:
				    System.out.println("Line Select");
				    Line(c,stmt);
				    break;
			    case 6:
				    System.out.println("Exit selected");
				    System.exit(0);
				    break;
			    default:
			      System.out.println("Invalid selection");
			      System.exit(0);
			      break; // This break is not really necessary
			    }
			    inInt.close();
			    	 
}// End Main

		 
public static void Pie(Connection c, Statement stmt) {

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
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    pieDataset.setValue(arg1List.get(j), arg2List.get(j));
    }
    
    JFreeChart chart = ChartFactory.createPieChart
        ("Pie Graph",    // Title
         pieDataset,                   // Dataset
         true,                         // Show legend  
         true,                         // Use tooltips
         false                         // Configure chart to generate URLs?
         );
    try {
        ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\PieChart.png"), chart, 1500, 800);
        System.out.println("Your pie chart should be in C:\\sqlite\\");
    } catch (Exception e) {
        System.out.println("Problem occurred creating chart.");
    }
    
    inString.close();
    scan1.close();
    scan2.close();
	
} // End Pie


public static void Histogram(Connection c, Statement stmt) {

    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Select a query for the Film Database");	    
    String query = inString.nextLine();
    
    System.out.println("============================");
    System.out.println("| Creating Histogram Graph |");
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
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    pieDataset.setValue(arg1List.get(j), arg2List.get(j));
    }
    
    JFreeChart chart = ChartFactory.createPieChart
        ("Pie Graph",    // Title
         pieDataset,                   // Dataset
         true,                         // Show legend  
         true,                         // Use tooltips
         false                         // Configure chart to generate URLs?
         );
    try {
        ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\HistogramGraph.png"), chart, 1500, 800);
        System.out.println("Your pie chart should be in C:\\sqlite\\");
    } catch (Exception e) {
        System.out.println("Problem occurred creating chart.");
    }
}//End Histogram

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
    
    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    barDataset.addValue(arg2List.get(j), " ", arg1List.get(j));
    }
    
    JFreeChart chart = ChartFactory.createBarChart
        ("Bar Graph",    // Title
         "Category",         // chart title
         "Value",               // domain axis label
         barDataset,
         PlotOrientation.VERTICAL, // orientation
         true,                         // Show legend  
         true,                         // Use tooltips
         false                         // Configure chart to generate URLs?
         );
    try {
        ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\BarChart.png"), chart, 1500, 800);
        System.out.println("Your pie chart should be in C:\\sqlite\\");
    } catch (Exception e) {
        System.out.println("Problem occurred creating chart.");
    }

    inString.close();
    scan1.close();
    scan2.close();
}//End Bar

public static void Scatter(Connection c, Statement stmt) {
	
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
       
       
   	ArrayList<Integer> arg1List = new ArrayList<Integer>();
       ArrayList<Integer> arg2List = new ArrayList<Integer>();
       
       try {
       	stmt = c.createStatement();
   	    ResultSet rs = stmt.executeQuery(query);
   	    int i = 1;

   	    while(rs.next()){ 
   	    	Integer newVal1 = rs.getInt(arg1);
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
       XYSeriesCollection scatterDataset = new XYSeriesCollection();
       System.out.println(arg2List.size());
       int j =0;
       XYSeries series = new XYSeries("Scatter");
       //WE JUST NEED TWO VALUES
       for(j=0;j < arg2List.size();j++){
           series.add(arg1List.get(j), arg2List.get(j));
       }
       scatterDataset.addSeries(series);
       
       JFreeChart chart = ChartFactory.createScatterPlot
           ("Scatter Plot Graph",    // Title
            "Value1",         // chart title
            "Value2",               // domain axis label
            scatterDataset,
            PlotOrientation.VERTICAL, // orientation
            true,                         // Show legend  
            true,                         // Use tooltips
            false                         // Configure chart to generate URLs?
            );
       try {
           ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\ScatterPlot.png"), chart, 1500, 800);
           System.out.println("Your pie chart should be in C:\\sqlite\\");
       } catch (Exception e) {
           System.out.println("Problem occurred creating chart.");
       }
    

    inString.close();
    scan1.close();
    scan2.close();
}//End Scatter



public static void Line(Connection c, Statement stmt) {
	
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Select a query for the Film Database");	    
    String query = inString.nextLine();
    
    

    
    System.out.println("============================");
    System.out.println("|   Creating Line Graph     ");
    System.out.println("============================");
    System.out.println("\n\nEnter the first column name for X axis: ");	    
   	String arg1 = scan1.nextLine();
   	System.out.println("Enter the first column name for Y axis: ");	   
   	String arg2 = scan2.nextLine();
       
       
   	ArrayList<Integer> arg1List = new ArrayList<Integer>();
    ArrayList<Integer> arg2List = new ArrayList<Integer>();
       
       try {
       	stmt = c.createStatement();
   	    ResultSet rs = stmt.executeQuery(query);
   	    int i = 1;

   	    while(rs.next()){ 
   	    	Integer newVal1 = rs.getInt(arg1);
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
       XYSeriesCollection lineDataset = new XYSeriesCollection();
       System.out.println(arg2List.size());
       int j =0;
       XYSeries series = new XYSeries("Scatter");
       //WE JUST NEED TWO VALUES
       for(j=0;j < arg2List.size();j++){
           series.add(arg1List.get(j), arg2List.get(j));
       }
       lineDataset.addSeries(series);
       
       JFreeChart chart = ChartFactory.createXYLineChart
           ("Scatter Plot Graph",    // Title
            "Value1",                // chart title
            "Value2",               // domain axis label
            lineDataset,
            PlotOrientation.VERTICAL, // orientation
            true,                         // Show legend  
            true,                         // Use tooltips
            false                         // Configure chart to generate URLs?
            );
       try {
           ChartUtilities.saveChartAsPNG(new File("C:\\sqlite\\LineGraph.png"), chart, 1500, 800);
           System.out.println("Your pie chart should be in C:\\sqlite\\");
       } catch (Exception e) {
           System.out.println("Problem occurred creating chart.");
       }
    

    inString.close();
    scan1.close();
    scan2.close();
}//End Line

	    
} //End Graph








