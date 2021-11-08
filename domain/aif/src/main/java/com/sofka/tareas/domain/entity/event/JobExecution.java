package com.sofka.tareas.domain.entity.event;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class JobExecution {
    private String id;
    private String jobId;
    private Date scheduledTime;
    private Date startTime;
    private Date endTime;
    private Long executionTime;
    private String httpCode;
    private Boolean status;
    private String output;

}
