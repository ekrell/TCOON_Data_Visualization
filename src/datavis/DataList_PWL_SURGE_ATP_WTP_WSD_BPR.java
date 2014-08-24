
package datavis;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014
    
    This class manages a list of Data_PWL_SURGE_ATP_WTP_WSD_BPR objects,
    that each represent data taken from the TCOON weather sample database.

    In addition to populating this data, it has graph objects for
    pie charts, line graphs, and bar graphs for each type of data.
	
	
	JFreeChart library Source: http://www.jfree.org/jfreechart/

*/
public class DataList_PWL_SURGE_ATP_WTP_WSD_BPR extends DataList
{

        //Class Fields
   
    //A linked list of DataPTLAVA objects
    private LinkedList< Data_PWL_SURGE_ATP_WTP_WSD_BPR > dataList;

   
    //The location where the station is located
    private String stationName;
    
    //The station ID
    private int stationID;
    
    //The station location abreviation
    private String stationAbr;

    //The interval at which the data was collected
    private String timeInterval;

    
    //An enumeration of each sample type that is processed by this class
    public enum sampleType
    {
        PrimaryWaterLevel, StormSurge, AirTemp, WaterTemp,
             WindSpeed, BarometricPressure
    }
    
    
    //Each sample type has a corrosponding pie chart
    private PieChart pieChart_PrimaryWaterLevel;
    private PieChart pieChart_StormSurge;
    private PieChart pieChart_AirTemp;
    private PieChart pieChart_WaterTemp;
    private PieChart pieChart_WindSpeed;
    private PieChart pieChart_BarometricPressure;

    //Each sample type has a corrosponding line graph
    private LineGraph lineGraph_PrimaryWaterLevel;
    private LineGraph lineGraph_StormSurge;
    private LineGraph lineGraph_AirTemp;
    private LineGraph lineGraph_WaterTemp;
    private LineGraph lineGraph_WindSpeed;
    private LineGraph lineGraph_BarometricPressure;

 
    //Each sample type has a corrosponding bar graph
    private BarGraph barGraph_PrimaryWaterLevel;
    private BarGraph barGraph_StormSurge;
    private BarGraph barGraph_AirTemp;
    private BarGraph barGraph_WaterTemp;
    private BarGraph barGraph_WindSpeed;
    private BarGraph barGraph_BarometricPressure;  



        //Constructors
    
    /*
    Function: 
        DataListPTLAVA
    
    Constructor: 
        0 arguments
    
    Purpose:
        Initialize a new list that contains no data or specified data source.
    */
    
    public DataList_PWL_SURGE_ATP_WTP_WSD_BPR()
    {
         super();
         dataList = new LinkedList< Data_PWL_SURGE_ATP_WTP_WSD_BPR >();

         pieChart_PrimaryWaterLevel = new PieChart( "Primary Water Level - Monthly Totals");
         pieChart_StormSurge = new PieChart( "Storm Surge - Monthly Totals");
         pieChart_AirTemp = new PieChart( "Air Temperature - Monthly Totals");
         pieChart_WaterTemp = new PieChart("Water Temperature - Monthly Totals");
         pieChart_WindSpeed = new PieChart("Wind Speed - Monthly Totals");
         pieChart_BarometricPressure = new PieChart("Barometric Pressure - Monthly Totals");


         lineGraph_PrimaryWaterLevel = new LineGraph( "Primary Water Level", "Monthly Average", "Meters" );
         lineGraph_StormSurge = new LineGraph( "Storm Surge", "Monthly Average", "Meters" );
         lineGraph_AirTemp = new LineGraph( "Air Temperature", "Monthly Average", "Celsius" );
         lineGraph_WaterTemp = new LineGraph( "Water Temperature", "Monthly Average", "Celsius" );
         lineGraph_WindSpeed = new LineGraph( "Wind Speed", "Monthly Average", "Celsius" );
         lineGraph_BarometricPressure = new LineGraph( "Barometric Pressure", "Monthly Average", "Millibars" );

         
         
         barGraph_PrimaryWaterLevel = new BarGraph( "Primary Water Level", "Monthly Average", "Meters" );
         barGraph_StormSurge = new BarGraph( "Storm Surge", "Monthly Average", "Meters" );
         barGraph_AirTemp = new BarGraph( "Air Temperature", "Monthly Average", "Celsius" );
         barGraph_WaterTemp = new BarGraph( "Water Temperature", "Monthly Average", "Celsius" );
         barGraph_WindSpeed = new BarGraph( "Wind Speed", "Monthly Average", "Celsius" );
         barGraph_BarometricPressure = new BarGraph( "Barometric Pressure", "Monthly Average", "Millibars" );

   }

    /*
    Function: 
        DataListPTLAVA
    
    Constructor: 
        3 arguments
    
    Purpose:
        Initialize a new list that contains no data, but has the source manually entered
    */
    
    public DataList_PWL_SURGE_ATP_WTP_WSD_BPR( String stationNameIn, int stationIDIn, String timeIntervalIn )
    {
         super();
         
         setStationName( stationNameIn );
         setStationID( stationIDIn );
         setTimeInterval( timeIntervalIn );
         
         
         dataList = new LinkedList< Data_PWL_SURGE_ATP_WTP_WSD_BPR >();

         initializeEmptyGraphs();

   }
    
