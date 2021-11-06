package com.sofka.tareas.domain.entity.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JobEvent {
    private String eventId;
    private String eventName;

    private String jobId;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private Boolean status;

}
