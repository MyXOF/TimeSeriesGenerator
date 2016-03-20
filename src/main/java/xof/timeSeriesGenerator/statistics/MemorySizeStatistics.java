package xof.timeSeriesGenerator.statistics;

public class MemorySizeStatistics  extends Statistics <Long>{

	@Override
	public Long getStatistics() {
		long sizeFree = osmb.getFreePhysicalMemorySize();
		return sizeFree;
	}

	public static  void main(String[] args) throws InterruptedException{
		MemorySizeStatistics aMemoryRatioStatistics = new MemorySizeStatistics();
		for(int i = 0;i < 30;i++){
			System.out.println(aMemoryRatioStatistics.getStatistics());
			Thread.sleep(500);
		}
	}
}
