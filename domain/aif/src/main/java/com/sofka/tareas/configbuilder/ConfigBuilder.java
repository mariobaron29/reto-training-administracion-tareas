package com.sofka.tareas.configbuilder;

import com.sofka.tareas.common.event.EventsGateway;
import com.sofka.tareas.domain.canonical.JobCanonicalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ConfigBuilder {

    private final EventsGateway eventsGateway;
    private final JobCanonicalRepository jobRepository;
    private final ConfigParameters configParameters;
}
