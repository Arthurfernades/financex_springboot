package br.com.financias.trabalho.service;

import br.com.financias.trabalho.model.Mensagem;
import br.com.financias.trabalho.model.dao.ClienteDao;
import br.com.financias.trabalho.model.dao.MensagemDao;
import br.com.financias.trabalho.model.dao.ModeradorDao;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import br.com.financias.trabalho.service.dto.MensagemResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MensagemService {
    private final ClienteDao clienteDao;
    private final ModeradorDao moderadorDao;
    private final MensagemDao mensagemDao;

    public MensagemService(ClienteDao clienteDao, ModeradorDao moderadorDao, MensagemDao mensagemDao) {
        this.clienteDao = clienteDao;
        this.moderadorDao = moderadorDao;
        this.mensagemDao = mensagemDao;
    }

    public MensagemResponseDTO criaMensagem(int id, String mensagemDto, String Login) {
        Optional<Cliente> cliente = clienteDao.findById(id);
        Moderador moderador = moderadorDao.findByLogin(Login);

        System.out.println("O clinte encontrado é na service " + id );

        Mensagem mensagem = new Mensagem();
        mensagem.setMensagem(mensagemDto);
        mensagem.setCliente(cliente.get());
        mensagem.setModerador(moderador);

        System.out.println(mensagem.getCliente().getLogin());

        mensagemDao.save(mensagem);


        return null;
    }
}
