package io.github.progmodular.gestaorh.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status, String message, List<ErrorField> erros) {
    public static ErrorResponse standardResponse(String message)
    {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),message,List.of());
    }

    public static ErrorResponse conflictResponse(String message)
    {
        return new ErrorResponse(HttpStatus.CONFLICT.value(),message,List.of());
    }
}
