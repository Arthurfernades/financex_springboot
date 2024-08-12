package br.com.financias.trabalho.controller;

import br.com.financias.trabalho.model.dto.AdministradorDTO;
import br.com.financias.trabalho.model.dto.ClienteDTO;
import br.com.financias.trabalho.model.dto.ModeradorDTO;
import br.com.financias.trabalho.model.dto.UsuarioListaDTO;
import br.com.financias.trabalho.service.AdministradorService;
import br.com.financias.trabalho.service.UserService;
import br.com.financias.trabalho.service.dto.ClienteResponseDTO;
import br.com.financias.trabalho.service.dto.ModeradorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    UserService uService;

    @Autowired
    AdministradorService aService;

    @Autowired
    private AdministradorService administradorService;

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/listarUsuarios")
    public String listarUsuarios(Model model, @ModelAttribute UsuarioListaDTO usuarioListaDTO) {

        model.addAttribute("lista", uService.getAllUsuarios());

        return("listarUsuarios");
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/{id}")
    public String deletarCliente(@PathVariable Integer id) {
        aService.DeletaClienteById(id);
        return "redirect:/administrador/listarUsuarios";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @GetMapping("registrar")
    public String registrarModerador(Model model, @ModelAttribute ModeradorDTO moderadorDTO) {
        model.addAttribute("moderador", moderadorDTO);
        return "registraModerador";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PostMapping("registrar")
    public String salvarModerador(Model model ,@ModelAttribute ModeradorDTO moderadorDTO, Authentication user) {
        ModeradorResponseDTO responseDTO = administradorService.adicionaModerador(moderadorDTO);
        model.addAttribute("messagem", responseDTO.getMensagem() );
        model.addAttribute("cliente", responseDTO.getModeradorDTO());
        return responseDTO.getPagina();
    }

}