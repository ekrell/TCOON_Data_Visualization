
package datavis;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/*
    Authors: Evan Krell, David Neathery, Javier A. Villarreal
    Date: May 5, 2014

	The main class that displays a Graphical User Interface
	to load data from local file or TCOON server, display the charts,
	and allow interaction with the charts.
	
	JFreeChart library Source: http://www.jfree.org/jfreechart/


*/
public class Gui extends javax.swing.JFrame {

    
    
    private void initGraphs( DataList dataset )
    {

		//Initialize the GUI with default, blank sample graphs
		//That serve as place holders for that actual content
        
		//Create Pie Chart
        PieChart samplePie = new PieChart( "Sample Data" );
        samplePie.addData( "Default Value", 1.0);
        JFreeChart chart = samplePie.getChartPanel();
      
		//Add chart to GUI
        javax.swing.JPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(jPanel1.getSize());
        jPanel1.add(chartPanel);
        jPanel1.getParent().validate();


        //Create Line graph
        DefaultCategoryDataset sampleLine = new DefaultCategoryDataset();
        sampleLine.setValue( 1.0, "sample Data" , "Sample Data" );
        JFreeChart chart2 = ChartFactory.createLineChart( "Sample Data", "Sample", "Sample", sampleLine );
        
        //Add chart to GUI
        javax.swing.JPanel chartPanel2 = new ChartPanel(chart2);
        chartPanel2.setSize(jPanel2.getSize());
        jPanel2.add(chartPanel2);
        jPanel2.getParent().validate();


		//Create bar graph
        DefaultCategoryDataset sampleBar = new DefaultCategoryDataset();
        sampleLine.setValue( 1.0, "sample Data" , "Sample Data" );
        JFreeChart chart3 = ChartFactory.createBarChart( "Sample Data", "Sample", "Sample", sampleBar );
        
		//Add chart to GUI
        javax.swing.JPanel chartPanel3 = new ChartPanel(chart3);
        chartPanel3.setSize(jPanel3.getSize());
        jPanel3.add(chartPanel3);
        jPanel3.getParent().validate();


        //Set the author information to the info box
        jTextArea2.setText( displayDevelopers );
    }
    
    // Function that displays the Pie Chart
    private void updateGraphs_pieChart( DataList dataset, boolean load )
    {
        String sItem = "default";
		
		//If you are initially loading the data..
        if( load )
        {
            sItem = "default"; 	//Display the default chart
        }
        else
        {
			//otherwise, display the chart from the drop-down menu selection
            sItem = jComboBox1.getSelectedItem().toString();
        }
		
		//modify the string to match what is needed
		//To select the proper graph
        sItem = sItem.replaceAll("\\s+","");
        sItem = Character.toLowerCase(sItem.charAt(0)) + (sItem.length() > 1 ? sItem.substring(1) : "");
        
		//Reinit and Add chart to GUI
        jPanel1.removeAll();
        jPanel1.revalidate();
        JFreeChart chart = dataset.getPieChartChart(sItem);
        chart.removeLegend();
        javax.swing.JPanel chartPanel = new ChartPanel(chart);       
        chartPanel.setSize(jPanel1.getSize());

        jPanel1.add(chartPanel);
        jPanel1.repaint();

    }
    
	//Function that displays the bar graph
    private void updateGraphs_barGraph( DataList dataset, boolean load )
    {
        String sItem = "default";
        
        if( load )
        {
            sItem = "default";
        }
        else
        {
            sItem = jComboBox2.getSelectedItem().toString();
        }
        
        sItem = sItem.replaceAll("\\s+","");
        sItem = Character.toLowerCase(sItem.charAt(0)) + (sItem.length() > 1 ? sItem.substring(1) : "");
        
        jPanel2.removeAll();
        jPanel2.revalidate();
        JFreeChart chart = dataset.getLineGraphChart(sItem);
        chart.removeLegend();
        javax.swing.JPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(jPanel2.getSize());

        jPanel2.add(chartPanel);
        jPanel2.repaint();
        
    }
    
