package br.com.financias.trabalho.service;
import br.com.financias.trabalho.model.dao.AdministradorDao;
import br.com.financias.trabalho.model.dao.ClienteDao;
import br.com.financias.trabalho.model.dao.ModeradorDao;
import br.com.financias.trabalho.model.dao.UsuarioBaseDao;
import br.com.financias.trabalho.model.dto.UserSecurityDetails;
import br.com.financias.trabalho.model.dto.UsuarioListaDTO;
import br.com.financias.trabalho.model.usuarios.Administrador;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import br.com.financias.trabalho.model.usuarios.UsuarioBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UsuarioBaseDao ud;

    @Autowired
    ClienteDao cd;

    @Autowired
    AdministradorDao ad;

    @Autowired
    ModeradorDao md;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClienteDao clienteDao;;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Procurando por usuário com login: " + username);

        Cliente c = cd.findByLogin(username);
        if (c != null) {
            System.out.println("Cliente encontrado: " + c.getLogin());
            return new UserSecurityDetails(c);
        } else {
            Administrador a = ad.findByLogin(username);
            if (a != null) {
                System.out.println("Administrador encontrado: " + a.getLogin());
                return new UserSecurityDetails(a);
            } else {
                Moderador m = md.findByLogin(username);
                if (m != null) {
                    System.out.println("Moderador encontrado: " + m.getLogin());
                    return new UserSecurityDetails(m);
                } else {
                    System.out.println("Usuário não encontrado: " + username);
                    throw new UsernameNotFoundException("Não foi possível encontrar o usuário.");
                }
            }
        }
    }

    public List<UsuarioListaDTO> getAllUsuarios() {

        List<Cliente> listaCliente = cd.findAll();
        List<Moderador> listaModerador = md.findAll();
        List<Administrador> listaAdministrador = ad.findAll();

        List<UsuarioListaDTO> listaUsuarios = new ArrayList<>();

        listaCliente.forEach( a -> listaUsuarios.add(new UsuarioListaDTO( a.getId(), a.getNome(),
                a.getLogin(), a.getEmail(), a.getSenha() ,"cliente")));

        listaModerador.forEach( a -> listaUsuarios.add(new UsuarioListaDTO( a.getId(), a.getNome(),
                a.getLogin(), a.getEmail(), a.getSenha() ,"moderador")));

        listaAdministrador.forEach( a -> listaUsuarios.add(new UsuarioListaDTO( a.getId(), a.getNome(),
                a.getLogin(), a.getEmail(), a.getSenha() ,"administrador")));

        return listaUsuarios;
    }

    public boolean buscaPorLogin(String login) {

        Optional<Cliente> clienteByLogin = Optional.ofNullable(cd.findByLogin(login));
        Optional<Administrador> administradorByLogin = Optional.ofNullable(ad.findByLogin(login));
        Optional<Moderador> moderadorByLogin = Optional.ofNullable(md.findByLogin(login));

        if (clienteByLogin.isPresent()|| administradorByLogin.isPresent() || moderadorByLogin.isPresent()){
            return true;
        }

        return false;
    }

}
