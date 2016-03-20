package xof.timeSeriesGenerator.enums;

public enum DataType {
	CPU{
		@Override
		public String toString(){
			return "Cpu";
		}
	},
	MEMORY_SIZE{
		@Override
		public String toString(){
			return "MemorySize";
		}
	},
	MEMORY_RATIO{
		@Override
		public String toString(){
			return "MemoryRatio";
		}
	},
//	NETWORK_RATIO{
//		@Override
//		public String toString(){
//			return "NetworkStatistics";
//		}
//	};
}
