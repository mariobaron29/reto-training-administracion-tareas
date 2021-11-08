package com.sofka.tareas.usecase;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.common.event.JobUpdatedEvent;
import com.sofka.tareas.common.event.notification.CanonicalNotification;
import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.domain.canonical.event.JobEventCanonical;
import com.sofka.tareas.domain.canonical.job.JobCanonical;
import com.sofka.tareas.domain.entity.event.JobEvent;
import com.sofka.tareas.domain.entity.event.JobEventFactory;
import com.sofka.tareas.domain.entity.job.Job;
import com.sofka.tareas.domain.entity.job.JobFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log
@RequiredArgsConstructor
public class JobController implements JobFactory, JobEventFactory {

    private final ConfigBuilder configBuilder;
    private final String uuid = UUID.randomUUID().toString();

    public Mono<Job> processCreateJob(JobCanonical jobCanonical) {
    Job job = buildCreateJob(jobCanonical);
        return job.registerJobCreatedEvent(buildCreateJobEvent(job));
    }

    private Mono<JobEventCanonical> emitJobCreatedEvent(JobEventCanonical jobEventCanonical) {
        return Mono.just(new JobCreatedEvent(CanonicalNotification.<JobEvent>builder()
                        .data(buildJobEvent(jobEventCanonical))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(jobEventCanonical);
    }


    /*public Mono<JobEventCanonical> processUpdateJob(JobCanonical jobCanonical) {

        return updateJob(jobCanonical)
                .flatMap(jobResponse -> saveJobEvent(buildJobEventCanonical(jobCanonical, UUID.randomUUID().toString(), JobUpdatedEvent.EVENT_NAME )))
                .flatMap(jobEvent -> emitJobUpdatedEvent(buildJobEventCanonical(jobEvent), uuid, configBuilder.getConfigParameters().getServiceName()));
    }*/



    private Mono<JobEventCanonical> emitJobUpdatedEvent(JobEventCanonical jobEventCanonical, String uuid, String serviceName) {
        return Mono.just(new JobUpdatedEvent(CanonicalNotification.<JobEvent>builder()
                            .data(buildJobEvent(jobEventCanonical))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(jobEventCanonical);
    }

}
