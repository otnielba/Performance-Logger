package com.performancleogger.conf;

public class PerformanceLoggerConfiguration {

	public enum DataGranulrarity{
		B(1),
		KB(1024),
		MB(1024*1024);

		private int bytesfactor;

		private DataGranulrarity(int bytesfactor) {
			this.bytesfactor = bytesfactor;
		}

		public int getBytesFactor(){
			return bytesfactor;
		}
		
		@Override
		public String toString(){
			switch (this) {
			case B:
				return "Bytes";
			default:
				return this.name();
			}
		}
	}
	
	private long sampleInterval = 1000;
	private boolean cpuSamplerOn = true;
	private boolean memorySamplerOn = true;
	private boolean threadsSamplerOn = true;
	private DataGranulrarity dataMetricGranularity = DataGranulrarity.KB;
	
	public long getSampleInterval() {
		return sampleInterval;
	}
	public void setSampleInterval(long sampleInterval) {
		this.sampleInterval = sampleInterval;
	}
	public boolean isCpuSamplerOn() {
		return cpuSamplerOn;
	}
	public void setCpuSamplerOn(boolean cpuSamplerOn) {
		this.cpuSamplerOn = cpuSamplerOn;
	}
	public boolean isMemorySamplerOn() {
		return memorySamplerOn;
	}
	public void setMemorySamplerOn(boolean memorySamplerOn) {
		this.memorySamplerOn = memorySamplerOn;
	}
	public boolean isThreadsSamplerOn() {
		return threadsSamplerOn;
	}
	public void setThreadsSamplerOn(boolean threadsSamplerOn) {
		this.threadsSamplerOn = threadsSamplerOn;
	}
	public DataGranulrarity getDataMetricGranularity() {
		return dataMetricGranularity;
	}
	public void setDataMetricGranularity(DataGranulrarity dataMetricGranularity) {
		this.dataMetricGranularity = dataMetricGranularity;
	}  

}
