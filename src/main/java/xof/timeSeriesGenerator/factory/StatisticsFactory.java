package xof.timeSeriesGenerator.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xof.timeSeriesGenerator.statistics.Statistics;

public class  StatisticsFactory {
	private static final Logger logger = LoggerFactory.getLogger(StatisticsFactory.class);
	private final static String PACKAGE = "xof.timeSeriesGenerator.statistics.";
	
	public static Statistics<?> getStatistics(String className){
		Class<Statistics<?>> statisticsClass = null;
		Statistics<?> statistics = null;
		try {
			statisticsClass = (Class<Statistics<?>>) Class.forName(PACKAGE+className);
		} catch (ClassNotFoundException e) {
			logger.error("StatisticsFactory : failed to get class name {}",className,e);
		}
		try {
			statistics = statisticsClass.newInstance();
		} catch (InstantiationException e) {
			logger.error("StatisticsFactory: failed to get new instance of {}",className,e);
		} catch (IllegalAccessException e) {
			logger.error("StatisticsFactory: failed to access new instance of {}",className,e);
		}
		return statistics;
	}
	
	public static  void main(String[] args){
		Statistics<?> statistics = StatisticsFactory.getStatistics("CpuStatistics");
	}
}
