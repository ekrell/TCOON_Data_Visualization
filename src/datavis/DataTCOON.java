package datavis;


import java.util.Date;

/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014
    
    This class represents one node of data from TCOON weather sources,
    that represents some instance, total or average of data during some selected
    timeslice, such as reading of storm surge levels every day at midnight.

    The class is abstract as it has no sample data information, only an
    element name, and station information.
	
		JFreeChart library Source: http://www.jfree.org/jfreechart/

*/

public abstract class DataTCOON
{
   
//Section A : Station Information


      //variables

   private String elementName;

   private String stationName;
   private int stationID;
   private String stationAbr;

      //setters and getters
      

   public void setElementName( String elemNameIn )
   {
      elementName = elemNameIn;
   }

   public String getElementName()
   {
      return elementName;
   }


   public void setStationName( String nameIn )
   {
      stationName = nameIn;
   }

   public String getStationName()
   {
      return stationName;
   }


   public void setStationID( int IDIn )
   {
      stationID = IDIn;
   }

   public int getStationID()
   {
      return stationID;
   }


   public void setStationAbr( String AbrIn )
   {
      stationAbr = AbrIn;
   }

   public String getStationAbr()
   {
      return stationAbr;
   }



      //display formatted information

   public String displayStation()
   {
      return String.format( "Station: %s ( %s )\nID: %d", getStationName(), getStationAbr(), getStationID() );
   }



//Section B: Date and Interval Information

      //variables

   private Date date;
   private String Interval;


      //setters and getters
   
   public void setDate( Date dateIn )
   {
      date = dateIn;
   }

   public Date getDate()
   {
      return date;
   }

   public String getDateString()
   {
      return date.toString();
      //convert date to string and return
   }



//Section C: Data fields


   public abstract String toString();





//Section D: Constructors

   public DataTCOON()
   {
      setElementName(null);
      setStationName(null);
      setStationID(0);
      setStationAbr(null);

      //setDate("");

   }

   public DataTCOON( String elemNameIn, String stationNameIn, int stationIDIn, String stationAbrIn, Date dateIn )
   {
      setElementName(elemNameIn);

      setStationName(stationAbrIn);
      setStationID(stationIDIn);
      setStationAbr(stationAbrIn);

      setDate(dateIn);
   }

}
