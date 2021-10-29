package com.sofka.tareas.domain.canonical;


import com.sofka.tareas.domain.Job;
import reactor.core.publisher.Mono;

public interface JobCanonicalRepository {

    Mono<Job> save(Job data);
    Mono<Job> findById(String id);

}
