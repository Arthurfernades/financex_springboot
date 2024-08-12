package br.com.financias.trabalho.model.dto;

import br.com.financias.trabalho.model.usuarios.Administrador;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import br.com.financias.trabalho.model.usuarios.UsuarioBase;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserSecurityDetails implements UserDetails {

    private UsuarioBase usuario;

    public UserSecurityDetails(UsuarioBase usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> lista = new ArrayList<>();

        if(usuario instanceof Cliente) {
            lista.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        if(usuario instanceof Administrador) {
            lista.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
        }
        if(usuario instanceof Moderador) {
            lista.add(new SimpleGrantedAuthority("ROLE_MODERADOR"));
        }
        lista.add(new SimpleGrantedAuthority("ROLE_USUARIO"));

        return lista;
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    public UsuarioBase getUsuario() { return usuario; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}