/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Map;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Admin
 */
public class JobInstance {
    
    private Scheduler scheduler;
    private JobKey jobKey;
    private TriggerKey triggerKey;

    public JobInstance() {
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public JobKey getJobKey() {
        return jobKey;
    }

    public void setJobKey(JobKey jobKey) {
        this.jobKey = jobKey;
    }

    public TriggerKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(TriggerKey triggerKey) {
        this.triggerKey = triggerKey;
    }

    
    
    

    public static <T extends Job> JobInstance create(Class<T> jobClass, Map<String, Object> jobDataMap, String jobIdentity, String triggerIdentity, int delayInSeconds) throws SchedulerException {
        JobInstance ji = new JobInstance();
        JobDataMap jdm = new JobDataMap();
        
        for (Map.Entry<String, Object> entry : jobDataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jdm.put(key, value);
            
        }
        
        ji.setJobKey(new JobKey(jobIdentity, "group1"));
        
         // Create a JobDetail for the given Job class
        JobDetail job = JobBuilder.newJob(jobClass)
                .withIdentity(jobIdentity, "group1")
                .usingJobData(jdm)
                .build();

        ji.setTriggerKey(new TriggerKey(triggerIdentity, "group1"));
        // Create a Trigger that fires after the specified delay
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerIdentity, "group1")
                .startAt(DateBuilder.futureDate(delayInSeconds, IntervalUnit.SECOND))
                .build();

        // Schedule the job using the trigger
        Scheduler scheduler = SchedulerSingleton.getInstance();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        
        
        ji.setScheduler(scheduler);
        return ji;
    }
    
    public static void kill(JobKey jk, TriggerKey tk) throws SchedulerException {
        Scheduler scheduler = SchedulerSingleton.getInstance();
        scheduler.unscheduleJob(tk);
        scheduler.deleteJob(jk);
    }
    
    public static void execute(JobKey jk) throws SchedulerException {
        Scheduler scheduler = SchedulerSingleton.getInstance();
        scheduler.triggerJob(jk);
    }
    
    public void kill() throws SchedulerException {
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
    }
}
