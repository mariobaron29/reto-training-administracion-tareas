package com.sofka.tareas.usecase;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.common.event.notification.CanonicalNotification;
import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.domain.entity.event.JobEvent;
import com.sofka.tareas.domain.entity.event.JobEventFactory;
import com.sofka.tareas.domain.entity.job.Job;
import com.sofka.tareas.domain.entity.job.JobFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class CreateJobUseCase implements JobFactory, JobEventFactory {

    private final ConfigBuilder configBuilder;

    @Async
    @EventListener
    public void handleJobCreatedEvent(JobCreatedEvent jobCreatedEvent) {
        saveJob(buildJob(jobCreatedEvent))
                .flatMap(job -> saveJobEvent(buildCreateJobEvent(job)))
                .flatMap(jobEvent -> emitJobCreatedEvent(jobEvent));
    }

    public Mono<Job> saveJob(Job job) {
        return configBuilder.getJobRepository()
                .save(job);
    }

    private Mono<JobEvent> saveJobEvent(JobEvent jobEvent) {
        return configBuilder.getEventRepository()
                        .save(jobEvent);
    }

    private Mono<JobEvent> emitJobCreatedEvent(JobEvent jobEvent) {
        return Mono.just(new JobCreatedEvent(CanonicalNotification.<JobEvent>builder()
                            .data(jobEvent)
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(jobEvent);
    }

}
