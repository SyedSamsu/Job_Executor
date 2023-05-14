package com.project.jobexecutor.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "EVENT_JOB")
@Data
public class EventJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "JOB_NAME")
    private String jobName;
    @Column(name = "JOB_DETAILS")
    private String jobDetails;
    @Column(name = "JOB_EVENT")
    private String jobEvent;
    @Column(name = "JOB_EXEC_COUNT")
    private int jobExecCount;
    @Column(name = "JOB_CURRENT_STATUS")
    private String jobCurrentStatus;
}
