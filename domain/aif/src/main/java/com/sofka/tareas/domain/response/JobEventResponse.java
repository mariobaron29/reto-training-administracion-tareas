package com.sofka.tareas.domain.response;

import com.sofka.tareas.domain.canonical.event.JobEventCanonical;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JobEventResponse {

    private final JobEventCanonical jobEventCanonical;

}
