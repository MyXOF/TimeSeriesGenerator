package xof.timeSeriesGenerator.controller;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraphController {
	private JPanel graphPanel;
	
	public GraphController( JPanel graphPanel){
		this.graphPanel = graphPanel;
	}
	
	public void drawGraph(DefaultCategoryDataset dataSet){
		JFreeChart chart = ChartFactory.createLineChart(null,null, null,dataSet,PlotOrientation.VERTICAL, false, true, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(0, 0, 509, 370);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		chartPanel.setVisible(true);
		graphPanel.add(chartPanel);
		graphPanel.setVisible(true);
	}
}