    /*
    Function: 
        DataListPTLAVA( String fileHandleIn )
    
    Constructor: 
        4 argument
    
    Purpose:
        Parse a file to populate the list of objects with sample data.
        Generate the various charts. The station information is specified.
    */
    public DataList_PWL_SURGE_ATP_WTP_WSD_BPR( String fileHandleIn, String stationNameIn, int stationIDIn, String timeIntervalIn ) throws java.io.UnsupportedEncodingException
    {
         super( fileHandleIn );
         
         setStationName( stationNameIn );
         setStationID( stationIDIn );
         setTimeInterval( timeIntervalIn );

         initializeEmptyGraphs();

         generatePieCharts();
         generateLineGraphs();
         generateBarGraphs();
    }

    /*
    Function: 
        DataListPTLAVA( String fileHandleIn )
    
    Constructor: 
        1 argument
    
    Purpose:
        Parse a file to populate the list of objects with sample data.
        Generate the various charts. The station information will be 
        gathered from the file.
    */
    public DataList_PWL_SURGE_ATP_WTP_WSD_BPR( String fileHandleIn ) throws java.io.UnsupportedEncodingException
    {
         super( fileHandleIn );
         
         
   parseStationInfo();

         initializeEmptyGraphs();

         generatePieCharts();
         generateLineGraphs();
         generateBarGraphs();
    }

         //Accessors and Mutators

    /*
    Function: 
        getNumberOfElements
    
    Purpose:
        Access the number of elements in dataPTLAVAList.
    
    */
    
    @Override
    public int getNumberOfElements()
    {
       return dataList.size();
    }

    
    
    /*
    Function: 
        setStationName( String stationNameIn )
    
    Purpose:
        Set stationName.
    
    */
    private void setStationName( String stationNameIn )
    {
       stationName = stationNameIn;
    }

    
    
    /*
    Function: 
        getStationName()
    
    Purpose:
        get stationName.
    
    */
    public String getStationName()
    {
       return stationName;
    }

    
    
    /*
    Function: 
        setStationID( String stationIDIn )
    
    Purpose:
        Set stationID.
    
    */
    private void setStationID( int stationIDIn )
    {
       stationID = stationIDIn;
    }

    
    /*
    Function: 
        getStationID()
    
    Purpose:
        Get stationID.
    
    */
    public int getStationID()
    {
       return stationID;
    }


    
    /*
    Function: 
        setStationAbr( String stationAbrIn )
    
    Purpose:
        Set stationAbr.
    
    */
    private void setStationAbr( String stationAbrIn )
    {
       stationAbr = stationAbrIn;
    }

    
    
    /*
    Function: 
        getStationAbr()
    
    Purpose:
        Get stationAbr.
    
    */
    public String getStationAbr()
    {
       return stationAbr;
    }


    
    /*
    Function: 
        setTimeInterval( String timeIntervalIn )
    
    Purpose:
        Set timeInterval
    
    */
    private void setTimeInterval( String timeIntervalIn )
    {
       timeInterval = timeIntervalIn;
    }

    
    
    /*
    Function: 
        getTimeInterval()
    
    Purpose:
        Get timeInterval.
    
    */
    public String getTimeInterval()
    {
       return timeInterval;
    }

    
    /*
    Function: 
        parseStationInfo()
    
    Purpose:
        Read the file to find the station name
        and the station ID. 
    
        Set those values once found.
    
    */
    
    private void parseStationInfo()
    {
        
        
        Scanner readFile = null;

        //attempt to open the file
        try
        {
             readFile = new Scanner( new File( super.getFileHandle() ) );
        }
        catch( FileNotFoundException fileNotFoundException )
        {
             System.err.printf( "Error opening file." );
             System.exit( 1 );
        }




        try
        {

             //counters used to track location the name
             int counter = 0;   //if this is zero, then you have not
                                //yet found the name. Avoids duplication.
             
             int counter2 = 0;  //use to iterate from value whose distance is
                                //known from the name, to the actual name
             
             //booleans to keep track of what stage you are at in 
             //location the information
             boolean stationNameObtained = false; //The first word of the name is found
             boolean stationNameFinished = false; //The final word of the name is found
             boolean timeIntervalObtained = false;
             boolean beginCounting = false;    
             

             String tempStationName = "";
             
             while ( readFile.hasNext( ) ) 
             {

                String lineIn = readFile.next();

                String linesIn[] = lineIn.split(",");

                //if the name is not yet found
                if( counter == 0 )
                {
                  
                    //Get the string that contains the integer value of 
                    //the station ID number
                    String tempStationID = linesIn[1];
                    //It is the first three characters of that string
                    tempStationID = tempStationID.substring( 0, 3 );
                    //Set the ID value as an integer
                    setStationID( Integer.parseInt( tempStationID ) );

                }

                //Only parse lines that are not data node lines
                if( ! ( lineIn.startsWith("\"") ) )
                {
                    //we know how many words the start of the name is from
                    //"generated", so we look there when found
                    if( lineIn.equals( "generated" ) && !stationNameObtained )
                    {
                        //begin counting from generated
                        beginCounting = true;
 
                    }
                }
                
                //If we have found "generated" and are still counting, then incrememnt counter
                if( beginCounting )
                {
                    counter2++;

                }
                
                //If we are 10 places away, then we have reached the name's first word
                if( counter2 == 10 && !stationNameObtained )
                {
                    
                    stationNameObtained = true;
                    beginCounting = false;  //cease counting


                }
                
                //If we have found the first part of the name, but not the last..               
                if( stationNameObtained && !stationNameFinished )
                {
                    //and we have not hit the word that contains " ( "
                    if( !lineIn.contains( "(") )
                    {
                        //we add that string as part of the station name 
                        tempStationName += lineIn;
                        tempStationName += " ";
                    }
                    else    //otherwise, the name has been found
                    {
                        stationNameFinished = true;
                    }
                }

                counter++;
            }
            
             //set the name
             setStationName( tempStationName );
             
//    System.out.println( getStationName() );

        }
        catch( NoSuchElementException elementException )
        {
             System.err.printf( "File may be corrupted." );
             readFile.close();
             System.exit( 1 );
        }
        catch( IllegalStateException stateException )
        {
             System.err.printf( "File read mishap occured." );
             System.exit( 1 );
        }

    }

        
        //Enumeration conversion
    
