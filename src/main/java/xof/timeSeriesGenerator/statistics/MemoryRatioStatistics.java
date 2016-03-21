package xof.timeSeriesGenerator.statistics;

public class MemoryRatioStatistics extends Statistics<Float>{

	@Override
	public Float getStatistics() {
		long sizeFree = osmb.getFreePhysicalMemorySize();
		long sizeAll = osmb.getTotalPhysicalMemorySize();
		double ratio =((double) sizeFree / sizeAll) * 100;
		return (float) ratio;
	}

	public static  void main(String[] args) throws InterruptedException{
		MemoryRatioStatistics aMemoryRatioStatistics = new MemoryRatioStatistics();
		for(int i = 0;i < 30;i++){
			System.out.println(aMemoryRatioStatistics.getStatistics());
			Thread.sleep(500);
		}
	}
}
