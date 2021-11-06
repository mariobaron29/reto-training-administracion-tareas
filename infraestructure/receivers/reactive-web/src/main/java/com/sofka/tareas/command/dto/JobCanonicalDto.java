package com.sofka.tareas.command.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobCanonicalDto {

    private String id;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private Boolean status;

}
