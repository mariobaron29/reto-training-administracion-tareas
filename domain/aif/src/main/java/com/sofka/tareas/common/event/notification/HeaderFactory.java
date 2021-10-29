package com.sofka.tareas.common.event.notification;

import java.util.Date;

public interface HeaderFactory {
    default Header getHeader(String uuid, String serviceName) {
        return Header.builder()
                .applicationId(serviceName)
                .transactionId(uuid)
                .hostname("vtex-canonico-producto-rabbitmq-mongodb")
                .user("vtex-canonico-producto-rabbitmq-mongodb")
                .transactionDate(new Date())
                .build();
    }
}