    //Function that displays the line graph
    private void updateGraphs_lineGraph( DataList dataset, boolean load )
    {
        String sItem = "default";
        if( load )
        {
            sItem = "default";
        }
        else
        {
            sItem = jComboBox3.getSelectedItem().toString();
        }

            sItem = sItem.replaceAll("\\s+","");
            sItem = Character.toLowerCase(sItem.charAt(0)) + (sItem.length() > 1 ? sItem.substring(1) : "");

            jPanel3.removeAll();
            jPanel3.revalidate();


            JFreeChart chart = dataset.getBarGraphChart(sItem);
            chart.removeLegend();
            javax.swing.JPanel chartPanel = new ChartPanel(chart);
            chartPanel.setSize(jPanel3.getSize());

            jPanel3.add(chartPanel);
            jPanel3.repaint();
    }
    
   
	//Gui constructor
    public Gui() 
    {
        
    initComponents();
        
    
        
  
    
    initGraphs( dataset );
    
    // <editor-fold defaultstate="collapsed" desc="Intervals for dialogue box">  
    intervals = new HashMap<>();
    intervals.put("Every six minutes", 360);
    intervals.put("Every half hour at :00 and :30", 1800);
    intervals.put("Every hour at :00", 3600);
    intervals.put("Every three (3) hours", 10800);
    intervals.put("Every six (6) hours", 21600);
    intervals.put("Every nine (9) hours", 32400);
    intervals.put("Every twelve (12) hours", 43200);
    intervals.put("Every day at midnight (UTC)", 86400);
    intervals.put("Every month", 0);
    // </editor-fold>
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 914, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setAutoscrolls(false);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        button1.setLabel("Import Existing");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setLabel("Import From Web");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Pop-Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton2.setText("Pop-Out");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton3.setText("Pop-Out");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addGap(64, 64, 64)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(19, 19, 19)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        

        updateGraphs_pieChart( dataset, false );
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
      
        
        updateGraphs_barGraph( dataset, false );
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
      
        
        updateGraphs_lineGraph( dataset, false );
        
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed

		//The action to select a file 
		//By opening a pop-up containing a file browser
	
        
        javax.swing.JFileChooser choose = new javax.swing.JFileChooser();

        choose.showOpenDialog(null);
        
        File f = choose.getSelectedFile();
        
        String fileHandle = f.getAbsolutePath();
        
        System.out.println(fileHandle);
        
       // jComboBox1.removeAllItems();
      //  jComboBox2.removeAllItems();
      //  jComboBox3.removeAllItems();
        
        try {        
          dataset = new DataList_PWL_SURGE_ATP_WTP_WSD_BPR( fileHandle );
        } catch (UnsupportedEncodingException ex) {
          JOptionPane.showMessageDialog(null, "The data file you selected is not supported.");
          return;
        }
		
		//Set the text of the button to the file handle absolute pathname
        button1.setLabel(fileHandle);
		
		//update all graphs
        updateGraphs_pieChart( dataset, true );
        updateGraphs_barGraph( dataset, true );
        updateGraphs_lineGraph( dataset, true );
        
		
		//get the drop down menu items from the dataset class
        String sampleTypes[] = dataset.getDropMenu();
        
        //fill the node text area with all the node information
        jTextArea1.setText(dataset.toString());
        jTextArea1.setCaretPosition(0);

		//fill the status text area with station and developer info
        jTextArea2.setText(dataset.getStationInfo() + displayDevelopers);
        jTextArea2.setCaretPosition(0);
        
        //clear the combobox
        for(int i = 0; i < jComboBox1.getItemCount(); i++)               
        {
            jComboBox1.remove(i);
            jComboBox2.remove(i);
            jComboBox3.remove(i);
        } 

		//Add each sample type to the drop-down combobox
        for(int i = 0; i < sampleTypes.length; i++)               
        {
            jComboBox1.addItem(sampleTypes[i]);
            jComboBox2.addItem(sampleTypes[i]);
            jComboBox3.addItem(sampleTypes[i]);
        }
        
        

        
        
    }//GEN-LAST:event_button1ActionPerformed

