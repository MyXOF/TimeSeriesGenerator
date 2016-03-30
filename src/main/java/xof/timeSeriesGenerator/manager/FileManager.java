package xof.timeSeriesGenerator.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager {
	private static final Logger logger = LoggerFactory.getLogger(FileManager.class);
	private String intputFile;
	private String outputFile;
	private String deviceID;
	private String sensorID;
	private List<String> valueList;

	public FileManager(String inputFile,String outputFile,String deviceID,String sensorID){
		this.intputFile = inputFile;
		this.outputFile = outputFile;
		this.deviceID = deviceID;
		this.sensorID = sensorID;
		valueList = new ArrayList<String>();
	}
	
	private boolean init() throws IOException{
		File source = new File(intputFile);
		if(!source.exists()){
			logger.error("FileManager: cannot read source file");
			return false;
		}
		BufferedReader reader = new BufferedReader(new FileReader(source));
		String record = null;
		while((record = reader.readLine() )!= null) {
			String[] values = record.trim().split(",");
			valueList.add(values[3]);
		}
		reader.close();	
		return true;
	}
	
	public void service(int frequence,int count,int maxRepeatCount) throws IOException{
		if(!init()){
			return;
		}
		int baseCount = valueList.size() > maxRepeatCount ? maxRepeatCount : valueList.size();
		int index = 0;
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		
		Random random = new Random(System.currentTimeMillis());
		int offsetbase = frequence / 10 * 2;
		int offsetRange = offsetbase * 2;
		long timestamp = System.currentTimeMillis();
		while(count > 0){
			int offset = random.nextInt(offsetRange);
			timestamp = timestamp + frequence - offsetbase + offset;
			String record = String.format("%s,%s,%d,%s\n", deviceID,sensorID,timestamp,valueList.get(index));
			index = (index + 1) % baseCount;
			bufferedWriter.write(record);
			bufferedWriter.flush();
			count--;
		}
		bufferedWriter.close();
		
	}
	
	public static void main(String[] args) throws IOException {
		FileManager app = new FileManager("cpu_output_50Hz", "cpu_dest_50Hz", "device_cpu_1", "sensor_xuyi_1");
		app.service(20, 1000,100);
		
	}
}