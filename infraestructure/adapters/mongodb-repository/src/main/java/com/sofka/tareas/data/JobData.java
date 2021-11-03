package com.sofka.tareas.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JobData {
    @Id
    private String id;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private String status;
}
