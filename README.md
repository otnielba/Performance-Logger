Performance-Logger
==================

Description
------------
A simple tool to log your Java application basic performance using log4j. This tool is a supplement for other GUI oriented tools such as Jconsole and VirtualVM that are not oriented for long term profiling. 
This project is aimed for :
Java developers who want to track their application’s performance over time. This is very useful in benchmark environments and system profiling.       
Java developers that are not very familiar with Java performance tools and APIs.

Overview
------------

Performance logger collects metrics from 3 samplers. 

**CPU Sampler** denoted the percentage of the CPU usage at sampling time. Values range from 0 to 100 for all the system processors combined.   

**Threads Sampler** denotes the total number of the live threads in the JVM, how many of them are daemon threads, and the peak, the highest number of live threads recorded at once since the JVM started.  

**Memory Sampler** denotes the memory usage of the JVM. I tried to keep it simple so only Heap size and Non-Heap (permanent generation)  size are sampled. 

GC information will be added in the future, upon request.   

Usage
=====

IMPORTANT: 
----------

This tool uses log4j. You MUST configure the log4j according to your needs or this tool will be useless. Do this with respect for disc space, sampling interval and log4j MaxBackupIndex property.
For example, with the default sampling interval of 1 second, a 24 hour log size will be about 16MB.
It is highly recommended to add to your log4j configuration a separate appender for the Performance Logger in order to keep your other logs nice and clean. 


Example: 
-------

<pre><code>
log4j.logger.com.performancleogger=INFO, data
log4j.additivity.com.performancleogger=false

log4j.appender.data=org.apache.log4j.RollingFileAppender
log4j.appender.data.File=performancleogger.log
log4j.appender.data.Threshold=INFO
log4j.appender.data.MaxFileSize=2MB
log4j.appender.data.MaxBackupIndex=200
log4j.appender.data.layout=org.apache.log4j.PatternLayout
log4j.appender.data.layout.ConversionPattern=%d (%F) %-5p - %m%n
 </code></pre>



Basic usage: 
------
<pre><code>
PerformanceSampler performanceSampler = PerformanceSampler.getPerformanceSampler();
performanceSampler.logPerformanceMetrics();

// do your stuff here 
		
// gracefully shutdown, if needed  
performanceSampler.shutdown(); 
</code></pre>


Custom usage:
--------------------

<pre><code>
PerformanceSampler performanceSampler = PerformanceSampler.getPerformanceSampler();

PerformanceLoggerConfiguration configuration = new PerformanceLoggerConfiguration();
configuration.setMemorySamplerOn(true);// default is on == true;
configuration.setThreadsSamplerOn(true);// default is on == true;
configuration.setCpuSamplerOn(true);// default is on == true;
configuration.setDataMetricGranularity(DataGranulrarity.MB); // log memory as MB/KB/Bytes. default is KB
configuration.setSampleInterval(2000);// in milliseconds. Default is 1 second
performanceSampler.setConfiguration(configuration);  

performanceSampler.logPerformanceMetrics();

// do your stuff here 

// gracefully shutdown, if needed  
performanceSampler.shutdown(); 

</code></pre>

Legal
------------
This project is distributed under my STL license (Spread The Love).
Meaning  It’s free, do whatever you want with it. 


