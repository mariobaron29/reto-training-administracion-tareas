package com.sofka.tareas.common.event;


import com.sofka.tareas.common.event.notification.CanonicalNotification;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class JobCreatedEvent implements Event {

    public static final String EVENT_NAME = "JOB_CREATED";
    private final CanonicalNotification canonical;

    @Override
    public String name() {
        return EVENT_NAME;
    }

    @Override
    public Object getData() {
        return this.canonical;
    }
}
