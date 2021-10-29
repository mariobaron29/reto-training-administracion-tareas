package com.sofka.tareas;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.common.event.JobUpdatedEvent;
import com.sofka.tareas.common.event.notification.CanonicalNotification;
import com.sofka.tareas.common.event.notification.HeaderFactory;
import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.domain.Job;
import com.sofka.tareas.domain.JobFactory;
import com.sofka.tareas.domain.canonical.JobCanonical;
import com.sofka.tareas.domain.response.JobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log
@RequiredArgsConstructor
public class JobController implements JobFactory, HeaderFactory {

    private final ConfigBuilder configBuilder;
    private final String uuid = UUID.randomUUID().toString();

    public Mono<JobResponse> processCreateJob(JobCanonical jobCanonical) {

        return createJob(jobCanonical)
                .flatMap(jobResponse -> emitJobCreatedEvent(jobCanonical, uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    public Mono<JobResponse> processUpdateJob(JobCanonical jobCanonical) {

        return createJob(jobCanonical)
                .flatMap(jobResponse -> emitJobUpdatedEvent(jobCanonical, uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    private Mono<JobResponse> createJob(JobCanonical jobCanonical) {
        return configBuilder.getJobRepository()
                        .save(buildJob(jobCanonical))
                .then(Mono.just(JobResponse.builder()
                        .jobCanonical(jobCanonical)
                        .build()));
    }

    private Mono<JobResponse> updateJob(JobCanonical jobCanonical) {
        return configBuilder.getJobRepository()
                        .save(buildJob(jobCanonical))
                .then(Mono.just(JobResponse.builder()
                        .jobCanonical(jobCanonical)
                        .build()));
    }

    private Mono<JobResponse> emitJobCreatedEvent(JobCanonical job, String uuid, String serviceName) {
        return Mono.just(new JobCreatedEvent(CanonicalNotification.<Job>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(Job.builder()
                                    .id(job.getId())
                                    .cronRegExp(job.getCronRegExp())
                                    .email(job.getEmail())
                                    .status(job.getStatus())
                                    .timeZone(job.getTimeZone())
                                    .url(job.getUrl())
                                .build())
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobResponse.builder()
                        .jobCanonical(job)
                        .build());
    }

    private Mono<JobResponse> emitJobUpdatedEvent(JobCanonical job, String uuid, String serviceName) {
        return Mono.just(new JobUpdatedEvent(CanonicalNotification.<Job>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(Job.builder()
                                .id(job.getId())
                                .cronRegExp(job.getCronRegExp())
                                .email(job.getEmail())
                                .status(job.getStatus())
                                .timeZone(job.getTimeZone())
                                .url(job.getUrl())
                                .build())
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobResponse.builder()
                        .jobCanonical(job)
                        .build());
    }

}
