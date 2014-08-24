
package datavis;


import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;


/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014

    The class manages a DefaultPieDataset object and JFreeChart object,
    to control how a class is able to add to and display the graph
	
		JFreeChart library Source: http://www.jfree.org/jfreechart/


*/
public class PieChart {
    
        
    private DefaultPieDataset pieDataSet;
    private String pieDataSetName;
    
    
    
    public PieChart()
    {
        
        pieDataSet = new DefaultPieDataset();
        pieDataSetName = null;
        
    }
    
        //setters and getters
    
    public PieChart( DefaultPieDataset pieIn, String nameIn )
    {
        pieDataSet = pieIn;
        pieDataSetName = nameIn;
    }
    
    public PieChart( String nameIn)
    {
        pieDataSet = new DefaultPieDataset();
        pieDataSetName = nameIn;
    }
    
    
    
    public void setPieChartName( String nameIn )
    {
        pieDataSetName = nameIn;
    }
    
    public String getPieChartName()
    {
        return pieDataSetName;
    }
    
    
        //add the data
    public void addData( String sliceName, Double dataIn )
    {
        pieDataSet.setValue(sliceName, new Double(dataIn) );
    }
    
        //get the PieChart
    public DefaultPieDataset getPieChart()
    {
        return pieDataSet;
    }
    
        //Get the particularly customized chart
    public JFreeChart getChartPanel()
    {
        JFreeChart chart = ChartFactory.createPieChart( getPieChartName(), getPieChart(), true, true, true );
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setShadowXOffset( 0.0 );
        plot.setShadowYOffset( 0.0 );
        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(Color.darkGray);
        plot.setLabelShadowPaint( new Color( 0, 0, 0, 0 ));
        
        return chart;
    }
    
}
