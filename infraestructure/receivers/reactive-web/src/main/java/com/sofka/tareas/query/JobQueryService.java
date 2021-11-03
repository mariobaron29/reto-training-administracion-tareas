package com.sofka.tareas.query;

import com.sofka.tareas.domain.response.JobResponse;
import com.sofka.tareas.query.dto.ResponseJobQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class JobQueryService {

    private final JobQueryController controller;

        @GetMapping(path ="/api/v1/tareas/consultar-tarea")
    public ResponseEntity<Mono<ResponseJobQueryDto>> findJobById(
            @RequestParam("id") String id ){

            try{
            return ResponseEntity.ok()
                    .body(controller.processFindJobById(id)
                        .flatMap(substituteResponse -> buildDto(substituteResponse))
                        );
            }catch(Exception exc){
                return handleErrors(exc);
            }
    }

    @GetMapping(path ="/api/v1/tareas/consultar-tareas")
    public ResponseEntity findAllJobs( ){

             return ResponseEntity.ok()
                    .body(controller.processFindAllJobs());
    }

        @GetMapping(path ="/api/v1/admin-tarea/query/getValue")
    public Mono<String> getValue() {
        return Mono.just("OK");
    }

    private Mono<ResponseJobQueryDto> buildDto(JobResponse response) {
        return Mono.just(ResponseJobQueryDto.builder()
                        .jobCanonical(response.getJobCanonical())
                .build());
    }

    private ResponseEntity<Mono<ResponseJobQueryDto>> handleErrors(Exception error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Mono.just(ResponseJobQueryDto.builder()
                                .error(error.toString())
                                .build()));

    }

}