    /*
    Function: 
        stringToSampleType( String sIn )
    
    Purpose:
        Convert a string to the appropiate sampleType enum case.
        If the string does not match any case, default to PrimaryWaterLevel.
    
    */
    public sampleType stringToSampleType( String sIn )
    {
        switch ( sIn )
        {
            case "primaryWaterLevel":
            {
                return sampleType.PrimaryWaterLevel;

            }
            case "stormSurge":
            {
                return sampleType.StormSurge;

            }
            case "airTemperature":
            {
                return sampleType.AirTemp;

            }
            case "waterTemperature":
            {
                return sampleType.WaterTemp;

            }
            case "windSpeed":
            {
                return sampleType.WindSpeed;

            }
            case "barometricPressure":
            {
                return sampleType.BarometricPressure;

            }
            default:
            {
                return sampleType.PrimaryWaterLevel;
            }
        }
    }

    
    
    /*
    Function: 
        sampleTypeToString( sampleType sIn )
    
    Purpose:
        Convert a sampleType enum to an appropriate string representation.
        If the sampleType does not match any case, default to "Primary Water Level".
    
    */
    public String sampleTypeToString( sampleType sIn )
    {
        switch ( sIn )
        {
            case PrimaryWaterLevel:
            {
                return "Primary Water Level";

            }
            case StormSurge:
            {
                return "Storm Surge";

            }
            case AirTemp:
            {
                return "Air Temperature";

            }
            case WaterTemp:
            {
                return "Water Temperature";

            }
            case WindSpeed:
            {
                return "Wind Speed";

            }
            case BarometricPressure:
            {
                return "Barometric Pressure";

            }
            default:
            {
                return "Primary Water Level";
            }
        }
    }
   
   
    /*
    Function: 
        populate()
    
    Purpose:
        Use the file to capture all the data information.
    
    */

    @Override
    public void populate() throws java.io.UnsupportedEncodingException
    {
        
       dataList = new LinkedList< Data_PWL_SURGE_ATP_WTP_WSD_BPR >();

       Scanner readFile = null;
       
       //attempt to open the file
       try
       {
          readFile = new Scanner( new File( super.getFileHandle() ) );
       }
       catch( FileNotFoundException fileNotFoundException )
       {
          System.err.printf( "Error opening file." );
          System.exit( 1 );
       }

       
       //If the file doesn't match this pattern, then file is irrelevant and must be thrown out (and throw an exception)
       Pattern regex = Pattern.compile("^#date\\+time,[0-9]{3}-pwl,[0-9]{3}-surge,[0-9]{3}-atp,[0-9]{3}-wtp,[0-9]{3}-wsd,[0-9]{3}-bpr");
       if (readFile.findInLine(regex) == null)
         throw new java.io.UnsupportedEncodingException("File is not valid PSAWWB TCOON data"); 

       Date dateTemp;
       double primaryWaterLevelTemp;
       double stormSurgeTemp;
       double airTempTemp;
       double waterTempTemp;
       double windSpeedTemp;
       double barometricPressureTemp;


       //attempt to parse the file
       try
       {
          while ( readFile.hasNext( ) ) 
          {

             String lineIn = readFile.next();

             String linesIn[] = lineIn.split(",");


             if( lineIn.startsWith("\"") )  //if the line starts with the double quote,
                                            //then it is valid data and not a comment
             {

                 //replace all the quotes with nothingness
                 String dateLine = linesIn[0].replaceAll("\"", "");

                 //Date variables for storing the data as an actual Date
                 dateTemp = new Date();

                 DateFormat formatter;

                 formatter = new SimpleDateFormat("MM-dd-yyyy");

                 try
                 {
                     dateTemp = formatter.parse(dateLine);
                 }catch( ParseException p )
                 {
                    formatter = new SimpleDateFormat("yyyyDDD+HHmm");
                    try
                    {
                      dateTemp = formatter.parse(dateLine);
                    } catch (ParseException ex)
                    {
                      // give up
                      System.err.printf("Default SimpleDateFormat failed.\n");
                    }
                 }

                 //if the field has an "N/A", just make it 0.. probably not the best idea actually
                 if( linesIn[1].contains("NA"))
                 {
                     primaryWaterLevelTemp = 0.0;
                 }
                 else
                 {
                     primaryWaterLevelTemp = Double.parseDouble(linesIn[1]);
                 }

                 if( linesIn[2].contains("NA"))
                 {
                     stormSurgeTemp = 0.0;
                 }
                 else
                 {
                     stormSurgeTemp = Double.parseDouble(linesIn[2]);
                 }


                 if( linesIn[3].contains("NA"))
                 {
                     airTempTemp = 0.0;
                 }
                 else
                 {
                     airTempTemp = Double.parseDouble(linesIn[3]);    
                 }

                 if( linesIn[4].contains("NA"))
                 {
                     waterTempTemp = 0.0;
                 }
                 else
                 {
                     waterTempTemp = Double.parseDouble(linesIn[4]);
                 }


                 if( linesIn[5].contains("NA"))
                 {
                     windSpeedTemp = 0.0;
                 }
                 else
                 {
                     windSpeedTemp = Double.parseDouble(linesIn[5]);
                 }


                 if( linesIn[6].contains("NA"))
                 {
                     barometricPressureTemp = 0.0;
                 }
                 else
                 {
                     barometricPressureTemp = Double.parseDouble(linesIn[6]);
                 }

                 //now add all that collected data as a node
                 addData(dateTemp, primaryWaterLevelTemp, stormSurgeTemp, airTempTemp, waterTempTemp, windSpeedTemp, barometricPressureTemp );

             }

          }
       }
       catch( NoSuchElementException elementException )
       {
          System.err.printf( "File may be corrupted." );
          readFile.close();
          System.exit( 1 );
       }
       catch( IllegalStateException stateException )
       {
          System.err.printf( "File read mishap occured." );
          System.exit( 1 );
       }

    }


    
    /*
    Function: 
        addData()
    
    Purpose:
        Add the node to the list
    
    */
   public void addData( Date dateIn, double primaryWaterLevelIn, double stormSurgeIn, double airTempIn,
         double waterTempIn, double windSpeedIn, double barometricPressureIn )
   {
      dataList.add( new Data_PWL_SURGE_ATP_WTP_WSD_BPR( nextElementName(), getStationName(), getStationID(), getStationAbr(),
               dateIn, primaryWaterLevelIn, stormSurgeIn, airTempIn, waterTempIn, 
               windSpeedIn, barometricPressureIn ) );

   }

