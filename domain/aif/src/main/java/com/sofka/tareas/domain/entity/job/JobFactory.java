package com.sofka.tareas.domain.entity.job;

import com.sofka.tareas.domain.canonical.job.JobCanonical;

import java.util.UUID;

public interface JobFactory {

    default Job buildJob(JobCanonical canonical) {
        return Job.builder()
                    .id(canonical.getId())
                    .cronRegExp(canonical.getCronRegExp())
                    .email(canonical.getEmail())
                    .status(canonical.getStatus())
                    .timeZone(canonical.getTimeZone())
                    .url(canonical.getUrl())
                .build();
    }

    default JobCanonical buildJobCanonical(Job job) {
        return JobCanonical.builder()
                    .id(job.getId())
                    .cronRegExp(job.getCronRegExp())
                    .email(job.getEmail())
                    .status(job.getStatus())
                    .timeZone(job.getTimeZone())
                    .url(job.getUrl())
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
