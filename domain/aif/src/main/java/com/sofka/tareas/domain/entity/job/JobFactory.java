package com.sofka.tareas.domain.entity.job;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.domain.canonical.event.JobEventCanonical;
import com.sofka.tareas.domain.canonical.job.JobCanonical;
import com.sofka.tareas.domain.entity.event.JobEvent;

import java.util.UUID;

public interface JobFactory {

    default JobEventCanonical buildJobEventCanonical(JobCanonical jobCanonical, String eventId, String eventName) {
        return JobEventCanonical.builder()
                    .eventId(eventId)
                    .eventName(eventName)
                    .jobId(jobCanonical.getId())
                    .cronRegExp(jobCanonical.getCronRegExp())
                    .email(jobCanonical.getEmail())
                    .timeZone(jobCanonical.getTimeZone())
                    .status(jobCanonical.getStatus())
                    .url(jobCanonical.getUrl())
                .build();
    }

    default JobEventCanonical buildJobEventCanonical(JobEvent jobEvent) {
        return JobEventCanonical.builder()
                    .eventId(jobEvent.getEventId())
                    .eventName(jobEvent.getEventName())
                    .jobId(jobEvent.getJobId())
                    .cronRegExp(jobEvent.getCronRegExp())
                    .email(jobEvent.getEmail())
                    .timeZone(jobEvent.getTimeZone())
                    .status(jobEvent.getStatus())
                    .url(jobEvent.getUrl())
                .build();
    }

    default Job buildCreateJob(JobCanonical canonical) {
        return Job.builder()
                    .id(UUID.randomUUID().toString())
                    .cronRegExp(canonical.getCronRegExp())
                    .email(canonical.getEmail())
                    .status(canonical.getStatus())
                    .timeZone(canonical.getTimeZone())
                    .url(canonical.getUrl())
                    .status(true)
                .build();
    }

    default Job buildJob(JobCreatedEvent jobCreatedEvent) {
        JobEvent event = (JobEvent) jobCreatedEvent.getData();

        return Job.builder()
                    .id(event.getJobId())
                    .cronRegExp(event.getCronRegExp())
                    .email(event.getEmail())
                    .status(event.getStatus())
                    .timeZone(event.getTimeZone())
                    .url(event.getUrl())
                    .status(true)
                .build();
    }

    default Job buildJob(JobEvent event) {

        return Job.builder()
                    .id(event.getJobId())
                    .cronRegExp(event.getCronRegExp())
                    .email(event.getEmail())
                    .status(event.getStatus())
                    .timeZone(event.getTimeZone())
                    .url(event.getUrl())
                    .status(true)
                .build();
    }

    default Job buildUpdateJob(JobCanonical canonical) {
        return Job.builder()
                    .id(canonical.getId())
                    .cronRegExp(canonical.getCronRegExp())
                    .email(canonical.getEmail())
                    .status(canonical.getStatus())
                    .timeZone(canonical.getTimeZone())
                    .url(canonical.getUrl())
                .build();
    }
}
