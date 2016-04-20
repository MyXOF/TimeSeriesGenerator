package xof.timeSeriesGenerator.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileManager {
	private String outputFile;
	
	public FileManager(String outputFile){
		this.outputFile = outputFile;
	}
	
	public void createLargeFile(String device,int frequence, int count, List<FileInfo> infoList) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		Random random = new Random(System.currentTimeMillis());
		int index = 0;
		int offsetbase = frequence / 10 * 2;
		int offsetRange = offsetbase * 2;
		long timestamp = System.currentTimeMillis();
		final int BASE_FREQ = 50;
		
		while(count > 0){
			int offset = random.nextInt(offsetRange);
			timestamp = timestamp + frequence - offsetbase + offset;

			StringBuilder builder = new StringBuilder();
			builder.append(device);
			builder.append(",");
			builder.append(timestamp);
			
			for(FileInfo info : infoList){
				if(info.frequency == 50){
					builder.append(",");
					builder.append(createSensorValuePair(info.sensorID, info.getNext()));
				}
				else if((info.frequency == 10) && (index % 5 == 0)){
					builder.append(",");
					builder.append(createSensorValuePair(info.sensorID, info.getNext()));
				}
				else if((info.frequency == 1) && (index % 50 == 0)){
					builder.append(",");
					builder.append(createSensorValuePair(info.sensorID, info.getNext()));
				}else{
					builder.append(",");
					builder.append(createSensorValuePair(info.sensorID, ""));
				}
			}
			builder.append("\n");
			index = (index + 1) % BASE_FREQ;
			bufferedWriter.write(builder.toString());
			bufferedWriter.flush();
			count--;
		}
		bufferedWriter.close();
	}
	
	private String createSensorValuePair(String sensor,String value){
		return String.format("%s,%s",sensor,value);
	}
	
	public FileInfo collectFileInfo(String filePath,int frequency,String sensorID,boolean isfloat,boolean isM) throws IOException{
		FileInfo info = new FileInfo(filePath, frequency, sensorID);
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String record = null;
		while((record = reader.readLine()) != null){
			String[] values = record.trim().split(",");
			if(isfloat){
				int value = (int) (Float.parseFloat(values[3]));
				info.addValue(String.valueOf(value));
			}else if(isM){
				int value = Integer.parseInt(values[3]) / 1024;
				info.addValue(String.valueOf(value));
			}
			else{
				info.addValue(values[3]);
			}
		}
		reader.close();
		info.setSize();
		return info;
	}
	
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		FileManager manager = new FileManager("data/output_final_big.csv");
		List<FileInfo> infoList = new ArrayList<FileManager.FileInfo>();
		infoList.add(manager.collectFileInfo("data/cpu_output_50Hz", 50, "sensor_cpu_50", true,false));
		infoList.add(manager.collectFileInfo("data/cpu_output_10Hz", 10, "sensor_cpu_10", true,false));
		infoList.add(manager.collectFileInfo("data/cpu_output_kr_1Hz", 1, "sensor_cpu_1", true,false));
		infoList.add(manager.collectFileInfo("data/mr_output_10Hz", 10, "sensor_mr_10", true,false));
		infoList.add(manager.collectFileInfo("data/ms_output_50Hz_0", 50, "sensor_ms_50", false,false));
//		infoList.add(manager.collectFileInfo("data/disk_output_50Hz", 50, "sensor_df_50", false,true));
		manager.createLargeFile("device_1", 20, 2000000, infoList);
		
//		List<FileInfo> infoList = new ArrayList<FileManager.FileInfo>();
//		infoList.add(manager.collectFileInfo("data2/cpu_output_50Hz", 50, "sensor_cpu_50", true));
//		infoList.add(manager.collectFileInfo("data2/cpu_output_10Hz", 10, "sensor_cpu_10", true));
//		infoList.add(manager.collectFileInfo("data2/cpu_output_kr_1Hz", 1, "sensor_cpu_1", true));
//		infoList.add(manager.collectFileInfo("data2/mr_output_10Hz", 10, "sensor_mr_10", true));
////		infoList.add(manager.collectFileInfo("data2/ms_output_50Hz_0", 50, "sensor_ms_50", false));
//		infoList.add(manager.collectFileInfo("data2/disk_output_50Hz", 50, "sensor_df_50", false,true));
//		manager.createLargeFile("device_2", 20, 10000000, infoList);
		
		long end = System.currentTimeMillis() - start;
		System.out.println("It costs "+end+" ms");
	}
	
	class FileInfo{
		public String filePath;
		public int frequency;
		public String sensorID;
		public List<String> values;
		private int index;
		private int size;
		
		public FileInfo(String filePath,int frequency,String sensorID){
			this.filePath = filePath;
			this.frequency = frequency;
			this.sensorID = sensorID;
			this.values = new ArrayList<String>();
			index = 0;
		}
		
		public void addValue(String value){
			if(values == null){
				values = new ArrayList<String>();
			}
			values.add(value);
		}
		
		public void setSize(){
			size = values.size();
		}
		
		public String getNext(){
			String result = values.get(index);
			index = (index + 1) % size;
			return result;
		}
	}
}