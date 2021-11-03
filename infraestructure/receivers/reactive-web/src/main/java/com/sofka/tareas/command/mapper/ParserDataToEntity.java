package com.sofka.tareas.command.mapper;

import com.sofka.tareas.domain.canonical.job.JobCanonical;
import com.sofka.tareas.command.dto.JobCanonicalDto;
import com.sofka.tareas.command.dto.RequestJobDto;
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
                    .status(jobCanonicalDto.getStatus())
                .build();
    }
}
