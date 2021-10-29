package com.sofka.tareas.common.ex;

import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    public enum Type {
        INVALID_HEADER_INITIAL_DATA("Invalid Header initial data"),
        ERROR_NIT_EMPTY("Nit can not be empty");

        private final String message;

        public String getMessage() {
            return message;
        }

        public BusinessException build() {

            return new BusinessException(this, "");
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this, "");
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public BusinessException(Type type, String exceptionInfo) {
        super(type.message + " " + exceptionInfo);
        this.type = type;
    }

    @Override
    public String getCode() {
        return type.name();
    }


}
