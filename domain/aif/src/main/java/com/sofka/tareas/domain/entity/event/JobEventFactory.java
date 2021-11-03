package com.sofka.tareas.domain.entity.event;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.domain.canonical.event.JobEventCanonical;

public interface JobEventFactory {

    default JobEvent buildJobEvent(JobEventCanonical canonical) {
        return JobEvent.builder()
                    .eventId(canonical.getEventId())
                    .eventName(canonical.getEventName())
                    .jobId(canonical.getJobId())
                    .cronRegExp(canonical.getCronRegExp())
                    .email(canonical.getEmail())
                    .status(canonical.getStatus())
                    .timeZone(canonical.getTimeZone())
                    .url(canonical.getUrl())
                    .status(canonical.getStatus())
                .build();
    }

    default JobEvent buildCreateJobEvent(JobEventCanonical canonical) {
        return JobEvent.builder()
                    .eventId(canonical.getEventId())
                    .eventName(canonical.getEventName())
                    .jobId(canonical.getJobId())
                    .cronRegExp(canonical.getCronRegExp())
                    .email(canonical.getEmail())
                    .status(canonical.getStatus())
                    .timeZone(canonical.getTimeZone())
                    .url(canonical.getUrl())
                    .status(JobCreatedEvent.EVENT_NAME)
                .build();
    }

    default JobEvent buildUpdateJobEvent(JobEventCanonical canonical) {
        return JobEvent.builder()
                    .eventId(canonical.getJobId())
                    .cronRegExp(canonical.getCronRegExp())
                    .email(canonical.getEmail())
                    .status(canonical.getStatus())
                    .timeZone(canonical.getTimeZone())
                    .url(canonical.getUrl())
                    .status(canonical.getStatus())
                .build();
    }
}
