package xof.timeSeriesGenerator.statistics;

import java.lang.management.ManagementFactory; 
import com.sun.management.OperatingSystemMXBean;

public abstract class Statistics<T> {
	protected OperatingSystemMXBean osmb;
	public Statistics(){
		osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}
	
	public abstract T getStatistics();
}
