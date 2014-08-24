package datavis;

import java.util.Scanner;
import javax.swing.JComboBox;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014
    
    This class manages a list of DataTCOON objects,
    that each represent data taken from the TCOON weather sample database.

    In addition to populating this data, it has graph objects for
    pie charts, line graphs, and bar graphs for each type of data.

    This class is abstract as DataTCOON is also abstract.
    It is intended that DataTCOON be inherited to specify a certain set
    of TCOON sample types, then DataList be inherited to manage that data.

	
		JFreeChart library Source: http://www.jfree.org/jfreechart/

*/

public abstract class DataList
{

      //variables

   //private LinkedList< DataTCOON > dataTCOONList;

   private String fileHandle;

   private Scanner readFile;


      //constructor

   public DataList()
   {
      fileHandle = null;
      readFile = null;
      //dataTCOONList = new LinkedList< DataTCOON >();
   }


   public DataList( String fileHandleIn ) throws java.io.UnsupportedEncodingException
   {
      setFileHandle( fileHandleIn );

      populate();
   }


      //setters and getters

   public void setFileHandle( String fileHandleIn )
   {
      fileHandle = fileHandleIn;
   }

   public String getFileHandle()
   {
      return fileHandle;
   }


        //number of elements
   
   public abstract int getNumberOfElements();
   


      //element adding functions
   
   public abstract void populate() throws java.io.UnsupportedEncodingException;
   
    
      //display functions

   @Override
   public abstract String toString();

   public abstract String getStationInfo();

   
      //graph and chart access functions
   
   public abstract JFreeChart getBarGraphChart( String select );
   
   public abstract JFreeChart getLineGraphChart( String select );

   public abstract JFreeChart getPieChartChart( String select );

   public abstract DefaultCategoryDataset getBarGraph( String select );

   public abstract DefaultCategoryDataset getLineGraph( String select );

   public abstract DefaultPieDataset getPieChart( String select );
   
        //user interaction components access
   public abstract JComboBox getSelectMenu();

   public abstract String[] getDropMenu();
   
}
