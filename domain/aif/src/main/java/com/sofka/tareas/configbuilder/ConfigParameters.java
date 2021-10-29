package com.sofka.tareas.configbuilder;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ConfigParameters {

    private final String componentName;
    private final String serviceName;
    private final String operation;

}
