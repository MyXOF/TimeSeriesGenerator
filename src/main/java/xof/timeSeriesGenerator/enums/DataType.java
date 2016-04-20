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
	DISK_FREE{
		@Override
		public String toString(){
			return "DiskFreeSize";
		}
	},
	SWITCH_VALUES{
		@Override
		public String toString(){
			return "SwitchValues";
		}		
	},
	ALL{
		@Override
		public String toString(){
			return "All";
		}
	};
}
