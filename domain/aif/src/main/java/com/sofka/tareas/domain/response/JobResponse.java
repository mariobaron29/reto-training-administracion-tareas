package com.sofka.tareas.domain.response;

import com.sofka.tareas.domain.canonical.JobCanonical;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JobResponse {

    private final JobCanonical jobCanonical;

}
