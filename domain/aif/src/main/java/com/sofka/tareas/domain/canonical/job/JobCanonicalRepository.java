package com.sofka.tareas.domain.canonical.job;


import com.sofka.tareas.domain.entity.job.Job;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface JobCanonicalRepository {

    Mono<Job> save(Job data);
    Mono<Job> findById(String id);
    Flux<Job> findAllJobs();

}
