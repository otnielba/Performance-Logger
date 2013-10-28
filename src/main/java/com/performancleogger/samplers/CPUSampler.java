package com.performancleogger.samplers;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.sun.management.OperatingSystemMXBean;

class CPUSampler implements Sampler {

	private static final String LOG_MESSAGE = " CPU% - ";
	private static final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	private static final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	private static final int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
	
	private long prevUpTime = runtimeMXBean.getUptime();
	private long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
	

	public void sample(StringBuilder sb){
		long upTime = runtimeMXBean.getUptime();
		long processCpuTime = operatingSystemMXBean.getProcessCpuTime();
		long elapsedCpu = processCpuTime - prevProcessCpuTime;
		long elapsedTime = upTime - prevUpTime;
		
		int cpuUsage = (int) Math.min(99F, elapsedCpu / (elapsedTime * 10000F * availableProcessors));
		prevUpTime = upTime;
		prevProcessCpuTime = processCpuTime;
		
		sb.append(LOG_MESSAGE);
		sb.append(cpuUsage);
	}

}
