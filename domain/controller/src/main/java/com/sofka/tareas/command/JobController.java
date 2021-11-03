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
import com.sofka.tareas.domain.response.JobEventResponse;
import com.sofka.tareas.domain.response.JobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log
@RequiredArgsConstructor
public class JobController implements JobFactory, HeaderFactory, JobEventFactory {

    private final ConfigBuilder configBuilder;
    private final String uuid = UUID.randomUUID().toString();

    public Mono<JobEventResponse> processCreateJob(JobCanonical jobCanonical) {

        return createJob(jobCanonical)
                .flatMap(jobResponse -> saveJobEvent(JobEventCanonical.builder()
                            .eventId(UUID.randomUUID().toString())
                            .eventName(JobCreatedEvent.EVENT_NAME)
                            .jobId(jobResponse.getId())
                            .cronRegExp(jobResponse.getCronRegExp())
                            .email(jobResponse.getEmail())
                            .timeZone(jobResponse.getTimeZone())
                            .status(jobResponse.getStatus())
                            .url(jobResponse.getUrl())
                        .build()))
                .flatMap(jobEventResponse -> emitJobCreatedEvent(jobEventResponse.getJobEventCanonical(), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    public Mono<JobEventResponse> processUpdateJob(JobCanonical jobCanonical) {

        return updateJob(jobCanonical)
                .flatMap(jobResponse -> saveJobEvent(JobEventCanonical.builder()
                            .eventId(UUID.randomUUID().toString())
                            .eventName(JobUpdatedEvent.EVENT_NAME)
                            .jobId(jobResponse.getId())
                            .cronRegExp(jobResponse.getCronRegExp())
                            .email(jobResponse.getEmail())
                            .timeZone(jobResponse.getTimeZone())
                            .status(jobResponse.getStatus())
                            .url(jobResponse.getUrl())
                        .build()))
                .flatMap(jobEventResponse -> emitJobUpdatedEvent(jobEventResponse.getJobEventCanonical(), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    private Mono<JobCanonical> createJob(JobCanonical jobCanonical) {
        return configBuilder.getJobRepository()
                        .save(buildCreateJob(jobCanonical))
                .flatMap(job -> Mono.just(JobCanonical.builder()
                                    .id(job.getId())
                                    .url(job.getUrl())
                                    .cronRegExp(job.getCronRegExp())
                                    .email(job.getEmail())
                                    .timeZone(job.getTimeZone())
                                    .status(job.getStatus())
                                .build()));
    }

    private Mono<JobEventResponse> saveJobEvent(JobEventCanonical jobCanonical) {
        return configBuilder.getEventRepository()
                        .save(buildCreateJobEvent(jobCanonical))
                .flatMap(jobEvent -> Mono.just(JobEventResponse.builder()
                        .jobEventCanonical(JobEventCanonical.builder()
                                    .eventId(jobEvent.getEventId())
                                    .eventName(jobEvent.getEventName())
                                    .jobId(jobEvent.getJobId())
                                    .url(jobEvent.getUrl())
                                    .cronRegExp(jobEvent.getCronRegExp())
                                    .email(jobEvent.getEmail())
                                    .timeZone(jobEvent.getTimeZone())
                                    .status(jobEvent.getStatus())
                                .build())
                        .build()));
    }

    private Mono<JobCanonical> updateJob(JobCanonical jobCanonical) {
        return configBuilder.getJobRepository()
                        .save(buildUpdateJob(jobCanonical))
                .flatMap(job -> Mono.just(JobCanonical.builder()
                                .id(job.getId())
                                .url(job.getUrl())
                                .cronRegExp(job.getCronRegExp())
                                .email(job.getEmail())
                                .timeZone(job.getTimeZone())
                                .status(job.getStatus())
                                .build()));
    }

    private Mono<JobEventResponse> emitJobCreatedEvent(JobEventCanonical jobEvent, String uuid, String serviceName) {
        return Mono.just(new JobCreatedEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(JobEvent.builder()
                                    .eventId(jobEvent.getEventId())
                                    .eventName(jobEvent.getEventName())
                                    .jobId(jobEvent.getJobId())
                                    .cronRegExp(jobEvent.getCronRegExp())
                                    .email(jobEvent.getEmail())
                                    .status(jobEvent.getStatus())
                                    .timeZone(jobEvent.getTimeZone())
                                    .url(jobEvent.getUrl())
                                .build())
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobEventResponse.builder()
                        .jobEventCanonical(jobEvent)
                        .build());
    }

    private Mono<JobEventResponse> emitJobUpdatedEvent(JobEventCanonical jobEvent, String uuid, String serviceName) {
        return Mono.just(new JobUpdatedEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(JobEvent.builder()
                                    .eventId(jobEvent.getEventId())
                                    .eventName(jobEvent.getEventName())
                                    .jobId(jobEvent.getJobId())
                                    .cronRegExp(jobEvent.getCronRegExp())
                                    .email(jobEvent.getEmail())
                                    .status(jobEvent.getStatus())
                                    .timeZone(jobEvent.getTimeZone())
                                    .url(jobEvent.getUrl())
                                .build())
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobEventResponse.builder()
                        .jobEventCanonical(jobEvent)
                        .build());
    }

}
