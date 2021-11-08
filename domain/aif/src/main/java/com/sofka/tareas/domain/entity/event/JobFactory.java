package com.sofka.tareas.domain.entity.event;

import com.sofka.tareas.domain.canonical.event.JobEventCanonical;

public interface JobFactory {

    default JobEventCanonical buildJobEventCanonical(JobEvent event) {
        return JobEventCanonical.builder()
                    .eventId(event.getEventId())
                    .eventName(event.getEventName())
                    .jobId(event.getEventId())
                    .cronRegExp(event.getCronRegExp())
                    .email(event.getEmail())
                    .status(event.getStatus())
                    .timeZone(event.getTimeZone())
                    .url(event.getUrl())
                .build();
    }

    default JobEvent buildJobEvent(JobEventCanonical event) {
        return JobEvent.builder()
                    .eventId(event.getEventId())
                    .eventName(event.getEventName())
                    .jobId(event.getEventId())
                    .cronRegExp(event.getCronRegExp())
                    .email(event.getEmail())
                    .status(event.getStatus())
                    .timeZone(event.getTimeZone())
                    .url(event.getUrl())
                    .status(event.getStatus())
                .build();
    }

}
