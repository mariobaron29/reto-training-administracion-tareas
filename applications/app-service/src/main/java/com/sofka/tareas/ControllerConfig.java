package com.sofka.tareas;

import com.sofka.tareas.command.JobController;
import com.sofka.tareas.common.event.EventsGateway;
import com.sofka.tareas.configbuilder.ConfigBuilder;
import com.sofka.tareas.configbuilder.ConfigParameters;
import com.sofka.tareas.domain.canonical.event.EventCanonicalRepository;
import com.sofka.tareas.domain.canonical.job.JobCanonicalRepository;
import com.sofka.tareas.query.JobQueryController;
import lombok.extern.java.Log;
import org.reactivecommons.async.impl.config.RabbitMqConfig;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Log
@Configuration
@Import(RabbitMqConfig.class)
public class ControllerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${component.name}")
    private String componentName;

    @Value("${service.name}")
    private String serviceName;

    @Value("${operation}")
    private String operation;

    @Value("${rabbit.ssl.protocol}")
    private String tls;

    @Bean
    public JobController jobController(
            EventsGateway eventsGateway,
            JobCanonicalRepository jobRepository,
            EventCanonicalRepository eventRepository
    ) {
        return new JobController(
                ConfigBuilder.builder()
                        .eventsGateway(eventsGateway)
                        .jobRepository(jobRepository)
                        .eventRepository(eventRepository)
                        .configParameters(ConfigParameters.builder()
                                .componentName(componentName)
                                .serviceName(serviceName)
                                .operation(operation)
                                .build())
                        .build()
        );
    }

    @Bean
    public JobQueryController jobQueryController(
            EventsGateway eventsGateway,
            JobCanonicalRepository jobRepository,
            EventCanonicalRepository eventRepository
    ) {
        return new JobQueryController(
                ConfigBuilder.builder()
                        .eventsGateway(eventsGateway)
                        .jobRepository(jobRepository)
                        .eventRepository(eventRepository)
                        .configParameters(ConfigParameters.builder()
                                .componentName(componentName)
                                .serviceName(serviceName)
                                .operation(operation)
                                .build())
                        .build()
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapperImp();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(TcpClient.newConnection())))
                .build();
    }

}
