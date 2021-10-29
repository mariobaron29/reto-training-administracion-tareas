package com.sofka.tareas.domain;

import com.sofka.tareas.domain.canonical.JobCanonical;

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
}
