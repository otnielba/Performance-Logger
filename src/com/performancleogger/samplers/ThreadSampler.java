package com.performancleogger.samplers;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class ThreadSampler implements Sampler{

	private static final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	
	private static final String COUNT_LOG_MESSAGE = " THREAD COUNT - ";
	private static final String DAEMON_LOG_MESSAGE = " DEAMON COUNT - ";
	private static final String PEAK_LOG_MESSAGE = " THREADS PEAK - ";
	
	public void sample(StringBuilder sb){
		int threadCount = threadMXBean.getThreadCount();
		int daemonThreadCount = threadMXBean.getDaemonThreadCount();
		int peakThreadCount = threadMXBean.getPeakThreadCount();
		
		sb.append(COUNT_LOG_MESSAGE);
		sb.append(threadCount);
		sb.append(DAEMON_LOG_MESSAGE);
		sb.append(daemonThreadCount);
		sb.append(PEAK_LOG_MESSAGE);
		sb.append(peakThreadCount);
	}
}
