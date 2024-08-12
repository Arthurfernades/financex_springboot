package br.com.financias.trabalho.controller;

import br.com.financias.trabalho.model.dto.MensagemDTO;
import br.com.financias.trabalho.service.MensagemService;
import br.com.financias.trabalho.service.dto.MensagemResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mensagem")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping("/criar/{id}")
    public String criarMensagem(Model model, @ModelAttribute MensagemDTO mensagemDTO, @PathVariable int id) {
        mensagemDTO.setClienteId(id);
        model.addAttribute("mensagem", mensagemDTO );
        return "criarMensagem";
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @PostMapping("criar")
    public String criarMensagem(Model model, @ModelAttribute MensagemDTO mensagemDto, Authentication user) {
        System.out.println("##########################" + mensagemDto.getClienteId());
        MensagemResponseDTO responseDTO = mensagemService.criaMensagem(mensagemDto.getClienteId(), mensagemDto.getMensage(), user.getName());
        return "home";
    }
}
