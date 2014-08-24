/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datavis;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.font.TextAttribute;
import java.util.Hashtable;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;

public class BarGraph
{
    
    private DefaultCategoryDataset barDataSet;
    private String barDataSetName;
    private String intervalTypeName;
    private String numberTypeName;
    
    
    public BarGraph()
    {
        
        barDataSet = new DefaultCategoryDataset();
        barDataSetName = null;
        intervalTypeName = null;
        numberTypeName = null;
        
    }
    
    public BarGraph( DefaultCategoryDataset lineIn, String nameIn, 
            String intervalTypeNameIn, String numberTypeNameIn )
    {
        barDataSet = lineIn;
        barDataSetName = nameIn;
        intervalTypeName = intervalTypeNameIn;
        numberTypeName = numberTypeNameIn;
    }
    
    
    public BarGraph( String nameIn, 
            String intervalTypeNameIn, String numberTypeNameIn )
    {
        barDataSet = new DefaultCategoryDataset();
        barDataSetName = nameIn;
        intervalTypeName = intervalTypeNameIn;
        numberTypeName = numberTypeNameIn;
    }
    
    public void setBarGraphName( String nameIn )
    {
        barDataSetName = nameIn;
    }
    
    public String getBarGraphName()
    {
        return barDataSetName;
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
    
    
    public void addData( String sliceName, double dataIn, String typeIn )
    {
        barDataSet.setValue(new Double(dataIn), typeIn , sliceName );
    }
    
    public DefaultCategoryDataset getBarGraph()
    {
        return barDataSet;
    }
    
    
    public JFreeChart getChartPanel()
    {
        JFreeChart chart = ChartFactory.createBarChart( getBarGraphName(), getIntervalTypeName(), getNumberTypeName(), getBarGraph(), PlotOrientation.VERTICAL, true, true, false );
        chart.setBackgroundPaint(Color.white);
        
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.darkGray);
        plot.setDomainGridlinePaint( new Color( 240, 180, 180) );
        plot.setRangeGridlinePaint( new Color( 240, 180, 180) );


        final BarRenderer renderer = (BarRenderer) plot.getRenderer(); 
        renderer.setBarPainter(new StandardBarPainter());


        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, new Color( 170, 240, 240), 
            0.0f, 0.0f, new Color( 170, 240, 240)
        );
        
        renderer.setSeriesPaint(0, gp0);
        
       final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        
        return chart;
    }
    
}
