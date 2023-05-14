package com.project.jobexecutor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TIME_JOB")
@Data
public class TimeJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "JOB_NAME")
    private String jobName;
    @Column(name = "JOB_DETAILS")
    private String jobDetails;
    @Column(name = "JOB_KEY")
    private String jobKey;
    @Column(name = "JOB_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") //const formattedDate = date.toISOString().slice(0, -1);
    private LocalDateTime jobTime;
    @Column(name = "JOB_EXEC_TIME")
    private LocalDateTime jobExecTime;
    @Column(name = "JOB_RECURR")
    private boolean jobRecurr;
    @Column(name = "JOB_EXEC_COUNT")
    private int jobExecCount;
    @Column(name = "JOB_CURRENT_STATUS")
    private String jobCurrentStatus;

}
