package br.com.financias.trabalho.controller;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.Mensagem;
import br.com.financias.trabalho.model.dto.ClienteDTO;
import br.com.financias.trabalho.model.dto.MensagemDTO;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.service.ClienteService;
import br.com.financias.trabalho.service.ModeradorService;
import br.com.financias.trabalho.service.dto.ClienteResponseDTO;
import br.com.financias.trabalho.service.dto.GastosDashboardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModeradorService moderadorService;

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping("registrar")
    public String registrarCliente(Model model, @ModelAttribute ClienteDTO clienteDTO) {
        model.addAttribute("cliente", clienteDTO);
        return "registrar";
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @PostMapping("registrar")
    public String salvarCliente(Model model ,@ModelAttribute ClienteDTO clienteDTO, Authentication user) {
        ClienteResponseDTO response = clienteService.registraCliente(clienteDTO, user.getName());
        model.addAttribute("messagem", response.getMensagem() );
        model.addAttribute("cliente", response.getCliente());
        return response.getPagina();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/receita")
    public String receitaCliente(Model model, @ModelAttribute ClienteDTO clienteDTO) {
        model.addAttribute("receita" , clienteDTO);
        return "receita";
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping("/receita")
    public String adicionaReceita(Model model, @ModelAttribute ClienteDTO clienteDTO, Authentication user) {
        ClienteResponseDTO responseDTO = clienteService.adicionaReceita(clienteDTO.getReceita(), user.getName() );
        model.addAttribute("message", responseDTO.getMensagem());
        return responseDTO.getPagina();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/meta")
    public String metaCliente(Model model, @ModelAttribute ClienteDTO clienteDTO) {
        model.addAttribute("cliente" , clienteDTO);
        return "meta";
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping("/meta")
    public String adicionaMeta(Model model, @ModelAttribute ClienteDTO clienteDTO, Authentication user) {
        ClienteResponseDTO responseDTO = clienteService.adicionaMeta(clienteDTO.getMeta(), user.getName() );
        model.addAttribute("message", responseDTO.getMensagem());
        return responseDTO.getPagina();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("gastos")
    public String gastos(Model model, Authentication user) {
        List<Gastos> todosGastos = clienteService.retornaTodosOsGastos(user.getName());
        model.addAttribute("gastosUsuarios", todosGastos);
        return "clienteGastos";
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping({"dashboard"})
    public String dashboardGastos(Model model, Authentication user, @ModelAttribute GastosDashboardResponseDTO gastosDashboardResponseDTO) {
        GastosDashboardResponseDTO responseDTO = clienteService.retornaDadosDashboard(user.getName());
        model.addAttribute("valorTotal", responseDTO.getValorTotal());
        model.addAttribute("valorContas", responseDTO.getValorContas());
        model.addAttribute("valorSaude", responseDTO.getValorSaude());
        model.addAttribute("valorComida", responseDTO.getValorComida());
        model.addAttribute("valorTransporte", responseDTO.getValorTransporte());
        model.addAttribute("valorInvestimento", responseDTO.getValorInvestimento());
        model.addAttribute("valorDoacao", responseDTO.getValorDoacao());
        model.addAttribute("valorLazer", responseDTO.getValorLazer());
        model.addAttribute("valorAssinaturas", responseDTO.getValorAssinaturas());
        model.addAttribute("valorEducacao", responseDTO.getValorEducacao());
        model.addAttribute("valorOutros", responseDTO.getValorOutros());
        model.addAttribute("valorReceita", responseDTO.getValorReceita());
        model.addAttribute("valorRestante", responseDTO.getValorRestante());
        model.addAttribute("valorMeta", responseDTO.getValorMeta());

        Map<String, Double> graphData = new TreeMap<>();
        graphData.put("Contas", responseDTO.getValorContas());
        graphData.put("Saúde", responseDTO.getValorSaude());
        graphData.put("Comida", responseDTO.getValorComida());
        graphData.put("Transporte", responseDTO.getValorTransporte());
        graphData.put("Investimento", responseDTO.getValorInvestimento());
        graphData.put("Doacao", responseDTO.getValorDoacao());
        graphData.put("Lazer", responseDTO.getValorLazer());
        graphData.put("Assinaturas", responseDTO.getValorAssinaturas());
        graphData.put("Educacao", responseDTO.getValorEducacao());
        graphData.put("Outros", responseDTO.getValorOutros());
        model.addAttribute("chartData", graphData);

        return "dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/{id}")
    public String deletarCliente(@PathVariable Integer id) {
        clienteService.deleteById(id);
        return "redirect:/administrador/listarUsuarios";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/alterar/{id}")
    public String alterarCliente(@PathVariable Integer id, Model model) {

        Optional<Cliente> clienteOpt = clienteService.FindById(id);

        if(clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
        } else {

            model.addAttribute("error", "Cliente not found");
            return "errorPage";
        }

        return "alterar";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PostMapping("/{id}")
    public String updateCliente(@PathVariable Integer id, @ModelAttribute ClienteDTO clienteDTO, Model model) {
        ClienteResponseDTO response = clienteService.updateCliente(clienteDTO, id);
        model.addAttribute("messagem", response.getMensagem() );
        model.addAttribute("cliente", response.getCliente());
        return response.getPagina();
    }


    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/mensagens")
    public String exibirMensagens(Model model, Authentication user, @ModelAttribute MensagemDTO mensagemDTO) {
        List<Mensagem> mensagemDTOS = clienteService.mensagensUsuario(user.getName());
        model.addAttribute("mensagens", mensagemDTOS);

        return "mensagens";
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping({"/gastosCliente/{id}"})
    public String gastosCliente(Model model, Authentication user ,@PathVariable Integer id , @ModelAttribute GastosDashboardResponseDTO gastosDashboardResponseDTO) {
        GastosDashboardResponseDTO responseDTO = clienteService.retornaDadosGastosCliente(id, moderadorService.findByLogin(user.getName()));

        Map<String, Double> graphData = new TreeMap<>();
        graphData.put("Contas", responseDTO.getValorContas());
        graphData.put("Saúde", responseDTO.getValorSaude());
        graphData.put("Comida", responseDTO.getValorComida());
        graphData.put("Transporte", responseDTO.getValorTransporte());
        graphData.put("Investimento", responseDTO.getValorInvestimento());
        graphData.put("Doacao", responseDTO.getValorDoacao());
        graphData.put("Lazer", responseDTO.getValorLazer());
        graphData.put("Assinaturas", responseDTO.getValorAssinaturas());
        graphData.put("Educacao", responseDTO.getValorEducacao());
        graphData.put("Outros", responseDTO.getValorOutros());
        model.addAttribute("chartData", graphData);

        return "gastosCliente";
    }

}