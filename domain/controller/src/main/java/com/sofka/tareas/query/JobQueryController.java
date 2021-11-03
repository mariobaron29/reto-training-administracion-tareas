package com.sofka.tareas.query;

import com.sofka.tareas.common.event.notification.HeaderFactory;
import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.domain.canonical.job.JobCanonical;
import com.sofka.tareas.domain.entity.event.JobEventFactory;
import com.sofka.tareas.domain.entity.job.Job;
import com.sofka.tareas.domain.entity.job.JobFactory;
import com.sofka.tareas.domain.response.JobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Log
@RequiredArgsConstructor
public class JobQueryController implements JobFactory, HeaderFactory, JobEventFactory {

    private final ConfigBuilder configBuilder;

    public Mono<JobResponse> processFindJobById(String id) {

        return findJobById(id);

    }

    public Flux<Job> processFindAllJobs() {

        return findAllJobs();

    }

    private Mono<JobResponse> findJobById(String id) {
        return configBuilder.getJobRepository()
                        .findById(id)
                .flatMap(job -> Mono.just(JobResponse.builder()
                        .jobCanonical(Arrays.asList(JobCanonical.builder()
                                .id(job.getId())
                                .url(job.getUrl())
                                .cronRegExp(job.getCronRegExp())
                                .email(job.getEmail())
                                .timeZone(job.getTimeZone())
                                .status(job.getStatus())
                                .build()))
                        .build()));
    }

    private Flux<Job> findAllJobs() {
        return configBuilder.getJobRepository().findAllJobs();
       /* List<JobCanonical> jobs = new ArrayList<>();
       configBuilder.getJobRepository().findAllJobs()
                .map(jobs1 -> jobs1.stream().map(job -> buildJobCanonical(job))
                        .map(jobCanonical -> jobs.add(jobCanonical)))
               .then();

        return Mono.just(JobResponse.builder()
                        .jobCanonical(jobs)
                        .build());*/
    }

}
