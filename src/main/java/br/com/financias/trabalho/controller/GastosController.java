package br.com.financias.trabalho.controller;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.dto.GastosDTO;
import br.com.financias.trabalho.service.ClienteService;
import br.com.financias.trabalho.service.GastosService;
import br.com.financias.trabalho.service.dto.GastosDashboardResponseDTO;
import br.com.financias.trabalho.service.dto.GastosResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gastos")
public class GastosController {

    @Autowired
    GastosService gastosService;
    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("criar")
    public String registraGastos(Model model, @ModelAttribute GastosDTO gastosDTO) {
        model.addAttribute("gastos", new GastosDTO());
        return "gastos";
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping("salvar")
    public String salvarGastos(Model model, @ModelAttribute GastosDTO gastosDTO, Authentication user) {

        gastosDTO.setLoginUser(user.getName());

        GastosResponseDTO response = gastosService.registrarGastos(gastosDTO);
        model.addAttribute("messagem", response.getMensagem() );
        model.addAttribute("gastos", response.getGastos());
        return response.getPagina() ;
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public String deletarGastos(@PathVariable Integer id) {
        gastosService.deleteById(id);
        return "redirect:/cliente/gastos";
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/alterar/{id}")
    public String alterarGastos(@PathVariable Integer id, Model model) {

        Optional<Gastos> gastosOpt = gastosService.FindById(id);

        if(gastosOpt.isPresent()) {
            model.addAttribute("gastos", gastosOpt.get());
        } else {

            model.addAttribute("error", "Gastos not found");
            return "errorPage";
        }

        return "alterarGastos";
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @PostMapping("/{id}")
    public String updateGastos(@PathVariable Integer id, @ModelAttribute Gastos gastos, Model model) {
        GastosResponseDTO response = gastosService.updateGastos(gastos, id);
        model.addAttribute("messagem", response.getMensagem());
        model.addAttribute("gastos", response.getGastos2());
        return response.getPagina();
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/exibir/{categoria}")
    public String listarGastos(@PathVariable String categoria, Model model, Authentication user) {
        model.addAttribute("lista", gastosService.retornaGastosPorCategoria(user.getName(), categoria));
        return "gastosDetalhados";
    }

}