package com.project.jobexecutor.service;

import com.project.jobexecutor.jobscheduler.JobScheduler;
import com.project.jobexecutor.model.EventJob;
import com.project.jobexecutor.repository.EventJobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventJobService {

    @Autowired
    JobScheduler jobScheduler;

    @Autowired
    EventJobRepo eventJobRepo;

    public static Map<String, String> eventJobList = new HashMap<>();
    public static EventJob staticEventJob = new EventJob();
    static{
        eventJobList.put("birthday", "print_birthday.bat");
        eventJobList.put("greeting","print_greeting.bat");
        eventJobList.put("news","print_news.bat");

        staticEventJob.setJobName("Birthday");
        staticEventJob.setJobDetails("For Birthday Wises");
        staticEventJob.setId(20);
        staticEventJob.setJobEvent("birthday");
    }

    public void createEventJob(EventJob eventJob){
        if (eventJobList.containsKey(eventJob.getJobEvent().toLowerCase())){
            eventJobRepo.save(eventJob);
        } else {
            eventJob.setJobEvent(eventJobList.get("greeting"));
            eventJob.setJobName("Greeting");
            eventJob.setJobDetails("greeting purpose");
            eventJobRepo.save(eventJob);
        }
    }

    public void runEventJobById(long id){
        Optional<EventJob> eventJob = eventJobRepo.findById(id).or(() -> {
            eventJobRepo.save(staticEventJob);
            return Optional.ofNullable(staticEventJob);
        });
        System.out.println(eventJob.get().getJobEvent());
        String scriptPath = eventJobList.getOrDefault(eventJob.get().getJobEvent(), "print_birthday.bat");
        System.out.println(scriptPath);
        jobScheduler.startExecutionForEventJob(scriptPath, eventJob.get());
    }

    public void runEventJobByName(String eventName){
        Optional<EventJob> eventJob = eventJobRepo.findByJobEvent(eventName).or(() -> {
            eventJobRepo.save(staticEventJob);
            return Optional.ofNullable(staticEventJob);
        });
        System.out.println(eventJob.get().getJobEvent());
        String scriptPath = eventJobList.getOrDefault(eventJob.get().getJobEvent(), "print_birthday.bat");
        System.out.println(scriptPath);
        jobScheduler.startExecutionForEventJob(scriptPath, eventJob.get());
    }

    public Optional<EventJob> getEventById(long id){
        Optional<EventJob> eventJob = eventJobRepo.findById(id).or(() -> {
            eventJobRepo.save(staticEventJob);
            return Optional.ofNullable(staticEventJob);
        });
        return eventJob;
    }

    public List<EventJob> getAllEventById() {
        List<EventJob> list = eventJobRepo.findAll();
        return list;
    }

    public List<String> getAllEvents() {
        List<String> keysList = new ArrayList<>(eventJobList.keySet());
        return keysList;
    }

}
