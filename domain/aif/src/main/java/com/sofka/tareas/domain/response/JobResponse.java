package com.sofka.tareas.domain.response;

import com.sofka.tareas.domain.canonical.job.JobCanonical;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class JobResponse {

    private final List<JobCanonical> jobCanonical;

}
