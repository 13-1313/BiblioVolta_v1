package com.biblioteca.gestao.controller;

import com.biblioteca.gestao.model.Aluno;
import com.biblioteca.gestao.model.Usuario;
import com.biblioteca.gestao.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Optional;

@Controller
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/alunos/novo")
    public String novoAlunoForm(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("usuario", usuario);
        return "aluno-form";
    }

    @PostMapping("/alunos/salvar")
    public String salvarAluno(@AuthenticationPrincipal Usuario usuario,
                              @Valid @ModelAttribute Aluno aluno,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "aluno-form";
        }

        if (alunoService.rgmJaCadastrado(aluno.getRgm())) {
            result.rejectValue("rgm", "rgm.duplicado", "RGM já cadastrado no sistema");
            model.addAttribute("usuario", usuario);
            return "aluno-form";
        }

        alunoService.salvar(aluno);
        redirectAttributes.addFlashAttribute("sucesso", "Aluno cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    @GetMapping("/alunos/buscar")
    @ResponseBody
    public ResponseEntity<Map<String, String>> buscarPorRgm(@RequestParam String rgm) {
        Optional<Aluno> aluno = alunoService.buscarPorRgm(rgm);

        if (aluno.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "nome", aluno.get().getNome(),
                    "curso", aluno.get().getCurso(),
                    "rgm", aluno.get().getRgm()
            ));
        }

        return ResponseEntity.notFound().build();
    }
}
