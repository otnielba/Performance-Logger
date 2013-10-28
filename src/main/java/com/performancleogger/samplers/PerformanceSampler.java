package com.performancleogger.samplers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.performancleogger.conf.PerformanceLoggerConfiguration;
import com.performancleogger.samplers.Sampler;

public class PerformanceSampler{

	private static PerformanceSampler INSTANCE;

	private static final Logger log = Logger.getLogger(PerformanceSampler.class);
	private final StringBuilder logMessage = new StringBuilder();	
	private final List<Sampler> samplers = new ArrayList<Sampler>();
	private PerformanceLoggerConfiguration conf = new PerformanceLoggerConfiguration();
	private final MetricCollector metricCollector = new MetricCollector();
	private volatile boolean shouldCollectinMetrics = true;

	private PerformanceSampler(){}

	public static PerformanceSampler getPerformanceSampler(){
		if(INSTANCE == null){
			synchronized (PerformanceSampler.class) {
				if(INSTANCE == null){
					INSTANCE = new PerformanceSampler();
				}				
			}
		}
		return INSTANCE;
	}

	public void setConfiguration(PerformanceLoggerConfiguration conf){
		this.conf = conf; 
	}
	
	public void logPerformanceMetrics(){
		this.metricCollector.setDaemon(true);
		this.metricCollector.start();
	}
	
	public void shutdown() {
		this.shouldCollectinMetrics = false;
	}

	private void init(){
		if(conf.isMemorySamplerOn()){
			samplers.add(new MemorySampler(conf.getDataMetricGranularity()));
		}
		if(conf.isCpuSamplerOn()){
			samplers.add(new CPUSampler());
		}
		if(conf.isThreadsSamplerOn()){
			samplers.add(new ThreadSampler());
		}
	}

	private void collectMetrics(){
		for(Sampler sampler : samplers ){
			sampler.sample(this.logMessage);
		}
		logMetrics();
		logMessage.setLength(0);
	}


	private void logMetrics() {
		log.info(logMessage);
	}

	
	private class MetricCollector extends Thread{
		@Override
		public void run(){
			init();
			long interval = conf.getSampleInterval();
			while(shouldCollectinMetrics){
				long startSampling = System.currentTimeMillis();
				collectMetrics();
				long samplingTook = System.currentTimeMillis() - startSampling;
				try {
					sleep(interval - samplingTook);
				} catch (InterruptedException e) {
					log.warn("Performance Logger thread was interrupted! shutting down...", e);
					shouldCollectinMetrics = false;
				}
			}
		}
	}
}
