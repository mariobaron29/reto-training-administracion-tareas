package com.sofka.tareas.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.tareas.command.dto.RequestJobDto;
import com.sofka.tareas.command.dto.ResponseJobDto;
import com.sofka.tareas.command.mapper.ParserDataToEntity;
import com.sofka.tareas.domain.canonical.event.JobEventCanonical;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class JobCommandService {

    private final JobController controller;

    private final ParserDataToEntity toEntity;
    RequestJobDto inscriptionDto;
    final ObjectMapper mapper = new ObjectMapper();

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path ="/api/v1/tareas/crear-tarea")
    public ResponseEntity<Mono<ResponseJobDto>> createJobQuery(@RequestBody String job){

            try{
                inscriptionDto = mapper.readValue(job, RequestJobDto.class);
            return ResponseEntity.ok()
                    .body(controller.processCreateJob(toEntity.buildJob(inscriptionDto))
                        .flatMap(this::buildDto)
                        );
            }catch(Exception exc){
                return handleErrors(exc);
            }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path ="/api/v1/tareas/actualizar-tarea")
    public ResponseEntity<Mono<ResponseJobDto>> updateJobQuery(@RequestBody String job){

        try{
            inscriptionDto = mapper.readValue(job, RequestJobDto.class);
            return ResponseEntity.ok()
                    .body(controller.processUpdateJob(toEntity.buildJob(inscriptionDto))
                            .flatMap(this::buildDto)
                    );
        }catch(Exception exc){
            return handleErrors(exc);
        }
    }

        @GetMapping(path ="/api/v1/admin-tarea/command/getValue")
    public Mono<String> getValue() {
        return Mono.just("OK");
    }

    private Mono<ResponseJobDto> buildDto(JobEventCanonical canonical) {
        return Mono.just(ResponseJobDto.builder()
                        .jobEventCanonical(canonical)
                .build());
    }

    private ResponseEntity<Mono<ResponseJobDto>> handleErrors(Exception error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Mono.just(ResponseJobDto.builder()
                                .error(error.toString())
                                .build()));

    }

}
