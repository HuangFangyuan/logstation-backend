package com.hfy.logstation.job;

import com.hfy.logstation.exception.ServerException;
import org.quartz.JobDetail;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.quartz.JobBuilder.newJob;

public class JobFactory {

    private static final String SIMPLE_MONITOR = "普通监控";
    private static final String COUNT_MONITOR = "次数监控";
    private static final String AVG_MONITOR = "平均值监控";

    private static final Map<String, Supplier<JobDetail>> JOB_SUPPLIER = new HashMap<>();

    static {
        JOB_SUPPLIER.put(SIMPLE_MONITOR, () -> newJob().ofType(SimpleMonitorJob.class).build());
        JOB_SUPPLIER.put(COUNT_MONITOR, () -> newJob().ofType(CountMonitorJob.class).build());
        JOB_SUPPLIER.put(AVG_MONITOR, () -> newJob().ofType(AvgMonitorJob.class).build());
    }

    public static JobDetail createJobDetail(String type) {
        Supplier<JobDetail> supplier = JOB_SUPPLIER.get(type);
        if (supplier != null)return supplier.get();
        else throw new ServerException("not support this type of monitor:" + type);
    }
}
