package com.sofka.tareas.query;

import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.domain.entity.event.JobEventFactory;
import com.sofka.tareas.domain.entity.job.Job;
import com.sofka.tareas.domain.entity.job.JobFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Log
@RequiredArgsConstructor
public class JobQueryController implements JobFactory, HeaderFactory, JobEventFactory {

    private final ConfigBuilder configBuilder;

    public Mono<Job> processFindJobById(String id) {

        return findJobById(id);

    }

    public Flux<Job> processFindAllJobs() {

        return findAllJobs();

    }

    private Mono<Job> findJobById(String id) {
        return configBuilder.getJobRepository()
                        .findById(id);
    }

    private Flux<Job> findAllJobs() {
        return configBuilder.getJobRepository().findAllJobs();
     }

}
