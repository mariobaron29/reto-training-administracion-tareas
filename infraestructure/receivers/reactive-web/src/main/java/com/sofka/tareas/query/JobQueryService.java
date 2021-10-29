package com.sofka.tareas.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.tareas.JobController;
import com.sofka.tareas.domain.response.JobResponse;
import com.sofka.tareas.dto.RequestJobDto;
import com.sofka.tareas.dto.ResponseJobDto;
import com.sofka.tareas.mapper.ParserDataToEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class JobQueryService {

    private final JobController controller;

    private final ParserDataToEntity toEntity;
    RequestJobDto inscriptionDto;
    final ObjectMapper mapper = new ObjectMapper();

        @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path ="/api/v1/tareas/consultar-tarea")
    public ResponseEntity<Mono<ResponseJobDto>> createJobQuery(
            @RequestPart("tarea") String dto ){

            try{
                inscriptionDto = mapper.readValue(dto, RequestJobDto.class);
            return ResponseEntity.ok()
                    .body(controller.processCreateJob(toEntity.buildJob(inscriptionDto))
                        .flatMap(substituteResponse -> buildDto(substituteResponse))
                        );
            }catch(Exception exc){
                return handleErrors(exc);
            }
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path ="/api/v1/tareas/consultar-tareas")
    public ResponseEntity<Mono<ResponseJobDto>> updateJobQuery(
            @RequestPart("tarea") String dto ){

        try{
            inscriptionDto = mapper.readValue(dto, RequestJobDto.class);
            return ResponseEntity.ok()
                    .body(controller.processUpdateJob(toEntity.buildJob(inscriptionDto))
                            .flatMap(substituteResponse -> buildDto(substituteResponse))
                    );
        }catch(Exception exc){
            return handleErrors(exc);
        }
    }

        @GetMapping(path ="/api/v1/admin-tarea/query/getValue")
    public Mono<String> getValue() {
        return Mono.just("OK");
    }

    private Mono<ResponseJobDto> buildDto(JobResponse response) {
        return Mono.just(ResponseJobDto.builder()
                        .jobCanonical(response.getJobCanonical())
                .build());
    }

    private ResponseEntity<Mono<ResponseJobDto>> handleErrors(Exception error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Mono.just(ResponseJobDto.builder()
                                .error(error.toString())
                                .build()));

    }

}
