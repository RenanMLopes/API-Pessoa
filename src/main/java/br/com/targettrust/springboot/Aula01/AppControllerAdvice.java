package br.com.targettrust.springboot.Aula01;


import br.com.targettrust.springboot.Aula01.Model.exceptions.ClientePossuiEnderecosException;
import br.com.targettrust.springboot.Aula01.Model.exceptions.ExercicioIdsNotFoundException;
import br.com.targettrust.springboot.Aula01.Model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.Aula01.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestControllerAdvice
@Slf4j
public class AppControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegistryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException (RegistryNotFoundException exception){
        log.info("Registro não encontrado");
        return new ErrorResponse("Registro nao encontrado" + exception.getId());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException (RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse("Ocorreu um erro");
    }

    @ExceptionHandler(ClientePossuiEnderecosException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(ClientePossuiEnderecosException exception){
        return new ErrorResponse("O Cliente %s possui endereços e nao pode ser removido".formatted(exception.getClienteId()));
        }

    @ExceptionHandler(ExercicioIdsNotFoundException.class)
    public ErrorResponse handlerException(ExercicioIdsNotFoundException exception){
        var ids = exception.getIds().stream().map(Object::toString).collect(Collectors.joining(","));
        return new ErrorResponse("Não é possivel associar exercicios de id %s, não foram encontrados".formatted(ids));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetails.setType(URI.create("/problem-details/validation-error"));
        Map<String, Object> properties = extractValidationErrors(ex);
        problemDetails.setProperties(properties);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                problemDetails
        );
    }

    private static Map<String, Object> extractValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Set<ValidationErrorsResponse> errors = new HashSet<>();
        var objectTargetName = ex.getBindingResult().getObjectName();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new ValidationErrorsResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        Map<String, Object> properties = Map.of(
                "validations",
                new ValidationErrorResponse(objectTargetName, errors)
        );
        return properties;
    }


}

