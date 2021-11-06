package com.sofka.tareas.domain.entity.job;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Job {
    private String id;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private Boolean status;

}
