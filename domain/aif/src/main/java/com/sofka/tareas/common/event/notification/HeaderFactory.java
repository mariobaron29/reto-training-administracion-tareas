package com.sofka.tareas.common.event.notification;

import java.util.Date;

public interface HeaderFactory {
    default Header getHeader(String uuid, String serviceName) {
        return Header.builder()
                .applicationId(serviceName)
                .transactionId(uuid)
                .hostname("reto-training-administracion-tareas")
                .user("reto")
                .transactionDate(new Date())
                .build();
    }
}