    /*
    Function: 
        nextElementName()
    
    Purpose:
        Gets a string of the node Number of what would be the next added node.
        Used for node numbering for the toString().
    
    */
   private String nextElementName()
   {
      String elementName = "Node " + Integer.toString( getNumberOfElements() + 1 );

      return elementName;
   }




      //display functions

   
    /*
    Function: 
        toString)
    
    Purpose:
        Return a formatted list of all data nodes
   
   Example of what proper output should be:
   
   Node 3
        Water level:    3.98
        Storm surge:    -0.17
        Air temperature:    20.60
        Water temperature:  20.80
        Wind speed:    7.00
        Air pressure:    1015.20
        Tue May 07 00:00:00 CDT 2013

        Node 4
        Water level:    4.07
        Storm surge:    -0.09
        Air temperature:    21.90
        Water temperature:  21.50
        Wind speed:    5.50
        Air pressure:    1015.80
        Wed May 08 00:00:00 CDT 2013

        Node 5
        Water level:    4.11
        Storm surge:    -0.10
        Air temperature:    21.70
        Water temperature:  22.20
        Wind speed:    6.10
        Air pressure:    1016.60
        Thu May 09 00:00:00 CDT 2013
   
    
    
    */
   @Override
   public String toString()
   {

      String fullDataSet = "";


      for( ListIterator< Data_PWL_SURGE_ATP_WTP_WSD_BPR > iter = dataList.listIterator(); iter.hasNext(); )
      {
         Data_PWL_SURGE_ATP_WTP_WSD_BPR dataElem = iter.next();

         //display the node info
         fullDataSet += dataElem.toString();
         
         //enter two linebreaks for readability
         fullDataSet += "\n\n";

      }

      return fullDataSet;   //return that entire string

   }
   
   
   
    /*
    Function: 
        getStationInfo()
    
    Purpose:
        Return a string that contains formatted information related to the 
        TCOON station being used
   
   Example:
   
        Source Station: Texas Point  (126)

        Data obtained from TCOON
        http://www.cbi.tamucc.edu/TCOON/

        Start:  Sun May 05 00:00:00 CDT 2013
        End:  Mon May 05 00:00:00 CDT 2014
    
    */
   @Override
   public String getStationInfo()
   {
       Data_PWL_SURGE_ATP_WTP_WSD_BPR dataElem = dataList.getFirst();
       
       //get date of first 
       String startDate = "Start:  " + dataElem.getDate().toString();
       
       dataElem = dataList.getLast();
       
       //get date of last node
       String endDate = "End:  " + dataElem.getDate().toString();
       
       //combine the two
       String timeInterval = startDate + "  -  " + endDate;
       
       //Get the station info and TCOON source URL
       String sourceTitle = String.format( "Source Station: %s (%d)", getStationName(), getStationID() );
       String sourceProvider = String.format( "Data obtained from TCOON" );
       String sourceURL = String.format( "http://www.cbi.tamucc.edu/TCOON/");
       
       //combine all the info and return
       return String.format( "%s\n\n%s\n%s\n\n%s\n%s", sourceTitle, sourceProvider, 
               sourceURL, startDate, endDate );
       
   }
   
