
package datavis;

import java.awt.Color;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;


/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014

    The class manages a DefaultCategoryDataset line graph object and JFreeChart object,
    to control how a class is able to add to and display the graph
	
		JFreeChart library Source: http://www.jfree.org/jfreechart/


*/
public class LineGraph 
{
    
    private DefaultCategoryDataset lineDataSet;
    private String lineDataSetName;
    private String intervalTypeName;
    private String numberTypeName;
    
    
    public LineGraph()
    {
        
        lineDataSet = new DefaultCategoryDataset();
        lineDataSetName = null;
        intervalTypeName = null;
        numberTypeName = null;
        
    }
    
    public LineGraph( DefaultCategoryDataset lineIn, String nameIn, 
            String intervalTypeNameIn, String numberTypeNameIn )
    {
        lineDataSet = lineIn;
        lineDataSetName = nameIn;
        intervalTypeName = intervalTypeNameIn;
        numberTypeName = numberTypeNameIn;
    }
    
    
    public LineGraph( String nameIn, 
            String intervalTypeNameIn, String numberTypeNameIn )
    {
        lineDataSet = new DefaultCategoryDataset();
        lineDataSetName = nameIn;
        intervalTypeName = intervalTypeNameIn;
        numberTypeName = numberTypeNameIn;
    }
    
        //setters and getters
    public void setLineGraphName( String nameIn )
    {
        lineDataSetName = nameIn;
    }
    
    public String getLineGraphName()
    {
        return lineDataSetName;
    }
    
    
        
    public void setIntervalTypeName( String nameIn )
    {
        intervalTypeName = nameIn;
    }
    
    public String getIntervalTypeName()
    {
        return intervalTypeName;
    }
    
    
        
    public void setNumberTypeName( String nameIn )
    {
        numberTypeName = nameIn;
    }
    
    public String getNumberTypeName()
    {
        return numberTypeName;
    }
    
            //add the data

    public void addData( String sliceName, double dataIn, String typeIn )
    {
        lineDataSet.setValue(new Double(dataIn), typeIn , sliceName );
    }
    
        //get the line graph
    public DefaultCategoryDataset getLineGraph()
    {
        return lineDataSet;
    }
    
        //Get the particularly customized chart
    public JFreeChart getChartPanel()
    {
        JFreeChart chart = ChartFactory.createLineChart( getLineGraphName(), getIntervalTypeName(), getNumberTypeName(), getLineGraph() );
                chart.setBackgroundPaint(Color.white);
        
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.darkGray);
        plot.setDomainGridlinePaint( new Color( 240, 180, 180) );
        plot.setRangeGridlinePaint( new Color( 240, 180, 180) );


        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
	renderer.setSeriesPaint(1, Color.blue);
	renderer.setSeriesPaint(2, Color.blue);
        
        
        renderer.setSeriesShapesVisible(0, true);

       final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        
        return chart;
    }
    
}

