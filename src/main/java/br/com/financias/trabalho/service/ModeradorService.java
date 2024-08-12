package br.com.financias.trabalho.service;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.dao.ModeradorDao;
import br.com.financias.trabalho.model.dto.ClienteListaDTO;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeradorService {

    @Autowired
    ModeradorDao mDao;

    public void deleteById(int id) {
        mDao.deleteById(id);
    }

    public Moderador findByLogin(String login) { return mDao.findByLogin(login);}

    public List<ClienteListaDTO> findClientesByLogin(String login) {

        Moderador moderador = mDao.findByLogin(login);

        List<Cliente> listaClientes = moderador.getListaClientes();

        List<ClienteListaDTO> listaClientesDTO = new ArrayList<>();

        for (Cliente cliente : listaClientes) {

            ClienteListaDTO clienteListaDTO = getClienteListaDTO(cliente);

            listaClientesDTO.add(clienteListaDTO);
        }

        return listaClientesDTO;
    }

    private static ClienteListaDTO getClienteListaDTO(Cliente cliente) {
        ClienteListaDTO clienteListaDTO = new ClienteListaDTO();
        double gastosTotais = 0;

        clienteListaDTO.setNome(cliente.getNome());
        clienteListaDTO.setReceita(cliente.getReceita());
        clienteListaDTO.setMeta(cliente.getMeta());
        clienteListaDTO.setId(cliente.getId());

        List<Gastos> listaGastos =  cliente.getGastos();

        for (Gastos gasto : listaGastos) {
            gastosTotais+= gasto.getValor();
        }

            clienteListaDTO.setGastosTotais(gastosTotais);
        return clienteListaDTO;
    }
}
