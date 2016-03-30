package xof.timeSeriesGenerator.statistics;

public class AllStatistics extends Statistics<String>{
	private CpuStatistics  cpu;
	private MemoryRatioStatistics mr;
	private MemorySizeStatistics ms;
	
	
	@Override
	public String getStatistics() {
		cpu = new CpuStatistics();
		mr = new MemoryRatioStatistics();
		ms = new MemorySizeStatistics();
		
		float cpuLoad = cpu.getStatistics();
		float memRatio = mr.getStatistics();
		long memSize = ms.getStatistics();
		
		return String.format("%f,%f,%d", cpuLoad,memRatio,memSize);
	}


}
