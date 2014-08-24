package datavis;

import java.util.Date;

public class Test
{
   public static void main( String[] args )
   {

      DataList_PWL_SURGE_ATP_WTP_WSD_BPR dataSet1 = new DataList_PWL_SURGE_ATP_WTP_WSD_BPR();

      Date dateNow = new Date();

      dataSet1.addData( dateNow, 12.3, 45.0, 11.11, 3.3, 667.6, 1.12232 );

      dataSet1.addData( dateNow, 1.1, 23.0, 232.3, 44.32, 1.0, 12.123 );

      System.out.printf( "\n\n%s\n\n", dataSet1.toString() );
      
      
      // DataList_PWL_SURGE_ATP_WTP_WSD_BPR dataSet2 = new DataList_PWL_SURGE_ATP_WTP_WSD_BPR( "dataset-test.dat" );

      //System.out.printf( "\n\n\n\n\n%s\n\n", dataSet2.toString() );
      
      // doThing( dataSet2 );
      

      
   }
   
   
      static void doThing( DataList dataSet30 )
      {
            System.out.printf( "\n\n\n\n\n%s\n\n", dataSet30.toString() );
      }
   
}
