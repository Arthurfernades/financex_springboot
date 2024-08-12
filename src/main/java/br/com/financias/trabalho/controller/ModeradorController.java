package br.com.financias.trabalho.controller;

import br.com.financias.trabalho.model.dto.ClienteListaDTO;
import br.com.financias.trabalho.service.ModeradorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("moderador")
public class ModeradorController {

    @Autowired
    ModeradorService mService;

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping("/{id}")
    public String moderador(@PathVariable int id) {
        mService.deleteById(id);
        return "redirect:/administrador/listarUsuarios";
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping("/listaClientes")
    public String moderadorClientes(Model model, Authentication user) {
        model.addAttribute("lista", mService.findClientesByLogin(user.getName()));
        return "listaClientes";
    }

}
