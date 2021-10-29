package com.sofka.tareas;

import com.sofka.tareas.domain.Job;
import com.sofka.tareas.domain.canonical.JobCanonicalRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
