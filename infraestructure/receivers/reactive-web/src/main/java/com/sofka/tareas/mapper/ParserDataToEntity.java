package com.sofka.tareas.mapper;

import com.sofka.tareas.domain.canonical.JobCanonical;
import com.sofka.tareas.dto.JobCanonicalDto;
import com.sofka.tareas.dto.RequestJobDto;
import com.sofka.tareas.common.ObjectMapperDomain;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParserDataToEntity implements ObjectMapperDomain {

    @Autowired
    private ObjectMapper mapper;


    public JobCanonical buildJob(RequestJobDto dto) {
        return buildJobCanonical(dto.getJobCanonicalDto());
    }

    private JobCanonical buildJobCanonical(JobCanonicalDto jobCanonicalDto) {
        return JobCanonical.builder()
                    .id(jobCanonicalDto.getId())
                    .cronRegExp(jobCanonicalDto.getCronRegExp())
                    .email(jobCanonicalDto.getEmail())
                    .status(jobCanonicalDto.getStatus())
                    .timeZone(jobCanonicalDto.getTimeZone())
                    .url(jobCanonicalDto.getUrl())
                .build();
    }
}
