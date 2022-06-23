package com.akvelon.facebook.exception;

public class EntityNotFoundException extends RestException {

    public EntityNotFoundException(Class clz, Object obj) {
        super(String.format("Not found %s : %s", clz.getSimpleName(), obj));
    }

    public EntityNotFoundException(Class clz, String spec, Object obj) {
        super(String.format("Not found %s by %s: %s", clz.getSimpleName(), spec, obj));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
