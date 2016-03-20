package xof.timeSeriesGenerator.enums;

public enum DataType {
	CPU{
		@Override
		public String toString(){
			return "CpuStatistics";
		}
	},
	MEMORY_SIZE{
		@Override
		public String toString(){
			return "MemorySizeStatistics";
		}
	},
	MEMORY_RATIO{
		@Override
		public String toString(){
			return "MemoryRatioStatistics";
		}
	},
//	NETWORK_RATIO{
//		@Override
//		public String toString(){
//			return "NetworkStatistics";
//		}
//	};
}
