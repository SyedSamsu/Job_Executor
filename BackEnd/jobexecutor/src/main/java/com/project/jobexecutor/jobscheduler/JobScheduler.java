package com.project.jobexecutor.jobscheduler;

import com.project.jobexecutor.model.EventJob;
import com.project.jobexecutor.model.TimeJob;
import com.project.jobexecutor.repository.EventJobRepo;
import com.project.jobexecutor.repository.TimeJobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Component
public class JobScheduler {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    EventJobRepo eventJobRepo;

    @Autowired
    TimeJobRepo timeJobRepo;

    private Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    public void startExecutionTimejob(String scriptPath, TimeJob timeJob) {
        String cronExpression = convertDateTimeToCron(timeJob);
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(() -> {
            int count = timeJob.getJobExecCount();
            try {
                String path = "./src/main/resources/timejobs/"+scriptPath;
                Process process = Runtime.getRuntime().exec(path);
                timeJob.setJobExecCount(++count);
                int exitValue = process.waitFor();
                if(!timeJob.isJobRecurr()){
                    if(exitValue == 0 ){
                        process.destroy();
                        timeJob.setJobCurrentStatus("COMPLETED");
                        timeJob.setJobExecTime(LocalDateTime.now());
                        timeJobRepo.save(timeJob);
                        scheduledTasks.get(timeJob.getJobName()).cancel(true);
                        scheduledTasks.remove(timeJob.getJobName());
                    }
                } else {
                    timeJob.setJobCurrentStatus("RUNNING");
                    timeJob.setJobExecTime(LocalDateTime.now());
                    timeJobRepo.save(timeJob);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }, new CronTrigger(cronExpression));
        scheduledTasks.put(timeJob.getJobName(), scheduledFuture);
        timeJob.setJobCurrentStatus("SCHEDULED");
        timeJobRepo.save(timeJob);
    }

    public void stopExecutionTimeJob(String jobName, TimeJob timeJob) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(jobName);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledTasks.remove(jobName);
            timeJob.setJobCurrentStatus("STOPPED");
            timeJobRepo.save(timeJob);
        }
    }

    public void startExecutionForEventJob(String scriptPath, EventJob eventJob) {
        final int[] exitValue = new int[1];
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(() -> {
            int count = eventJob.getJobExecCount();
            try {
                String path = "./src/main/resources/eventjobs/"+scriptPath;
                Process process = Runtime.getRuntime().exec(path);
                eventJob.setJobCurrentStatus("RUNNING");
                eventJob.setJobExecCount(++count);
                eventJobRepo.save(eventJob);
                exitValue[0] = process.waitFor();
                if( exitValue[0] == 0 ){
                    process.destroy();
                    eventJob.setJobCurrentStatus("COMPLETED");;
                    eventJobRepo.save(eventJob);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }, new CronTrigger(convertDateToCron()));
    }

    public static String convertDateToCron(){
        LocalDateTime dateTime = LocalDateTime.now();
        String second = Integer.toString(dateTime.getSecond()+2);
        String minute = Integer.toString(dateTime.getMinute());
        String hour = Integer.toString(dateTime.getHour());
        String dayOfMonth = Integer.toString(dateTime.getDayOfMonth());
        String month = Integer.toString(dateTime.getMonthValue());
        String dayOfWeek = Integer.toString(dateTime.getDayOfWeek().getValue());
        String cronExpression = String.format("%s %s %s %s %s %s", second, minute, hour, dayOfMonth, month, dayOfWeek);
        System.out.println("Cron expression for current datetime: " + cronExpression);
        return cronExpression;
    }

    public static String convertDateTimeToCron(TimeJob timeJob){
        String cronExpression = "";
        LocalDateTime localDateTime = timeJob.getJobTime();
        String second = Integer.toString(localDateTime.getSecond()+10);
        String minute = Integer.toString(localDateTime.getMinute());
        String hour = Integer.toString(localDateTime.getHour());
        String dayOfMonth = Integer.toString(localDateTime.getDayOfMonth());
        String month = Integer.toString(localDateTime.getMonthValue());
        String dayOfWeek = Integer.toString(localDateTime.getDayOfWeek().getValue());
        if (timeJob.isJobRecurr()) {
            cronExpression = second+" "+ minute+" "+hour+" * * *";
        } else {
            cronExpression = String.format("%s %s %s %s %s %s", second, minute, hour, dayOfMonth, month, dayOfWeek);
        }
        System.out.println(cronExpression);
        return cronExpression;
    }
}
