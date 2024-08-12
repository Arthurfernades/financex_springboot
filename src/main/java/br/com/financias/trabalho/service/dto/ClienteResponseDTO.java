package br.com.financias.trabalho.service.dto;

import br.com.financias.trabalho.model.dto.ClienteDTO;
import br.com.financias.trabalho.model.usuarios.Cliente;

public class ClienteResponseDTO {
    private ClienteDTO cliente;
    private String mensagem;
    private String pagina;

    public ClienteResponseDTO(ClienteDTO cliente, String mensagem, String pagina) {
        this.cliente = cliente;
        this.mensagem = mensagem;
        this.pagina = pagina;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
}
