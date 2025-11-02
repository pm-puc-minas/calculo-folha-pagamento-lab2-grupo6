package io.github.progmodular.gestaorh.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.github.progmodular.gestaorh.controller.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(DuplicaValueException.class)
    public ResponseEntity<ErrorResponse> handleDuplicaValueExceptionException(DuplicaValueException e){
        var erroDTO = ErrorResponse.conflictResponse(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserNotExistException(UserNotExistException e){
        var erroDTO = ErrorResponse.standardResponse(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }
}
