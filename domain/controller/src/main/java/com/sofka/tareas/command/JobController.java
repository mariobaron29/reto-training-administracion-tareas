package com.sofka.tareas.command;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.common.event.JobUpdatedEvent;
import com.sofka.tareas.common.event.notification.CanonicalNotification;
import com.sofka.tareas.common.event.notification.HeaderFactory;
import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.domain.canonical.event.JobEventCanonical;
import com.sofka.tareas.domain.canonical.job.JobCanonical;
import com.sofka.tareas.domain.entity.event.JobEvent;
import com.sofka.tareas.domain.entity.event.JobEventFactory;
import com.sofka.tareas.domain.entity.job.JobFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log
@RequiredArgsConstructor
public class JobController implements JobFactory, HeaderFactory, JobEventFactory {

    private final ConfigBuilder configBuilder;
    private final String uuid = UUID.randomUUID().toString();

    public Mono<JobEventCanonical> processCreateJob(JobCanonical jobCanonical) {

        return createJob(jobCanonical)
                .flatMap(jobResponse -> saveJobEvent(buildJobEventCanonical(jobCanonical, UUID.randomUUID().toString(), JobCreatedEvent.EVENT_NAME )))
                .flatMap(jobEvent -> emitJobCreatedEvent(buildJobEventCanonical(jobEvent), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    public Mono<JobEventCanonical> processUpdateJob(JobCanonical jobCanonical) {

        return updateJob(jobCanonical)
                .flatMap(jobResponse -> saveJobEvent(buildJobEventCanonical(jobCanonical, UUID.randomUUID().toString(), JobUpdatedEvent.EVENT_NAME )))
                .flatMap(jobEvent -> emitJobUpdatedEvent(buildJobEventCanonical(jobEvent), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    private Mono<JobCanonical> createJob(JobCanonical jobCanonical) {
        return configBuilder.getJobRepository()
                        .save(buildCreateJob(jobCanonical))
                .flatMap(job -> Mono.just(buildJobCanonical(job)));
    }

    private Mono<JobEvent> saveJobEvent(JobEventCanonical jobCanonical) {
        return configBuilder.getEventRepository()
                        .save(buildCreateJobEvent(jobCanonical));
    }

    private Mono<JobCanonical> updateJob(JobCanonical jobCanonical) {
        return configBuilder.getJobRepository()
                        .save(buildUpdateJob(jobCanonical))
                .flatMap(job -> Mono.just(buildJobCanonical(job)));
    }

    private Mono<JobEventCanonical> emitJobCreatedEvent(JobEventCanonical jobEvent, String uuid, String serviceName) {
        return Mono.just(new JobCreatedEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(buildJobEvent(jobEvent))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(jobEvent);
    }

    private Mono<JobEventCanonical> emitJobUpdatedEvent(JobEventCanonical jobEvent, String uuid, String serviceName) {
        return Mono.just(new JobUpdatedEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(buildJobEvent(jobEvent))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(jobEvent);
    }

}
