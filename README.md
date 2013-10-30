Performance-Logger
==================

Description
===========
A simple tool to log your Java application basic performance using log4j. This tool is a supplement for other GUI oriented tools such as Jconsole and VirtualVM that are not oriented for long term profiling. 
This project is aimed for :
Java developers who want to track their application’s performance over time. This is very useful in benchmark environments and system profiling.       
Java developers that are not very familiar with Java performance tools and APIs.

Overview
========

Performance logger collects metrics from 3 samplers. 

**CPU Sampler** denoted the percentage of the CPU usage at sampling time. Values range from 0 to 100 for all the system processors combined.   

**Threads Sampler** denotes the total number of the live threads in the JVM, how many of them are daemon threads, and the peak, the highest number of live threads recorded at once since the JVM started.  

**Memory Sampler** denotes the memory usage of the JVM. I tried to keep it simple so only Heap size and Non-Heap (permanent generation)  size are sampled. 

GC information will be added in the future, upon request.   

Usage
=====

IMPORTANT: 
----------

This tool uses log4j. You MUST configure the log4j according to your needs or this tool will be useless. Do this with respect for disc space, sampling interval and log4j *MaxBackupIndex* property.
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

Output
======

Example of the log output: 

<pre><code>
2013-10-31 01:10:08,806 (PerformanceSampler.java) INFO  -  HEAP SIZE - 25776 KB  NON-HEAP SIZE - 6329 KB  CPU% - 81 THREAD COUNT - 39 DEAMON COUNT - 4 THREADS PEAK - 39
2013-10-31 01:10:09,807 (PerformanceSampler.java) INFO  -  HEAP SIZE - 23293 KB  NON-HEAP SIZE - 6329 KB  CPU% - 81 THREAD COUNT - 38 DEAMON COUNT - 4 THREADS PEAK - 39
2013-10-31 01:10:10,808 (PerformanceSampler.java) INFO  -  HEAP SIZE - 38389 KB  NON-HEAP SIZE - 6329 KB  CPU% - 80 THREAD COUNT - 39 DEAMON COUNT - 4 THREADS PEAK - 39
2013-10-31 01:10:11,828 (PerformanceSampler.java) INFO  -  HEAP SIZE - 34626 KB  NON-HEAP SIZE - 6329 KB  CPU% - 81 THREAD COUNT - 39 DEAMON COUNT - 4 THREADS PEAK - 39
2013-10-31 01:10:12,879 (PerformanceSampler.java) INFO  -  HEAP SIZE - 41493 KB  NON-HEAP SIZE - 6329 KB  CPU% - 79 THREAD COUNT - 40 DEAMON COUNT - 4 THREADS PEAK - 40
2013-10-31 01:10:13,880 (PerformanceSampler.java) INFO  -  HEAP SIZE - 52551 KB  NON-HEAP SIZE - 6329 KB  CPU% - 81 THREAD COUNT - 40 DEAMON COUNT - 4 THREADS PEAK - 40
2013-10-31 01:10:14,880 (PerformanceSampler.java) INFO  -  HEAP SIZE - 61869 KB  NON-HEAP SIZE - 6329 KB  CPU% - 81 THREAD COUNT - 40 DEAMON COUNT - 4 THREADS PEAK - 41
2013-10-31 01:10:15,881 (PerformanceSampler.java) INFO  -  HEAP SIZE - 14968 KB  NON-HEAP SIZE - 6329 KB  CPU% - 82 THREAD COUNT - 40 DEAMON COUNT - 4 THREADS PEAK - 41
2013-10-31 01:10:16,882 (PerformanceSampler.java) INFO  -  HEAP SIZE - 25165 KB  NON-HEAP SIZE - 6329 KB  CPU% - 79 THREAD COUNT - 41 DEAMON COUNT - 4 THREADS PEAK - 41
2013-10-31 01:10:17,883 (PerformanceSampler.java) INFO  -  HEAP SIZE - 38925 KB  NON-HEAP SIZE - 6329 KB  CPU% - 82 THREAD COUNT - 42 DEAMON COUNT - 4 THREADS PEAK - 42
2013-10-31 01:10:18,884 (PerformanceSampler.java) INFO  -  HEAP SIZE - 41015 KB  NON-HEAP SIZE - 6329 KB  CPU% - 82 THREAD COUNT - 41 DEAMON COUNT - 4 THREADS PEAK - 43
2013-10-31 01:10:19,929 (PerformanceSampler.java) INFO  -  HEAP SIZE - 49720 KB  NON-HEAP SIZE - 6329 KB  CPU% - 82 THREAD COUNT - 41 DEAMON COUNT - 4 THREADS PEAK - 43
2013-10-31 01:10:20,930 (PerformanceSampler.java) INFO  -  HEAP SIZE - 55396 KB  NON-HEAP SIZE - 6329 KB  CPU% - 80 THREAD COUNT - 41 DEAMON COUNT - 4 THREADS PEAK - 43
</code></pre>

Legal
=====
This project is distributed under my *STL license (Spread The Love)*.
Meaning it’s free, do whatever you want with it. 


