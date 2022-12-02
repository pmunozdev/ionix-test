package com.ionix.test.backend.controller.advice;

import com.ionix.test.backend.exception.EncodeException;
import com.ionix.test.backend.exception.ExternalServiceException;
import com.ionix.test.backend.exception.MappingExceptionEntity;
import com.ionix.test.backend.model.common.ErrorBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@ControllerAdvice
public class ErrorControllerAdvice   {




    @ExceptionHandler(value= ServerWebInputException.class)
    protected ResponseEntity<ErrorBody> handleException(ServerWebInputException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Error en los parametros de entrada")
                .detailedMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorBody> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Error en los parametros de entrada")
                .detailedMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= IllegalArgumentException.class)
    protected ResponseEntity<ErrorBody> handleIllegalArgumentException(IllegalArgumentException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Error en los parametros de entrada")
                .detailedMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorBody> handleIllegalMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Error en los parametros de entrada")
                .detailedMessage(Stream.of(e.getBindingResult().getFieldErrors()).limit(50).map(Object::toString).collect(Collectors.joining("\n")))
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= DataAccessException.class)
    protected ResponseEntity<ErrorBody> handleIllegalDataAccessException(DataAccessException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error al acceder a la db")
                .detailedMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value= UsernameNotFoundException.class)
    protected ResponseEntity<ErrorBody> handleMappingUsernameNotFoundException(UsernameNotFoundException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value= MappingExceptionEntity.class)
    protected ResponseEntity<ErrorBody> handleMappingExceptionEntity(MappingExceptionEntity e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= EncodeException.class)
    protected ResponseEntity<ErrorBody> handleEncodeException(EncodeException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value= ExternalServiceException.class)
    protected ResponseEntity<ErrorBody> handleEncodeExternalServiceException(ExternalServiceException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.NOT_FOUND);
    }




    @ExceptionHandler(value=Exception.class)
    protected ResponseEntity<ErrorBody> handleException(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorBody.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .detailedMessage(Arrays.asList(e.getStackTrace()).stream().limit(50).map(Object::toString).collect(Collectors.joining("\n")))
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
