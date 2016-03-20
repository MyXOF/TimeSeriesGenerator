package xof.timeSeriesGenerator.controller;

import java.awt.TextArea;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xof.timeSeriesGenerator.enums.DataType;
import xof.timeSeriesGenerator.factory.StatisticsFactory;
import xof.timeSeriesGenerator.statistics.Statistics;

public class GeneratorController {
	private static final Logger logger = LoggerFactory.getLogger(GeneratorController.class);
	private final int BATCH_NUM = 100;
	private String deviceID;
	private String sensorID;
	private DataType dataType;
	private String path;
	private long count;
	private int period;
	private JProgressBar progressBar;
	private TextArea record;
	private JFreeChart chart;
	private JButton startButton;
	private JButton stopButton;
	private JButton emptyButton;
	private boolean isStopped;
	private ExecutorService service;

	public GeneratorController() {
		isStopped = true;
		service = Executors.newCachedThreadPool();
	}

	public boolean initParameters(JTextField deviceField, JTextField sensorField, JTextField pathField, JTextField dataCountField,JTextField dataPeriodField, JComboBox<DataType> dataTypeField) {
		if (!checkTextFieldisNull(deviceField, "device id"))
			return false;
		if (!checkTextFieldisNull(sensorField, "sensor id"))
			return false;
		if (!checkTextFieldisNull(pathField, "file path"))
			return false;
		if (!checkTextFieldisNull(dataCountField, "data count"))
			return false;
		if (!checkTextFieldisNull(dataPeriodField, "data record"))
			return false;

		if (dataTypeField == null || dataTypeField.getSelectedItem().equals("")) {
			record.append("Error: data type is empty");
		}
		deviceID = deviceField.getText();
		sensorID = sensorField.getText();
		dataType = (DataType) dataTypeField.getSelectedItem();
		path = pathField.getText();
		try {
			count = Long.parseLong(dataCountField.getText());
			if (count <= 0) {
				record.append("Error: data count must be postive\n");
				return false;
			}
		} catch (Exception e) {
			record.append("Error: data count must be a number\n");
		}
		try {
			period = Integer.parseInt(dataPeriodField.getText());
			if (period <= 0) {
				record.append("Error: data period must be postive\n");
				return false;
			}
		} catch (Exception e) {
			record.append("Error: data period must be a number\n");
		}
		return true;
	}

	public void start() {
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		emptyButton.setEnabled(false);
		record.append(String.format("start collecting %s data.....\n", dataType.toString()));

		Statistics<?> statistics = StatisticsFactory.getStatistics(dataType.toString());
		isStopped = false;
		init();
		service.execute(new collectorThread(statistics));
	}

	private void init() {
		progressBar.setValue(0);
	}

	private void end() {
		record.append(String.format("end collecting %s data.....\n\n", dataType.toString()));
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		emptyButton.setEnabled(true);
	}

	public void stop() {
		isStopped = true;
	}

	private boolean checkTextFieldisNull(JTextField field, String name) {
		if (field == null || field.getText().equals("")) {
			record.append(String.format("Error: %s is empty\n", name));
			return false;
		}
		return true;
	}

	class collectorThread implements Runnable {
		private Statistics<?> statistics;

		public collectorThread(Statistics<?> statistics) {
			this.statistics = statistics;
		}

		@Override
		public void run() {
			try {
				File file = new File(path);
				if(!file.exists()){
					file.createNewFile();
				}
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				
				long leftCount = count;
				StringBuilder builder = new StringBuilder();
				int currentIndex = 0;
				while (!isStopped && leftCount > 0) {
					Object value = statistics.getStatistics();
					String content = String.format("%s,%s,%d,%s\n", deviceID, sensorID, System.currentTimeMillis(),value.toString());
					record.append(content);
					builder.append(content);
					currentIndex++;
					if(currentIndex > BATCH_NUM){
						bufferedWriter.write(builder.toString());
						bufferedWriter.flush();
						builder.delete(0, builder.length());
						currentIndex = 0;
					}
					try {
						Thread.sleep(period);
					} catch (InterruptedException e) {

					}
					leftCount--;
					setProcessBar(leftCount);
				}
				
				if(bufferedWriter != null){
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (Exception e) {
				logger.error("collectorThread: error occurs",e);
			} finally {
				end();
			}
		}

		private void setProcessBar(long leftCount) {
			double ratio = (double) leftCount / count;
			int value = (int) ((1 - ratio) * 100);
			progressBar.setValue(value);
		}

	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public TextArea getRecord() {
		return record;
	}

	public void setRecord(TextArea record) {
		this.record = record;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public void setStopButton(JButton stopButton) {
		this.stopButton = stopButton;
	}

	public JButton getEmptyButton() {
		return emptyButton;
	}

	public void setEmptyButton(JButton emptyButton) {
		this.emptyButton = emptyButton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
