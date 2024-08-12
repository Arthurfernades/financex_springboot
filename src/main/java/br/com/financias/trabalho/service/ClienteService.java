package br.com.financias.trabalho.service;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.Mensagem;
import br.com.financias.trabalho.model.dao.ClienteDao;
import br.com.financias.trabalho.model.dao.MensagemDao;
import br.com.financias.trabalho.model.dao.ModeradorDao;
import br.com.financias.trabalho.model.dto.ClienteDTO;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import br.com.financias.trabalho.service.dto.ClienteResponseDTO;
import br.com.financias.trabalho.service.dto.GastosDashboardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {

    @Autowired
    PasswordEncoder pass;

    @Autowired
    ClienteDao clienteDao;

    @Autowired
    ModeradorDao moderadorDao;

    @Autowired
    UserService userService;

    @Autowired
    GastosService gastosService;
    @Autowired
    private MensagemDao mensagemDao;

    public ClienteResponseDTO registraCliente(ClienteDTO clienteDTO, String loginModerador) {

        Cliente cliente = new Cliente();

        boolean usuarioJaCadastrado = userService.buscaPorLogin(clienteDTO.getLogin());

        if(clienteDTO.getNome().isEmpty() || clienteDTO.getEmail().isEmpty() ||
                clienteDTO.getSenha().isEmpty() || clienteDTO.getLogin().isEmpty()){
            return new ClienteResponseDTO(clienteDTO, "Preencha todos os campos!", "registrar");
        }

        if (usuarioJaCadastrado){
            return new ClienteResponseDTO(clienteDTO, "Usuario com esse login já existe!", "registrar");
        }

        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setSenha(pass.encode(clienteDTO.getSenha()));
        cliente.setLogin(clienteDTO.getLogin());
        cliente.setModerador(moderadorDao.findByLogin(loginModerador));

        clienteDao.save(cliente);

        return new ClienteResponseDTO(null, "Cliente registrado com sucesso!", "home");
    }

    public ClienteResponseDTO updateCliente(ClienteDTO clienteDTO, int id) {

        Optional<Cliente> cliente = clienteDao.findById(id);

        boolean usuarioJaCadastrado = false;

        if(!clienteDTO.getLogin().equals(cliente.get().getLogin())){
            usuarioJaCadastrado = userService.buscaPorLogin(clienteDTO.getLogin());
        }

        if(clienteDTO.getNome().isEmpty() || clienteDTO.getEmail().isEmpty() ||
                clienteDTO.getSenha().isEmpty() || clienteDTO.getLogin().isEmpty()){
            return new ClienteResponseDTO(clienteDTO, "Preencha todos os campos!", "alterar");
        }

        if (usuarioJaCadastrado){
            return new ClienteResponseDTO(clienteDTO, "Usuario com esse login já existe!", "alterar");
        }

        cliente.get().setNome(clienteDTO.getNome());
        cliente.get().setLogin(clienteDTO.getLogin());
        cliente.get().setEmail(clienteDTO.getEmail());
        cliente.get().setSenha(pass.encode(clienteDTO.getSenha()));

        clienteDao.save(cliente.get());

        return new ClienteResponseDTO(clienteDTO, "Cliente alterado com sucesso!", "alterar");
    }

    public void deleteById(Integer id) {

        clienteDao.deleteById(id);
    }

    public List<Gastos> retornaTodosOsGastos(String login) {

        Cliente cliente = clienteDao.findByLogin(login);

        List<Gastos> todosOsGastos = cliente.getGastos();

        return todosOsGastos;

    }

    public GastosDashboardResponseDTO retornaDadosDashboard(String login) {

        Cliente cliente = clienteDao.findByLogin(login);

        List<Gastos> todosOsGastos = cliente.getGastos();

        GastosDashboardResponseDTO response = gastosService.calculoDadosDashboard(todosOsGastos);

        response.setValorReceita(cliente.getReceita());

        response.setValorMeta(cliente.getMeta());

        return response;
    }

    public GastosDashboardResponseDTO retornaDadosGastosCliente(Integer id, Moderador moderador) {

        Optional<Cliente> cliente = clienteDao.findById(id);

        if(cliente.get().getModerador().equals(moderador)){
            List<Gastos> todosOsGastos = cliente.get().getGastos();

            GastosDashboardResponseDTO response = gastosService.calculoDadosDashboard(todosOsGastos);

            response.setValorReceita(cliente.get().getReceita());

            response.setValorMeta(cliente.get().getMeta());

            return response;
        }

            return new GastosDashboardResponseDTO();
    }

    public Optional<Cliente> FindById(Integer id) {

        return clienteDao.findById(id);
    }

    public ClienteResponseDTO adicionaReceita(double receita, String login) {
        Cliente cliente = clienteDao.findByLogin(login);

        cliente.setReceita(receita);
        clienteDao.save(cliente);
        System.out.println(cliente.getReceita());

        return new ClienteResponseDTO(null, "Receita registrada com sucesso!", "home");
    }

    public ClienteResponseDTO adicionaMeta(double meta, String login) {
        Cliente cliente = clienteDao.findByLogin(login);

        cliente.setMeta(meta);
        clienteDao.save(cliente);
        System.out.println(cliente.getMeta());

        return new ClienteResponseDTO(null, "Meta registrada com sucesso!", "home");
    }

    public List<Mensagem> mensagensUsuario(String login) {
        Cliente cliente = clienteDao.findByLogin(login);
        if (cliente != null) {
            return cliente.getMensagens();
        } else {
            return new ArrayList<>();
        }
    }

}