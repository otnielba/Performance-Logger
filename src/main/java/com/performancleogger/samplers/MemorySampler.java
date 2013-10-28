package com.performancleogger.samplers;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import com.performancleogger.conf.PerformanceLoggerConfiguration;


class MemorySampler implements Sampler{

	private static final String HEAP_LOG_MESSAGE = " HEAP SIZE - ";
	private static final String NON_HEAP_LOG_MESSAGE = " NON-HEAP SIZE - ";
	
	private long heapUsedMemorySize;
	private long nonHeapUsedMemorySize;
	private final int dataGranulrarityFactor;
	private final String dataGranulrarity;
	
	public MemorySampler(PerformanceLoggerConfiguration.DataGranulrarity dataGranulrarity){
		this.dataGranulrarityFactor = dataGranulrarity.getBytesFactor();
		this.dataGranulrarity = " " + dataGranulrarity.toString() + " ";
	}
	
	public void sample(StringBuilder sb){	
		
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
		heapUsedMemorySize = heapMemoryUsage.getUsed();
		nonHeapUsedMemorySize = nonHeapMemoryUsage.getUsed();
		
		sb.append(HEAP_LOG_MESSAGE);
		sb.append(heapUsedMemorySize / dataGranulrarityFactor);
		sb.append(dataGranulrarity);
		sb.append(NON_HEAP_LOG_MESSAGE);
		sb.append(nonHeapUsedMemorySize / dataGranulrarityFactor);
		sb.append(dataGranulrarity);
	}
}