    /*
    Function: 
        generatePieCharts()
    
    Purpose:
        Add pie charts for each sampleType
    
    */
   public void generatePieCharts()
   {
        pieChartAdd( pieChart_PrimaryWaterLevel, sampleType.PrimaryWaterLevel );
        pieChartAdd( pieChart_StormSurge, sampleType.StormSurge );
        pieChartAdd( pieChart_AirTemp, sampleType.AirTemp );
        pieChartAdd( pieChart_WaterTemp, sampleType.WaterTemp );
        pieChartAdd( pieChart_WindSpeed, sampleType.WindSpeed );
        pieChartAdd( pieChart_BarometricPressure, sampleType.BarometricPressure );
       
   }

    /*
    Function: 
        generateLineGraphs()
    
    Purpose:
        Add line graphs for each sampleType
    
    */
   public void generateLineGraphs()
   {
        lineGraphAdd( lineGraph_PrimaryWaterLevel, sampleType.PrimaryWaterLevel );
        lineGraphAdd( lineGraph_StormSurge, sampleType.StormSurge );
        lineGraphAdd( lineGraph_AirTemp, sampleType.AirTemp );
        lineGraphAdd( lineGraph_WaterTemp, sampleType.WaterTemp );
        lineGraphAdd( lineGraph_WindSpeed, sampleType.WindSpeed );
        lineGraphAdd( lineGraph_BarometricPressure, sampleType.BarometricPressure );
   }
   
    /*
    Function: 
        generateBarGraphs()
    
    Purpose:
        Add bar graphs for each sampleType
    
    */
   public void generateBarGraphs()
   {
        barGraphAdd( barGraph_PrimaryWaterLevel, sampleType.PrimaryWaterLevel );
        barGraphAdd( barGraph_StormSurge, sampleType.StormSurge );
        barGraphAdd( barGraph_AirTemp, sampleType.AirTemp );
        barGraphAdd( barGraph_WaterTemp, sampleType.WaterTemp );
        barGraphAdd( barGraph_WindSpeed, sampleType.WindSpeed );
        barGraphAdd( barGraph_BarometricPressure, sampleType.BarometricPressure );
   }
   
   
   
   
   
    /*
    Function: 
        pieChartAdd()
    
    Purpose:
        Populate the pie chart with data from the list of data nodes.
   
        The Pie Chart creates a graph based on the total value of each
        month for the specified sampleType.
   
        To do so, the initial date of the first node is captured,
        then subsequent nodes are totalled while the date is equal.
        When the date changes, the total is added to a list, and a new
        current month is set.
    
    */   
   private void pieChartAdd( PieChart chartIn, sampleType select )
   {
       
      double total = 0.0;
      
      
      Calendar cal = Calendar.getInstance();
      
      Data_PWL_SURGE_ATP_WTP_WSD_BPR first = dataList.getFirst();
      
      //Get the first node's date
      Date startDate = first.getDate();
      Date currentDate = startDate;
      cal.setTime(currentDate);
      
     //Init list of the names of each pie slice
      LinkedList< String > sliceNames = new LinkedList< String >();
      //Init list of the values of each pie slice
      LinkedList< Double > sliceData = new LinkedList< Double >();
      
      //Iterate through the data nodes
      for( ListIterator< Data_PWL_SURGE_ATP_WTP_WSD_BPR > iter = dataList.listIterator(); iter.hasNext(); )
      {
         
         Data_PWL_SURGE_ATP_WTP_WSD_BPR dataElem = iter.next();

// System.out.printf("\n\n%s", dataElem.displayData() );
         
         //get the current node's date
         Date tempDate = dataElem.getDate();
         Calendar tempCal = Calendar.getInstance();
         tempCal.setTime(tempDate);
         

         //If the months are not equal...
         if( ! (tempCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) )
         {
             //Append both pie slice values to their respective lists
             sliceNames.add( String.format("%s - %s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1 ));
             sliceData.add( total );
             
             currentDate = tempDate;
             cal.setTime(currentDate);
             
             //and clear the total
             total = 0.0;
           
             
         }
         
            //Add only data value that corrosponds to the selected sampleType
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     total += dataElem.getPrimaryWaterLevel();
                     break;
                 }
                 case StormSurge:
                 {
                     total += dataElem.getStormSurge();
                     break;
                 }
                 case AirTemp:
                 {
                     total += dataElem.getAirTemp();
                     break;
                 }
                 case WaterTemp:
                 {
                     total += dataElem.getWaterTemp();
                     break;
                 }
                 case WindSpeed:
                 {
                     total += dataElem.getWindSpeed();
                 }
                 case BarometricPressure:
                 {
                     total += dataElem.getBarometricPressure();
                     break;
                 }
                 
             }

      }

      Iterator iterA = sliceNames.iterator ();
      Iterator iterB = sliceData.iterator ();
      
      //Iterate through the lists of names and values..
      for (; iterA.hasNext() && iterB.hasNext (); )
      {
          //adding each to a pie chart
         chartIn.addData( iterA.next().toString(), Double.parseDouble( iterB.next() .toString() ) );
        
      }

   }
   
