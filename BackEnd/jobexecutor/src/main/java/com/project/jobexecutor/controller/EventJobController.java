package com.project.jobexecutor.controller;

import com.project.jobexecutor.jobscheduler.JobScheduler;
import com.project.jobexecutor.model.EventJob;
import com.project.jobexecutor.model.TimeJob;
import com.project.jobexecutor.service.EventJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventjobexecutor/v1")
public class EventJobController {

    @Autowired
    EventJobService eventJobService;

    @PostMapping(value = "/createeventjob")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createEventJob(@RequestBody EventJob eventJob) {
        eventJobService.createEventJob(eventJob);
        return new ResponseEntity<>("Created Job Successfully for the EventName "+eventJob.getJobName(), HttpStatus.OK);
    }

    @PutMapping(value = "/runeventjob/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> runEventJobById(@PathVariable long id) {
        eventJobService.runEventJobById(id);
        return new ResponseEntity<>("Event Job Executed Successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/runeventjobbyname/{jobName}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> runEventJobByName(@PathVariable String jobName) {
        eventJobService.runEventJobByName(jobName);
        return new ResponseEntity<>("Event Job Executed Successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/getalleventjob")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<EventJob>> getAllEventJob() {
        return new ResponseEntity<>(eventJobService.getAllEventById(), HttpStatus.OK);
    }

    @GetMapping(value = "/getallevents")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> getAllEvents() {
        return new ResponseEntity<>(eventJobService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping(value = "/geteventjob/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<EventJob> getEventJob(@PathVariable long id) {
        Optional<EventJob> eventJob = eventJobService.getEventById(id);
        return new ResponseEntity<>(eventJob.get(), HttpStatus.OK);
    }



}
