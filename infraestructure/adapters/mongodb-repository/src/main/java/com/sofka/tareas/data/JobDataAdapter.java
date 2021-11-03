package com.sofka.tareas.data;

import com.sofka.tareas.AdapterOperations;
import com.sofka.tareas.domain.canonical.job.JobCanonical;
import com.sofka.tareas.domain.entity.job.Job;
import com.sofka.tareas.domain.canonical.job.JobCanonicalRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JobDataAdapter
        extends AdapterOperations<Job, JobData, String, JobDataRepository>
        implements JobCanonicalRepository {


    private final ObjectMapper objectMapper;

    @Autowired
    public JobDataAdapter(JobDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Job.JobBuilder.class).build());
        this.objectMapper = mapper;

    }


    @Override
    public Flux<Job> findAllJobs() {
        return doQueryMany(repository.findAll());

    }
}
