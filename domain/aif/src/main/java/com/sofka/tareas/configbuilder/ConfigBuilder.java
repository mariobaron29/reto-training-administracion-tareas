package com.sofka.tareas.configbuilder;

import com.sofka.tareas.common.event.EventsGateway;
import com.sofka.tareas.domain.canonical.event.EventCanonicalRepository;
import com.sofka.tareas.domain.canonical.job.JobCanonicalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ConfigBuilder {

    private final EventsGateway eventsGateway;
    private final JobCanonicalRepository jobRepository;
    private final EventCanonicalRepository eventRepository;
    private final ConfigParameters configParameters;
}
