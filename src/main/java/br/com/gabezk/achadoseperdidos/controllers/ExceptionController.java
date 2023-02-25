package br.com.gabezk.achadoseperdidos.controllers;

import br.com.gabezk.achadoseperdidos.exceptions.ErroPrincipal;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErroPrincipal> GeneralException(ErrorException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPrincipal erroPrincipal = new ErroPrincipal(
                ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(String.valueOf(ZoneId.systemDefault()))),
                status.value(),
                status.toString(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erroPrincipal);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErroPrincipal> GeneralException(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPrincipal erroPrincipal = new ErroPrincipal(
                ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(String.valueOf(ZoneId.systemDefault()))),
                status.value(),
                status.toString(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erroPrincipal);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErroPrincipal> GeneralException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPrincipal erroPrincipal = new ErroPrincipal(
                ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(String.valueOf(ZoneId.systemDefault()))),
                status.value(),
                status.toString(),
                (e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erroPrincipal);
    }
}