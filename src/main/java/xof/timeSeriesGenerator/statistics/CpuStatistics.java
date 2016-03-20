package xof.timeSeriesGenerator.statistics;

public class CpuStatistics extends Statistics<Float> {

	@Override
	public Float getStatistics() {
		float cpuRatio = (float) osmb.getSystemCpuLoad();
		return (1 - cpuRatio) * 100;
	}

	public static  void main(String[] args) throws InterruptedException{
		CpuStatistics a = new CpuStatistics();
		for(int i = 0;i < 30;i++){
			System.out.println(a.getStatistics());
			Thread.sleep(500);
		}
	}
}