    /*
    Function: 
        lineGraphAdd()
    
    Purpose:
        Populate the line graph with data from the list of data nodes.
   
        The line graph creates a graph based on the total value of each
        month for the specified sampleType.
   
        To do so, the initial date of the first node is captured,
        then subsequent nodes are totalled while the date is equal.
        The nodes are also counted so that an average of the total is determined.
        When the date changes, the average is added to a list, and a new
        current month is set.
    
    */   
   private void lineGraphAdd( LineGraph lineIn, sampleType select )
   {
       
      
      double total = 0.0;
      double average = 0.0;
      int counter = 0;
      
      
      
      Calendar cal = Calendar.getInstance();
      
      Data_PWL_SURGE_ATP_WTP_WSD_BPR first = dataList.getFirst();
      
      Date startDate = first.getDate();
      Date currentDate = startDate;
      cal.setTime(currentDate);
      
     
      LinkedList< String > sliceNames = new LinkedList< String >();
      LinkedList< Double > sliceData = new LinkedList< Double >();
      
      
      for( ListIterator< Data_PWL_SURGE_ATP_WTP_WSD_BPR > iter = dataList.listIterator(); iter.hasNext(); )
      {
         
         Data_PWL_SURGE_ATP_WTP_WSD_BPR dataElem = iter.next();

 //System.out.printf("\n\n%s", dataElem.displayData() );
         
         Date tempDate = dataElem.getDate();
         Calendar tempCal = Calendar.getInstance();
         tempCal.setTime(tempDate);
         

         
         if( ! (tempCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) )
         {
             average = total / (double) counter;
             
             sliceNames.add( String.format("%s", new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)] ));
             sliceData.add( average );
             
             currentDate = tempDate;
             cal.setTime(currentDate);
             
             total = 0.0;
             counter = 0;
             
         }
         