  private void button2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_button2ActionPerformed
  {//GEN-HEADEREND:event_button2ActionPerformed
    

      
      
      int station = Integer.parseInt((String)
            ((String) JOptionPane.showInputDialog(null, "Select a station.", 
                    "Web Import", JOptionPane.PLAIN_MESSAGE, null, stations, 
                    stations[8])).subSequence(0, 3));
    int interval = intervals.get(JOptionPane.showInputDialog(null, "Select sample resolution", 
            "Web Import", JOptionPane.PLAIN_MESSAGE, null, intervals.keySet().toArray(), intervals.keySet().toArray()[5]));
    JDatePicker sP = new JDateComponentFactory().createJDatePicker();
    JDatePicker eP = new JDateComponentFactory().createJDatePicker();
    Calendar today = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    sP.getModel().setDate(today.get(Calendar.YEAR)-1, today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
    sP.getModel().setSelected(true);
    eP.getModel().setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
    eP.getModel().setSelected(true);
    JPanel panel = new JPanel();
    panel.add(new JLabel("Start date"));
    panel.add((Component) sP);
    panel.add(new JLabel("End date"));
    panel.add((Component) eP);
    JOptionPane.showMessageDialog(null, panel, "Web Import", JOptionPane.PLAIN_MESSAGE);
    try {
      dataset = getDataFromServer(station, interval, ((GregorianCalendar) sP.getModel().getValue()).getTime(), ((GregorianCalendar) eP.getModel().getValue()).getTime());
    } catch (UnsupportedEncodingException ex) {
      JOptionPane.showMessageDialog(null, "There was an error retreiving data from the server.");
      return;
    }

    updateGraphs_pieChart( dataset, true );
    updateGraphs_barGraph( dataset, true );
    updateGraphs_lineGraph( dataset, true );
    String sampleTypes[] = dataset.getDropMenu();
    
    
    jTextArea1.setText(dataset.toString());
    jTextArea1.setCaretPosition(0);

    jTextArea2.setText(dataset.getStationInfo() + displayDevelopers);
    
    jTextArea2.setCaretPosition(0);
    button1.setLabel("Import Existing");
    
    
    
    for(int i = 0; i < jComboBox1.getItemCount(); i++)               
    {
        jComboBox1.remove(i);
        jComboBox2.remove(i);
        jComboBox3.remove(i);
    }    

    
    
    for(int i = 0; i < sampleTypes.length; i++)               
    {
        jComboBox1.addItem(sampleTypes[i]);
        jComboBox2.addItem(sampleTypes[i]);
        jComboBox3.addItem(sampleTypes[i]);
    }
    

    
  }//GEN-LAST:event_button2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         
		//Initialize a copy of the current Pie chart on display and then create a pop-up containing it
		 
		//Get the chart based on the Combobox item
        String sItem = jComboBox1.getSelectedItem().toString();
        sItem = sItem.replaceAll("\\s+","");
        sItem = Character.toLowerCase(sItem.charAt(0)) + (sItem.length() > 1 ? sItem.substring(1) : "");

        JFreeChart popOut_PieChart = dataset.getPieChartChart( sItem );
        
		//Create the pop-up
        ChartFrame frame = new ChartFrame( dataset.getStationName() + "  (" + dataset.getStationID() + ")", popOut_PieChart );
        frame.setVisible(true);
        frame.setSize( 450,450);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
	   
	   	//Initialize a copy of the current Line chart on display and then create a pop-up containing it
		 
		//Get the chart based on the Combobox item
        String sItem = jComboBox2.getSelectedItem().toString();
        sItem = sItem.replaceAll("\\s+","");
        sItem = Character.toLowerCase(sItem.charAt(0)) + (sItem.length() > 1 ? sItem.substring(1) : "");   
        
		//Create the pop-up
        JFreeChart popOut_PieChart = dataset.getLineGraphChart( sItem );
        ChartFrame frame = new ChartFrame( dataset.getStationName() + "  (" + dataset.getStationID() + ")", popOut_PieChart );
        frame.setVisible(true);
        frame.setSize( 450,450);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
	   	//Initialize a copy of the current Bar chart on display and then create a pop-up containing it
		 
		//Get the chart based on the Combobox item
		
        String sItem = jComboBox3.getSelectedItem().toString();
        sItem = sItem.replaceAll("\\s+","");
        sItem = Character.toLowerCase(sItem.charAt(0)) + (sItem.length() > 1 ? sItem.substring(1) : "");          
        
		//Create the pop-up
        JFreeChart popOut_PieChart = dataset.getBarGraphChart( sItem );
        ChartFrame frame = new ChartFrame( dataset.getStationName() + "  (" + dataset.getStationID() + ")", popOut_PieChart );
        frame.setVisible(true);
        frame.setSize( 450,450);        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed
/**/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

	//The actual dataset object of TCOON data and charts
    DataList_PWL_SURGE_ATP_WTP_WSD_BPR dataset;
    
	
	//The choices for TCOON stations
    String[] stations = new String[]{"003 - Rincon del San Jose", "005 - Packery Channel", 
      "006 - Ingleside", "008 - USS Lexington", "009 - Port Aransas", "013 - S. Bird Island", 
      "017 - Port Mansfield", "031 - Seadrift", "033 - Port Lavaca", "036 - Copano Bay", 
      "051 - S. Padre Island Coast Guard Sta.", "057 - Port O'Connor", "068 - Baffin Bay", 
      "126 - Texas Point", "181 - Realitos Peninsula", "185 - Nueces Bay", 
      "198 - High Island ICWW", "200 - Sargent", "201 - Matagorda City", 
      "202 - Aransas Wildlife Refuge", "248 - Galveston Railroad Bridge", 
      "255 - San Luis Pass Temporary", "503 - Morgans Point", "504 - Rainbow Bridge", 
      "507 - Eagle Point", "513 - Manchester Houston", "517 - Lynchburg", 
      "518 - Rollover Pass", "524 - Port Arthur", "526 - San Luis Pass", 
      "529 - Galveston Entrance Channel, North Jetty"};
      Map<String, Integer> intervals;
      
   //Information related to the developer and creation date
   String authorship = String.format( "Developed by Evan A. Krell, \n   David Neathery, and Javier A. Villarreal.");
   String creationDate = String.format( "Version 01 - May 2014");
   String license = "";
   
   String displayDevelopers = String.format( "\n\n%s\n\n%s\n\n%s", authorship, creationDate, license);
              
      /** 
       * Returns a new PSAWWB data list containing data pulled from TCOON's website
       * @param station The station number, e.g., 33 for Port Lavaca
       * @param interval The number of seconds between data samples, e.g., 86400
       * @param start Date range start date
       * @param end Date range end date. Defaults to 1 year if null
       * @return A fresh DataList
       */
      public static DataList_PWL_SURGE_ATP_WTP_WSD_BPR getDataFromServer(int station, int interval, Date start, Date end) throws UnsupportedEncodingException
      {
        String dateString = new String(), pattern = new String("%25Y%25j%2B%25H%25M");
        DateFormat format = new SimpleDateFormat("MM.dd.yyyy");
        File temp = null;
        URL url;

        if (interval % 360 != 0 || interval < 360)
          interval = 0; // default to monthly
        if (interval >= 86400)
          pattern = "%25m-%25d-%25Y";
        if (start == null || end == null)
          dateString = "now,-1y";
        else
          dateString = format.format(start) + "-" + format.format(end);
        try
        {
          temp = File.createTempFile("TCOON", null);
          temp.deleteOnExit();
          url = new URL(String.format("http://lighthouse.tamucc.edu/pd?stnlist=%03d&serlist=pwl,surge,atp,wtp,wsd,bpr&when=%s&whentz=UTC0&-action=csv&unit=metric&elev=stnd&interval=%s&datefmt=%s&rm=0&na=0", station, dateString, interval>0?String.valueOf(interval):"monthly", pattern));
          Files.copy(url.openStream(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {}

        return new DataList_PWL_SURGE_ATP_WTP_WSD_BPR( temp.getAbsolutePath() );
      }
}
