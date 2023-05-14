package com.project.jobexecutor.service;

import com.project.jobexecutor.jobscheduler.JobScheduler;
import com.project.jobexecutor.model.EventJob;
import com.project.jobexecutor.model.TimeJob;
import com.project.jobexecutor.repository.TimeJobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TimeJobService {

    @Autowired
    JobScheduler jobScheduler;

    @Autowired
    TimeJobRepo timeJobRepo;

    public static Map<String, String> timeJobList = new HashMap<>();
    public static TimeJob staticTimeJob = new TimeJob();

    static{
        timeJobList.put("create_file", "create_file.bat");
        timeJobList.put("date","print_date.bat");
        timeJobList.put("sys_mry","print_system_memory.bat");
        timeJobList.put("time","print_time.bat");
        timeJobList.put("time10min","print_time_for_10_min.bat");

        staticTimeJob.setJobName("Date");
        staticTimeJob.setJobDetails("For Getting the data");
        staticTimeJob.setId(20);
        staticTimeJob.setJobTime(LocalDateTime.now());
        staticTimeJob.setJobKey("date");
    }

    public void createTimeJob(TimeJob timeJob){
        if (timeJobList.containsKey(timeJob.getJobKey().toLowerCase())){
            timeJobRepo.save(timeJob);
        } else {
            timeJob.setJobKey("date");
            timeJob.setJobName("date_now");
            timeJob.setJobDetails("printing date in file");
            timeJob.setJobRecurr(false);
            timeJob.setJobTime(LocalDateTime.now());
            timeJobRepo.save(timeJob);
        }
    }
    public void updateTimeJob(TimeJob timeJob){
        timeJobRepo.save(timeJob);
    }

    public void runTimeJobById(long id){
        Optional<TimeJob> timeJob = timeJobRepo.findById(id).or(() -> {
            timeJobRepo.save(staticTimeJob);
            return Optional.ofNullable(staticTimeJob);
        });
        System.out.println(timeJob.get().getJobKey());
        String scriptPath = timeJobList.getOrDefault(timeJob.get().getJobKey(), "print_date.bat");
        System.out.println(scriptPath);
        jobScheduler.startExecutionTimejob(scriptPath, timeJob.get());
    }

    public void stopTimeJobById(long id){
        Optional<TimeJob> timeJob = timeJobRepo.findById(id).or(() -> {
            timeJobRepo.save(staticTimeJob);
            return Optional.ofNullable(staticTimeJob);
        });
        jobScheduler.stopExecutionTimeJob(timeJob.get().getJobName(), timeJob.get());
    }

    public Optional<TimeJob> getTimeJobById(long id){
        Optional<TimeJob> timeJob = timeJobRepo.findById(id).or(() -> {
            timeJobRepo.save(staticTimeJob);
            return Optional.ofNullable(staticTimeJob);
        });
        return timeJob;
    }

    public List<TimeJob> getAllTimeJob() {
        List<TimeJob> list = timeJobRepo.findAll();
        return list;
    }

    public List<String> getAllTimeJobsKey(){
        List<String> keysList = new ArrayList<>(timeJobList.keySet());
        return keysList;
    }
}
