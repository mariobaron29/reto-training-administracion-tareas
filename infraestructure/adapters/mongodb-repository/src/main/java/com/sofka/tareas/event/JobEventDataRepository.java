package com.sofka.tareas.event;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface JobEventDataRepository extends ReactiveCrudRepository<JobEventData, String>,
        ReactiveQueryByExampleExecutor<JobEventData> {


}
