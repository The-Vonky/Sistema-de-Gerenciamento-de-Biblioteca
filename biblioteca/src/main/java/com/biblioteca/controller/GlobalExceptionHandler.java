package com.biblioteca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções.
 * Intercepta erros e devolve respostas JSON padronizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Trata RuntimeException (entidade não encontrada, etc.) */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", HttpStatus.NOT_FOUND.value());
        erro.put("erro", "Recurso não encontrado");
        erro.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /** Trata erros de validação (@Valid) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            campos.put(field, msg);
        });

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", HttpStatus.BAD_REQUEST.value());
        erro.put("erro", "Dados inválidos");
        erro.put("campos", campos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
