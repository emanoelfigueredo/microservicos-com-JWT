package com.efigueredo.service_identidade.infra.conf.exception;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Component
public class HandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DtoErro>> tratarMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<DtoErro> listaDtoErros = ex.getFieldErrors().stream().map(DtoErro::new).toList();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(listaDtoErros);
    }

    @ExceptionHandler(IdentityException.class)
    public ResponseEntity<DtoErro> tratarIdentityException(IdentityException ex) {
        DtoErro erro = new DtoErro(ex.getTitle(), ex.getDetail(), ex.getType(), ex.getStatus());
        int codeStatus = Integer.parseInt(ex.getStatus());
        return ResponseEntity.status(codeStatus).body(erro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DtoErro> tratarErro(BadCredentialsException ex) {
        DtoErro erro = new DtoErro("Falha na autenticacao", "Credenciais invalidas", "", "401");
        return ResponseEntity.status(401).body(erro);
    }

    public ResponseEntity<DtoErro> tratarExcecoesGenerias(Exception ex) {
        DtoErro erro = new DtoErro("", ex.getStackTrace().toString(), "", "500");
        return ResponseEntity.status(500).body(erro);
    }

}
