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

//A graphing program that allows the user to specify a database and print 1 of 4 graphs.
public class GraphMain {
	//The main method allows the user to choose which graph they want.
		 public static void main(String[] args) {
			    
		        int choice;
		        Scanner inInt = new Scanner(System.in);
		        //Get a statement from the database
			    Statement stmt = null;
			    //This is the connection to the database
			    Connection c = null;

			    try {
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\filmdb.db");
			    } catch ( Exception e ) {
			      //Print error if it doesn't connect
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			    System.out.println("\nOpened database successfully\n");
			    //Prints out a menu for the user to specify which graph to choose.
			    System.out.println("============================");
			    System.out.println("|   Graphing SQL Data      |");
			    System.out.println("============================");
			    System.out.println("| Options:                 |");
			    System.out.println("|        1. Pie            |");
			    System.out.println("|        2. Bar            |");
			    System.out.println("|        3. Scatter        |");
			    System.out.println("|        4. Line           |");
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
			      System.out.println("Bar selected");
			      Bar(c,stmt);
			      System.exit(0);
			    case 3:
			    	System.out.println("Scatter selected");
			    	Scatter(c,stmt);
				    System.exit(0);
			    case 4:
				    System.out.println("Line Select");
				    Line(c,stmt);
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
			    inInt.close();
			    	 
}// End Main

//This makes a pie chart with JFreeChart
public static void Pie(Connection c, Statement stmt) {

    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Enter a query for the Film Database:");	    
    String query = inString.nextLine();
  
    System.out.println("============================");
    System.out.println("|    Creating Pie Graph    |");
    System.out.println("============================");
       
	String arg1;
	String arg2;
    String change;
    
    //Pie graphs take in a string for the category and an integer for the value which 
    //will be added together to make the sum of the whole pie then made to a percent.
	ArrayList<String> arg1List = new ArrayList<String>();
    ArrayList<Integer> arg2List = new ArrayList<Integer>();
    
    try {
    	//Get the column type and make sure that the specific columns are applicable
    	//for a pie graph.
    	stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int colType = rs.getMetaData().getColumnType(2);
	    arg1 =  rs.getMetaData().getColumnName(1);
	    arg2 =  rs.getMetaData().getColumnName(2);
        /*This should change the X variable if it is a string or graph*/
	    if(determineType(colType)==true){
	    	change = arg1;
	    	arg1 = arg2;
	    	arg2 = change;   	
	    }
	    int i = 1;
	    //This will print out the information from the database in the console window
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
		//If it is a bad query, the system should catch it
    	System.out.println("Could not execute query!\n");
    }
    
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    pieDataset.setValue(arg1List.get(j), arg2List.get(j));
    }
    //JFreeChart creates a graph of the information for the user
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

//This makes a bar graph with JFreeChart
public static void Bar(Connection c, Statement stmt) {
	
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Enter a query for the Film Database:");	    
    String query = inString.nextLine();
       
    System.out.println("============================");
    System.out.println("|    Creating Bar Graph    |");
    System.out.println("============================");
    
	String arg1 = null;
	String arg2 = null;
	String change;
    //The bar graph is allowed to have a string or string for the x axis.    
	ArrayList<String> arg1List = new ArrayList<String>();
    ArrayList<Integer> arg2List = new ArrayList<Integer>();
    //Get the column type and make sure that the specific columns are applicable.
    //The x axis should always contain a string or a integer while the value should
    //be the y axis.
    try {
    	stmt = c.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int colType = rs.getMetaData().getColumnType(2);
	    arg1 =  rs.getMetaData().getColumnName(1);
	    arg2 =  rs.getMetaData().getColumnName(2);
        /*This should change the X variable if it is a string or graph*/
	    if(determineType(colType)==true){
	    	change = arg1;
	    	arg1 = arg2;
	    	arg2 = change;   	
	    }
	    int i = 1;
	    //This prints out the information in the console window for the user to view the
	    //data that will be added into the graph.
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
    //This will build the bar graph using JFreeChart
    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    System.out.println(arg2List.size());
    int j;
    for(j=0;j < arg2List.size();j++){
    barDataset.addValue(arg2List.get(j), " ", arg1List.get(j));
    }
    //This will set a title and the x and y axis labels for the database column labels
    JFreeChart chart = ChartFactory.createBarChart
        ("Bar Graph",    // Title
         arg1,         // chart title
         arg2,               // domain axis label
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
//This makes a scatter plot with JFreeChart
public static void Scatter(Connection c, Statement stmt) {
	
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Enter a query for the Film Database:");	    
    String query = inString.nextLine();
    
    System.out.println("============================");
    System.out.println("|  Creating Scatter Graph   ");
    System.out.println("============================");
     
   	String arg1 = null;
   	String arg2 = null;
       
       
   	  ArrayList<Integer> arg1List = new ArrayList<Integer>();
      ArrayList<Integer> arg2List = new ArrayList<Integer>();
       //Get the column type and make sure that the specific columns are applicable.
       //The scatter plot should take in two integers to be graphed. Otherwise an 
       //error is thrown.
       try {
       	stmt = c.createStatement();
   	    ResultSet rs = stmt.executeQuery(query);
   	    int colType = rs.getMetaData().getColumnType(2);
   	    arg1 =  rs.getMetaData().getColumnName(1);
   	    arg2 =  rs.getMetaData().getColumnName(2);
           /*This should change the X variable if it is a string or graph*/
   	    if(determineType(colType)==true){
   	    	System.out.println("A scatter plot should query a database and return two numbers!"); 
   	    }
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
       //This creates the scatter plot with JFreeChart and labels the x and y 
       //axis accordingly.
       JFreeChart chart = ChartFactory.createScatterPlot
           ("Scatter Plot Graph",    // Title
            arg1,         // x axis
            arg2,               // y axis
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


//This makes a line graph with JFreeChart
public static void Line(Connection c, Statement stmt) {
	
    Scanner inString = new Scanner(System.in);
    Scanner scan1 = new Scanner(System.in);
	Scanner scan2 = new Scanner(System.in);
	System.out.println("Enter a query for the Film Database:");	    
    String query = inString.nextLine();
        
    System.out.println("============================");
    System.out.println("|   Creating Line Graph     ");
    System.out.println("============================");
    
   	String arg1 = null;
   	String arg2 = null;
        
   	ArrayList<Integer> arg1List = new ArrayList<Integer>();
    ArrayList<Integer> arg2List = new ArrayList<Integer>();
    //Get the column type and make sure that the specific columns are applicable and
    //check to make sure that the x and y values are both integers.
    try {
       	stmt = c.createStatement();
   	    ResultSet rs = stmt.executeQuery(query);
   	    int colType = rs.getMetaData().getColumnType(2);
   	    arg1 =  rs.getMetaData().getColumnName(1);
   	    arg2 =  rs.getMetaData().getColumnName(2);
           /*This should change the X variable if it is a string or graph*/
   	    if(determineType(colType)==true){
   	    	System.out.println("A line graph should query a database and return two numbers!");  	
   	    }
   	    int i = 1;
   	    //This will print out all the values in the console
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
       //These are the values being added to the lists
       for(j=0;j < arg2List.size();j++){
           series.add(arg1List.get(j), arg2List.get(j));
       }
       lineDataset.addSeries(series);
       //This will create the scatter plot using JFreeChart
       JFreeChart chart = ChartFactory.createXYLineChart
           ("Scatter Plot Graph",    // Title
            arg1,                // x axis
            arg2,               // y axis
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

//This function determines if the column types are of a string or 
//an integer which allows us to switch them for the x and y axis
//if need be.
private static boolean determineType(int colType){
	
	if(colType == 12 || colType == 91){
		return true;
	}
	return false;

}//End determineType




} //End GraphMain








