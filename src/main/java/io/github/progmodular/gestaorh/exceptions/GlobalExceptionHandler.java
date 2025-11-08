package io.github.progmodular.gestaorh.exceptions;

import io.github.progmodular.gestaorh.dto.ErrorField;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.github.progmodular.gestaorh.dto.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(DuplicaValueException.class)
    public ResponseEntity<ErrorResponse> handleDuplicaValueExceptionException(DuplicaValueException e) {
        var erroDTO = ErrorResponse.conflictResponse(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserNotExistException(UserNotExistException e) {
        var erroDTO = ErrorResponse.standardResponse(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrorList = e.getFieldErrors();

        List<ErrorField> errorFields = fieldErrorList
                .stream()
                .map(ef -> new ErrorField(ef.getField(), ef.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", errorFields);
    }

    @ExceptionHandler(ValidationListException.class)
    public ResponseEntity<ErrorResponse> handleValidationListException(ValidationListException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                "Validation errors found in the fields:",
                ex.getErrors()
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String customMessage = ex.getMessage();

        if (ex.getMessage().contains("io.github.progmodular.gestaorh.model.Enum.UserType")) {
            customMessage = ex.getMessage().replace(
                    "io.github.progmodular.gestaorh.model.Enum.UserType",
                    "UserType"
            );
        }
        var response = ErrorResponse.standardResponse(customMessage);
        return new ResponseEntity<>(response,status);
    }
}
