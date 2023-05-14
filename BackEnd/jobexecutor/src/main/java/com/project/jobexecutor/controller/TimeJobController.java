package com.project.jobexecutor.controller;

import com.project.jobexecutor.model.EventJob;
import com.project.jobexecutor.model.TimeJob;
import com.project.jobexecutor.model.User;
import com.project.jobexecutor.service.TimeJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timejobexecutor/v1")
public class TimeJobController {

    @Autowired
    TimeJobService timeJobService;

    @PostMapping(value = "/createtimejob")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createTimeJob(@RequestBody TimeJob timeJob) {
        timeJobService.createTimeJob(timeJob);
        return new ResponseEntity<>("Created Job Successfully for the JobName : "+timeJob.getJobName(), HttpStatus.OK);
    }

    @PutMapping(value = "/updatetimejob")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> updateTimeJob(@RequestBody TimeJob timeJob) {
        timeJobService.updateTimeJob(timeJob);
        return new ResponseEntity<>("Updated Job Successfully for the JobName : "+timeJob.getJobName(), HttpStatus.OK);
    }

    @GetMapping(value = "/runtimejob/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> updateTimeJob(@PathVariable long id) {
        timeJobService.runTimeJobById(id);
        return new ResponseEntity<>("Time Job Started Executing", HttpStatus.OK);
    }

    @GetMapping(value = "/stoptimejob/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> stopTimeJob(@PathVariable long id) {
        timeJobService.stopTimeJobById(id);
        return new ResponseEntity<>("Time Job Stopped", HttpStatus.OK);
    }

    @GetMapping(value = "/getalltimejob")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TimeJob>> getAllTimeJob() {
        return new ResponseEntity<>(timeJobService.getAllTimeJob(), HttpStatus.OK);
    }

    @GetMapping(value = "/gettimejob/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TimeJob> getTimeJob(@PathVariable long id) {
        Optional<TimeJob> eventJob = timeJobService.getTimeJobById(id);
        return new ResponseEntity<>(eventJob.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/getalltimejobskey")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> getAllTimeJobsKey() {
        return new ResponseEntity<>(timeJobService.getAllTimeJobsKey(), HttpStatus.OK);
    }
}
