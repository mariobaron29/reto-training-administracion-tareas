package com.sofka.tareas.data;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface JobDataRepository extends ReactiveCrudRepository<JobData, String>,
        ReactiveQueryByExampleExecutor<JobData> {

}
