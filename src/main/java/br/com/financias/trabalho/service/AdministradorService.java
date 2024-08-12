package br.com.financias.trabalho.service;

import br.com.financias.trabalho.model.dao.AdministradorDao;
import br.com.financias.trabalho.model.dao.ModeradorDao;
import br.com.financias.trabalho.model.dto.ModeradorDTO;
import br.com.financias.trabalho.model.usuarios.Moderador;
import br.com.financias.trabalho.service.dto.ClienteResponseDTO;
import br.com.financias.trabalho.service.dto.ModeradorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {

    @Autowired
    AdministradorDao aDao;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder pass;

    @Autowired
    private ModeradorDao moderadorDao;

    public void DeletaClienteById(Integer id) {

        aDao.deleteById(id);
    }

    public ModeradorResponseDTO adicionaModerador(ModeradorDTO moderadorDTO) {
        Moderador moderador = new Moderador();

        boolean usuarioJaCadastrado = userService.buscaPorLogin(moderadorDTO.getLogin());

        if (usuarioJaCadastrado){
            return new ModeradorResponseDTO(moderadorDTO, "Usuario com esse login já existe!", "registrarModerador");
        }

        if(moderadorDTO.getNome().isEmpty() || moderadorDTO.getEmail().isEmpty() ||
                moderadorDTO.getSenha().isEmpty() || moderadorDTO.getLogin().isEmpty()){
            return new ModeradorResponseDTO(moderadorDTO, "Preencha todos os campos!", "registrarModerador");
        }

        moderador.setEmail(moderadorDTO.getEmail());
        moderador.setNome(moderadorDTO.getNome());
        moderador.setSenha(pass.encode(moderadorDTO.getSenha()));
        moderador.setLogin(moderadorDTO.getLogin());

        moderadorDao.save(moderador);

        return new ModeradorResponseDTO(null, "Cliente registrado com sucesso!", "home");
    }
}
