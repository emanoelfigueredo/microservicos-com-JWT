package com.efigueredo.service_identidade.infra.controller;

import com.efigueredo.service_identidade.service.RegistroUsuarioService;
import com.efigueredo.service_identidade.service.TokenJwt;
import com.efigueredo.service_identidade.service.TokenJwtService;
import com.efigueredo.service_identidade.service.dto.DtoAutenticacao;
import com.efigueredo.service_identidade.service.dto.requisicao.DtoRegistroRequisicao;
import com.efigueredo.service_identidade.service.exception.IdentidadeException;
import com.efigueredo.service_identidade.service.exception.NaoAutenticadoException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("identidade")
public class IdentidadeController {

    @Autowired
    private RegistroUsuarioService autenticacaoService;
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenJwtService tokenJwtService;

    @PostMapping("registrar")
    public ResponseEntity registrar(@RequestBody @Valid DtoRegistroRequisicao dto) throws IdentidadeException {
        this.autenticacaoService.salvarUsuario(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("autenticar")
    public ResponseEntity<TokenJwt> autenticacao(@RequestBody @Valid DtoAutenticacao dto) throws IdentidadeException {
        this.autenticar(dto);
        TokenJwt tokenJwt = this.tokenJwtService.gerarToken(dto.getUsername());
        return ResponseEntity.ok().body(tokenJwt);
    }

    @GetMapping("validar")
    public ResponseEntity<String> validar(@RequestParam("token") String token) {
        this.tokenJwtService.validarToken(token);
        return ResponseEntity.ok().build();
    }

    private void autenticar(DtoAutenticacao dto) throws IdentidadeException {
        Authentication autenticacao = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getSenha()));
        if(!autenticacao.isAuthenticated()) {
            throw new NaoAutenticadoException("Credenciais invalidas");
        }
    }

}