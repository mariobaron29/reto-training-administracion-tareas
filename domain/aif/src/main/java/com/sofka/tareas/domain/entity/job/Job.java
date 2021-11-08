package com.sofka.tareas.domain.entity.job;

import com.sofka.tareas.common.event.JobCreatedEvent;
import com.sofka.tareas.common.event.notification.CanonicalNotification;
import com.sofka.tareas.domain.entity.event.JobEvent;
import com.sofka.tareas.domain.entity.event.JobEventFactory;
import com.sofka.tareas.domain.entity.event.JobExecution;
import lombok.Builder;
import org.springframework.data.domain.AbstractAggregateRoot;
import reactor.core.publisher.Mono;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Builder(toBuilder = true)
public class Job extends AbstractAggregateRoot<Job> implements JobEventFactory, JobFactory {
    @Id
    @Column(name = "job_id", nullable = false)
    private String id;

    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private Boolean status;
    private List<JobExecution> executions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getEmail() {
        return email;
    }

    public String getCronRegExp() {
        return cronRegExp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Mono<Job> registerJobCreatedEvent(JobEvent jobEvent){
        JobCreatedEvent event = new JobCreatedEvent(CanonicalNotification.<JobEvent>builder()
                .data(jobEvent)
                .build());

        return Mono.just(registerEvent(event))
                .thenReturn(buildJob(jobEvent));
    }

}
