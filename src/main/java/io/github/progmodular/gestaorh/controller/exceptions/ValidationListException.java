package io.github.progmodular.gestaorh.controller.exceptions;

import io.github.progmodular.gestaorh.controller.dto.ErrorField;

import java.util.List;

public class ValidationListException extends RuntimeException {

    private final List<ErrorField> errors;

    public ValidationListException(List<ErrorField> errors) {
        super("One or more fields failed to validate.");
        this.errors = errors;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }
}