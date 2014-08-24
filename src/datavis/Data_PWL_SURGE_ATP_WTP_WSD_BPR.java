package datavis;

import java.util.Date;

/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014
    
    This class represents one node of data from TCOON weather sources,
    that represents some instance, total or average of data during some selected
    timeslice, such as reading of storm surge levels every day at midnight.

    The class features the following sample data types:

        Primary Water Level
        Storm Surge
        Air Temperature
        Water Temperature
        Wind Speed
        Barometric Pressure
		
			JFreeChart library Source: http://www.jfree.org/jfreechart/

*/


public class Data_PWL_SURGE_ATP_WTP_WSD_BPR extends DataTCOON
{

   //data fields
   private double primaryWaterLevel;
   private double stormSurge;
   private double airTemp;
   private double waterTemp;
   private double windSpeed;
   private double barometricPressure;



   //constructors

   public Data_PWL_SURGE_ATP_WTP_WSD_BPR()
   {
      super();

      setPrimaryWaterLevel(0.0);
      setStormSurge(0.0);
      setAirTemp(0.0);
      setWaterTemp(0.0);
      setWindSpeed(0.0);
      setBarometricPressure(0.0);

   }

   public Data_PWL_SURGE_ATP_WTP_WSD_BPR( String elemNameIn, String stationNameIn, int stationIDIn, String stationAbrIn, Date dateIn, 
           double primaryWaterLevelIn, double stormSurgeIn, double airTempIn, double waterTempIn, double windSpeedIn, double barometricPressureIn )
   {
      super( elemNameIn, stationNameIn, stationIDIn, stationAbrIn, dateIn );

      setPrimaryWaterLevel(primaryWaterLevelIn);
      setStormSurge(stormSurgeIn);
      setAirTemp(airTempIn);
      setWaterTemp(waterTempIn);
      setWindSpeed(windSpeedIn);
      setBarometricPressure(barometricPressureIn);

   }




   //setters and getters

   public void setPrimaryWaterLevel( double primaryWaterLevelIn )
   {
      primaryWaterLevel = primaryWaterLevelIn;
   }

   public double getPrimaryWaterLevel()
   {
      return primaryWaterLevel;
   }


   public void setStormSurge( double stormSurgeIn )
   {
      stormSurge = stormSurgeIn;
   }

   public double getStormSurge()
   {
      return stormSurge;
   }

   
   public void setAirTemp( double airTempIn )
   {
      airTemp = airTempIn;
   }

   public double getAirTemp()
   {
      return airTemp;
   }


   public void setWaterTemp( double waterTempIn )
   {
      waterTemp = waterTempIn;
   }

   public double getWaterTemp()
   {
      return waterTemp;
   }


   public void setWindSpeed( double windSpeedIn )
   {
      windSpeed = windSpeedIn;
   }

   public double getWindSpeed()
   {
      return windSpeed;
   }


   public void setBarometricPressure( double barometricPressureIn )
   {
      barometricPressure = barometricPressureIn;
   }

   public double getBarometricPressure()
   {
      return barometricPressure;
   }




   //display methods

   @Override
   public String toString()
   {
      String line0 = String.format( "%s\n",      getElementName() );
      String line1 = String.format( "Water level:    %.2f\n", getPrimaryWaterLevel() );
      String line2 = String.format( "Storm surge:    %.2f\n", getStormSurge() );
      String line3 = String.format( "Air temperature:    %.2f\n", getAirTemp() );
      String line4 = String.format( "Water temperature:  %.2f\n", getWaterTemp() );
      String line5 = String.format( "Wind speed:    %.2f\n", getWindSpeed() );
      String line6 = String.format( "Air pressure:    %.2f\n", getBarometricPressure() );
      String line7 = String.format( "%s", super.getDateString());

      String allLines = line0 + line1 + line2 + line3 + line4 + line5 + line6 + line7;

      return allLines;
   }

}
