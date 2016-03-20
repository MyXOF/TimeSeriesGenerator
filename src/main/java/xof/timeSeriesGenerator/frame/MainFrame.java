package xof.timeSeriesGenerator.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.JFreeChart;

import xof.timeSeriesGenerator.controller.GeneratorController;
import xof.timeSeriesGenerator.enums.DataType;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.Font;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField deviceID;
	private JTextField sensorID;
	private JTextField dataCount;
	private JTextField dataPeriod;
	private JTextField fileName;
	private JButton startButton ;
	private JButton stopButton;
	private JButton emptyButton;
	private JComboBox<DataType> dataType;
	private JProgressBar progressBar;
	private TextArea record;
	private JFreeChart chart;
	private GeneratorController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.init();
					frame.addConnectionWithController();
					frame.addButtonListener();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblDevice = new JLabel("\u8BBE\u5907\u540D");
		lblDevice.setVerticalAlignment(SwingConstants.TOP);
		lblDevice.setBounds(13, 25, 46, 14);
		contentPane.add(lblDevice);
		
		deviceID = new JTextField();
		deviceID.setText("device1");
		deviceID.setBounds(73, 22, 86, 20);
		contentPane.add(deviceID);
		deviceID.setColumns(10);
		
		JLabel lblSensor = new JLabel("\u4F20\u611F\u5668\u540D");
		lblSensor.setVerticalAlignment(SwingConstants.TOP);
		lblSensor.setBounds(169, 25, 69, 14);
		contentPane.add(lblSensor);
		
		sensorID = new JTextField();
		sensorID.setText("sensor1");
		sensorID.setBounds(232, 22, 86, 20);
		contentPane.add(sensorID);
		sensorID.setColumns(10);
		
		startButton = new JButton("\u5F00\u59CB");
		startButton.setBounds(507, 52, 108, 39);
		contentPane.add(startButton);
		
		stopButton = new JButton("\u7ED3\u675F");

		stopButton.setEnabled(false);
		stopButton.setBounds(625, 52, 108, 39);
		contentPane.add(stopButton);
		
		JLabel lblNewLabel = new JLabel("\u91C7\u6837\u7C7B\u578B");
		lblNewLabel.setBounds(358, 25, 61, 14);
		contentPane.add(lblNewLabel);
		
		dataType = new JComboBox<DataType>();
		dataType.setModel(new DefaultComboBoxModel<DataType>(DataType.values()));
		dataType.setBounds(429, 22, 134, 20);
		contentPane.add(dataType);
		
		JLabel lblNewLabel_1 = new JLabel("\u91C7\u6837\u6570\u636E\u91CF");
		lblNewLabel_1.setBounds(13, 56, 68, 14);
		contentPane.add(lblNewLabel_1);
		
		dataCount = new JTextField();
		dataCount.setText("10000");
		dataCount.setBounds(89, 53, 86, 20);
		contentPane.add(dataCount);
		dataCount.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u91C7\u6837\u95F4\u9694(ms)");
		lblNewLabel_2.setBounds(185, 56, 84, 14);
		contentPane.add(lblNewLabel_2);
		
		dataPeriod = new JTextField();
		dataPeriod.setText("500");
		dataPeriod.setBounds(285, 53, 86, 20);
		contentPane.add(dataPeriod);
		dataPeriod.setColumns(10);
		
		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setBounds(13, 119, 479, 23);
		contentPane.add(progressBar);
		
		record = new TextArea();
		record.setBounds(528, 119, 246, 405);
		contentPane.add(record);
		
		JLabel label = new JLabel("\u4FDD\u5B58\u4E3A");
		label.setBounds(13, 84, 46, 14);
		contentPane.add(label);
		
		fileName = new JTextField();
		fileName.setText("output1");
		fileName.setBounds(73, 81, 134, 20);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		emptyButton = new JButton("清空日志");
		emptyButton.setBounds(586, 537, 126, 23);
		contentPane.add(emptyButton);
	}
	
	public void init(){
		controller = new GeneratorController();
	}
	
	public void addConnectionWithController(){
		controller.setChart(chart);
		controller.setProgressBar(progressBar);
		controller.setRecord(record);
		controller.setStartButton(startButton);
		controller.setStopButton(stopButton);
		controller.setEmptyButton(emptyButton);
	}
	
	public void addButtonListener(){
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(controller.initParameters(deviceID, sensorID, fileName, dataCount, dataPeriod, dataType)){
					controller.start();
				}
			}
		});
		
		stopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.stop();
			}
		});
		
		emptyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				record.setText("");
			}
		});
	}
}