             counter++;
             
             
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     total += dataElem.getPrimaryWaterLevel();
                     break;
                 }
                 case StormSurge:
                 {
                     total += dataElem.getStormSurge();
                     break;
                 }
                 case AirTemp:
                 {
                     total += dataElem.getAirTemp();
                     break;
                 }
                 case WaterTemp:
                 {
                     total += dataElem.getWaterTemp();
                     break;
                 }
                 case WindSpeed:
                 {
                     total += dataElem.getWindSpeed();
                 }
                 case BarometricPressure:
                 {
                     total += dataElem.getBarometricPressure();
                     break;
                 }
                 
             }

      }

      Iterator iterA = sliceNames.iterator ();
      Iterator iterB = sliceData.iterator ();
      
      for (; iterA.hasNext() && iterB.hasNext (); )
      {
        lineIn.addData(  iterA.next().toString(),  Double.parseDouble( iterB.next() .toString() ),  sampleTypeToString( select )  );
        
      }
   }
   
   
    /*
    Function: 
        barGraphAdd()
    
    Purpose:
        Populate the bar graph with data from the list of data nodes.
   
        The bar graph creates a graph based on the total value of each
        month for the specified sampleType.
   
        To do so, the initial date of the first node is captured,
        then subsequent nodes are totalled while the date is equal.
        The nodes are also counted so that an average of the total is determined.
        When the date changes, the average is added to a list, and a new
        current month is set.
    
    */      
   private void barGraphAdd( BarGraph barIn, sampleType select )
   {
       
      
      double total = 0.0;
      double average = 0.0;
      int counter = 0;
      
      
      
      Calendar cal = Calendar.getInstance();
      
      Data_PWL_SURGE_ATP_WTP_WSD_BPR first = dataList.getFirst();
      
      Date startDate = first.getDate();
      Date currentDate = startDate;
      cal.setTime(currentDate);
      
     
      LinkedList< String > sliceNames = new LinkedList< String >();
      LinkedList< Double > sliceData = new LinkedList< Double >();
      
      
      for( ListIterator< Data_PWL_SURGE_ATP_WTP_WSD_BPR > iter = dataList.listIterator(); iter.hasNext(); )
      {
         
         Data_PWL_SURGE_ATP_WTP_WSD_BPR dataElem = iter.next();

// System.out.printf("\n\n%s", dataElem.displayData() );
         
         Date tempDate = dataElem.getDate();
         Calendar tempCal = Calendar.getInstance();
         tempCal.setTime(tempDate);
         

         
         if( ! (tempCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) )
         {
             average = total / (double) counter;
             
             sliceNames.add( String.format("%s", new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)] ));
             sliceData.add( average );
             
             currentDate = tempDate;
             cal.setTime(currentDate);
             
             total = 0.0;
             counter = 0;
             
         }
         
             counter++;
             
             
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     total += dataElem.getPrimaryWaterLevel();
                     break;
                 }
                 case StormSurge:
                 {
                     total += dataElem.getStormSurge();
                     break;
                 }
                 case AirTemp:
                 {
                     total += dataElem.getAirTemp();
                     break;
                 }
                 case WaterTemp:
                 {
                     total += dataElem.getWaterTemp();
                     break;
                 }
                 case WindSpeed:
                 {
                     total += dataElem.getWindSpeed();
                 }
                 case BarometricPressure:
                 {
                     total += dataElem.getBarometricPressure();
                     break;
                 }
                 
             }

      }

      Iterator iterA = sliceNames.iterator ();
      Iterator iterB = sliceData.iterator ();
      
      for (; iterA.hasNext() && iterB.hasNext (); )
      {
        barIn.addData(  iterA.next().toString(),  Double.parseDouble( iterB.next() .toString() ),  sampleTypeToString( select )  );
        
      }
   }
   
   
   
   
    /*
    Function: 
        getPieChart()
    
    Purpose:
        Obtain the DefaultPieDataset object that
        was created in the pieChart class.
   
        This allows the calling functions to access the graph,
        but they cannot modify the actual graph that still resides 
        in the class as it is private. 
    
    */   
   
   @Override
   public DefaultPieDataset getPieChart( String selectIn )
   {
        sampleType select = stringToSampleType( selectIn );

       //return a pie chart depending on the sampleType
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     return pieChart_PrimaryWaterLevel.getPieChart();
                     
                 }
                 case StormSurge:
                 {
                     return pieChart_StormSurge.getPieChart();
                     
                 }
                 case AirTemp:
                 {
                     return pieChart_AirTemp.getPieChart();
                     
                 }
                 case WaterTemp:
                 {
                     return pieChart_WaterTemp.getPieChart();
                     
                 }
                 case WindSpeed:
                 {
                     return pieChart_WindSpeed.getPieChart();
                     
                 }
                 case BarometricPressure:
                 {
                     return pieChart_BarometricPressure.getPieChart();
                     
                 }
                 default:
                 {
                     return pieChart_PrimaryWaterLevel.getPieChart();
                 }
                 
             }
   }    
    
   
    /*
    Function: 
        getLineGraph()
    
    Purpose:
        Obtain the DefaultCategoryDataset object that
        was created in the lineGraph class.
   
        This allows the calling functions to access the graph,
        but they cannot modify the actual graph that still resides 
        in the class as it is private. 
    
    */   
   
   @Override
   public DefaultCategoryDataset getLineGraph( String selectIn )
   {
       
       sampleType select = stringToSampleType( selectIn );

             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     return lineGraph_PrimaryWaterLevel.getLineGraph();
                     
                 }
                 case StormSurge:
                 {
                     return lineGraph_StormSurge.getLineGraph();
                     
                 }
                 case AirTemp:
                 {
                     return lineGraph_AirTemp.getLineGraph();
                     
                 }
                 case WaterTemp:
                 {
                     return lineGraph_WaterTemp.getLineGraph();
                     
                 }
                 case WindSpeed:
                 {
                     return lineGraph_WindSpeed.getLineGraph();
                     
                 }
                 case BarometricPressure:
                 {
                     return lineGraph_BarometricPressure.getLineGraph();
                     
                 }
                 default:
                 {
                     return lineGraph_PrimaryWaterLevel.getLineGraph();
                 }
                 
             }
       
   }
   
   
    /*
    Function: 
        getBarGraph()
    
    Purpose:
        Obtain the DefaultCategoryDataset object that
        was created in the barGraph class.
   
        This allows the calling functions to access the graph,
        but they cannot modify the actual graph that still resides 
        in the class as it is private. 
    
    */   
   
   @Override
   public DefaultCategoryDataset getBarGraph( String selectIn )
   {
       
        sampleType select = stringToSampleType( selectIn );

       
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     return barGraph_PrimaryWaterLevel.getBarGraph();
                     
                 }
                 case StormSurge:
                 {
                     return barGraph_StormSurge.getBarGraph();
                     
                 }
                 case AirTemp:
                 {
                     return barGraph_AirTemp.getBarGraph();
                     
                 }
                 case WaterTemp:
                 {
                     return barGraph_WaterTemp.getBarGraph();
                     
                 }
                 case WindSpeed:
                 {
                     return barGraph_WindSpeed.getBarGraph();
                     
                 }
                 case BarometricPressure:
                 {
                     return barGraph_BarometricPressure.getBarGraph();
                     
                 }
                 default:
                 {
                     return barGraph_PrimaryWaterLevel.getBarGraph();
                 }
                 
             }
       
   }

   
    /*
    Function: 
        getPieChartChart()
    
    Purpose:
        Obtain the JFreeChart object that
        was created in the pieChart class.
   
        Where getPieChart() returns the graph object itself,
        this class returns the pre-formatted chart object for ready display
    
    */   
   
   @Override
   public JFreeChart getPieChartChart( String selectIn )
   {
       
        sampleType select = stringToSampleType( selectIn );

       
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     return pieChart_PrimaryWaterLevel.getChartPanel();
                     
                 }
                 case StormSurge:
                 {
                     return pieChart_StormSurge.getChartPanel();
                     
                 }
                 case AirTemp:
                 {
                     return pieChart_AirTemp.getChartPanel();
                     
                 }
                 case WaterTemp:
                 {
                     return pieChart_WaterTemp.getChartPanel();
                     
                 }
                 case WindSpeed:
                 {
                     return pieChart_WindSpeed.getChartPanel();
                     
                 }
                 case BarometricPressure:
                 {
                     return pieChart_BarometricPressure.getChartPanel();
                     
                 }
                 default:
                 {
                     return pieChart_PrimaryWaterLevel.getChartPanel();
                 }
                 
             }
       
   }
   
   
    /*
    Function: 
        getLineGraphChart()
    
    Purpose:
        Obtain the JFreeChart object that
        was created in the lineGraph class.
   
        Where getLineGraph() returns the graph object itself,
        this class returns the pre-formatted chart object for ready display
    
    */   
   @Override
   public JFreeChart getLineGraphChart( String selectIn )
   {
       
        sampleType select = stringToSampleType( selectIn );

       
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     return lineGraph_PrimaryWaterLevel.getChartPanel();
                     
                 }
                 case StormSurge:
                 {
                     return lineGraph_StormSurge.getChartPanel();
                     
                 }
                 case AirTemp:
                 {
                     return lineGraph_AirTemp.getChartPanel();
                     
                 }
                 case WaterTemp:
                 {
                     return lineGraph_WaterTemp.getChartPanel();
                     
                 }
                 case WindSpeed:
                 {
                     return lineGraph_WindSpeed.getChartPanel();
                     
                 }
                 case BarometricPressure:
                 {
                     return lineGraph_BarometricPressure.getChartPanel();
                     
                 }
                 default:
                 {
                     return lineGraph_PrimaryWaterLevel.getChartPanel();
                 }
                 
             }
       
   }
      
   
    /*
    Function: 
        getLienGraphChart()
    
    Purpose:
        Obtain the JFreeChart object that
        was created in the barGraph class.
   
        Where getLineGraphChart() returns the graph object itself,
        this class returns the pre-formatted chart object for ready display
    
    */   
   
   @Override  
   public JFreeChart getBarGraphChart( String selectIn )
   {
       sampleType select = stringToSampleType( selectIn );
       
             switch ( select )
             {
                 case PrimaryWaterLevel:
                 {
                     return barGraph_PrimaryWaterLevel.getChartPanel();
                     
                 }
                 case StormSurge:
                 {
                     return barGraph_StormSurge.getChartPanel();
                     
                 }
                 case AirTemp:
                 {
                     return barGraph_AirTemp.getChartPanel();
                     
                 }
                 case WaterTemp:
                 {
                     return barGraph_WaterTemp.getChartPanel();
                     
                 }
                 case WindSpeed:
                 {
                     return barGraph_WindSpeed.getChartPanel();
                     
                 }
                 case BarometricPressure:
                 {
                     return barGraph_BarometricPressure.getChartPanel();
                     
                 }
                 default:
                 {
                     return barGraph_PrimaryWaterLevel.getChartPanel();
                 }
                 
             }
       
   }
   
   
   
    /*
    Function: 
        initialiozeEmptyGraphs()
    
    Purpose:
        Just make the blank objects with the specified constructor details
        that determine values such as the names and chart legends
    
    */
   protected void initializeEmptyGraphs()
   {
         pieChart_PrimaryWaterLevel = new PieChart( "Primary Water Level - Monthly Totals");
         pieChart_StormSurge = new PieChart( "Storm Surge - Monthly Totals");
         pieChart_AirTemp = new PieChart( "Air Temperature - Monthly Totals");
         pieChart_WaterTemp = new PieChart("Water Temperature - Monthly Totals");
         pieChart_WindSpeed = new PieChart("Wind Speed - Monthly Totals");
         pieChart_BarometricPressure = new PieChart("Barometric Pressure - Monthly Totals");


         lineGraph_PrimaryWaterLevel = new LineGraph( "Primary Water Pressure", "Monthly Average", "Meters" );
         lineGraph_StormSurge = new LineGraph( "Storm Surge", "Monthly Average", "Meters" );
         lineGraph_AirTemp = new LineGraph( "Air Temperature", "Monthly Average", "Celsius" );
         lineGraph_WaterTemp = new LineGraph( "Water Temperature", "Monthly Average", "Celsius" );
         lineGraph_WindSpeed = new LineGraph( "Wind Speed", "Monthly Average", "Celsius" );
         lineGraph_BarometricPressure = new LineGraph( "Barometric Pressure", "Monthly Average", "Millibars" );

         
         
         barGraph_PrimaryWaterLevel = new BarGraph( "Primary Water Pressure", "Monthly Average", "Meters" );
         barGraph_StormSurge = new BarGraph( "Storm Surge", "Monthly Average", "Meters" );
         barGraph_AirTemp = new BarGraph( "Air Temperature", "Monthly Average", "Celsius" );
         barGraph_WaterTemp = new BarGraph( "Water Temperature", "Monthly Average", "Celsius" );
         barGraph_WindSpeed = new BarGraph( "Wind Speed", "Monthly Average", "Celsius" );
         barGraph_BarometricPressure = new BarGraph( "Barometric Pressure", "Monthly Average", "Millibars" );
   }
   
    /*
    Function: 
        getSelectMenu()
    
    Purpose:
        Get a drop-down menu ( JComboBox ) to allow the user to select which 
        chart to view.
    
    */
   
   @Override
   public JComboBox getSelectMenu()
   {
       JComboBox dropMenu;
       
       ArrayList<String> sampleTypes = new ArrayList<String>();
       
       for (sampleType sample : sampleType.values())
       {
           sampleTypes.add(sampleTypeToString(sample));  
       }
       
       dropMenu = new JComboBox(sampleTypes.toArray(new String[sampleTypes.size()]));
       
       return dropMenu;
       
   }
      
   
    /*
    Function: 
        getDropMenu()
    
    Purpose:
        Return an array of strings that has the list of values
        that are need to populate the drop down menu,
        that allows the user to select which chart to display
    
    */   

    /**
     *
     * @return
     */
       
   @Override
   public String[] getDropMenu()
   {
       //JComboBox dropMenu;
       
       ArrayList<String> sampleTypes = new ArrayList<String>();
       
       for (sampleType sample : sampleType.values())
       {
           sampleTypes.add(sampleTypeToString(sample));  
       }
       
       //dropMenu = new JComboBox(sampleTypes.toArray(new String[sampleTypes.size()]));
       
       return (sampleTypes.toArray(new String[sampleTypes.size()]));
       
   }

}
