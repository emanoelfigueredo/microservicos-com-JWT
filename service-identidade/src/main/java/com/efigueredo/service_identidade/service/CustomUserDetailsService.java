package com.efigueredo.service_identidade.service;

import com.efigueredo.service_identidade.domain.Usuario;
import com.efigueredo.service_identidade.domain.UsuarioRepository;
import com.efigueredo.service_identidade.infra.conf.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = this.repository.findByUsername(username);
        return usuarioOptional
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found name: " + username));
    }
}
