package xof.timeSeriesGenerator.statistics;

public class SwitchValuesStatistics extends Statistics<String> {

	@Override
	public String getStatistics() {
		float cpuRatio = (float) osmb.getSystemCpuLoad();
		int num = (int)((1 - cpuRatio) * 100);
		if(num % 2 == 0) return "true";
		return "false";
	}

	public static void main(String[] args) throws InterruptedException{
		SwitchValuesStatistics a = new SwitchValuesStatistics();
		for(int i = 0;i < 30;i++){
			System.out.println(a.getStatistics());
			Thread.sleep(500);
		}
	}
}
