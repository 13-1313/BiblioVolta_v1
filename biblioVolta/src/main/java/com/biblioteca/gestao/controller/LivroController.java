package com.biblioteca.gestao.controller;

import com.biblioteca.gestao.model.Livro;
import com.biblioteca.gestao.model.StatusEmprestimo;
import com.biblioteca.gestao.model.Usuario;
import com.biblioteca.gestao.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal Usuario usuario,
                            Model model,
                            @RequestParam(required = false) String busca,
                            @RequestParam(required = false) String status) {

        model.addAttribute("usuario", usuario);

        List<Livro> livros;

        if (busca != null && !busca.isBlank()) {
            livros = livroService.buscarPorTitulo(busca);
            model.addAttribute("busca", busca);
        } else if (status != null && !status.isBlank()) {
            livros = livroService.listarPorStatus(StatusEmprestimo.valueOf(status));
            model.addAttribute("statusFiltro", status);
        } else {
            livros = livroService.listarTodos();
        }

        model.addAttribute("livros", livros);
        model.addAttribute("statusList", StatusEmprestimo.values());
        model.addAttribute("totalLivros", livroService.listarTodos().size());
        model.addAttribute("totalEmprestados", livroService.listarPorStatus(StatusEmprestimo.EMPRESTADO).size());
        model.addAttribute("totalDisponiveis", livroService.listarPorStatus(StatusEmprestimo.DISPONIVEL).size());

        return "dashboard";
    }

    @GetMapping("/livros/novo")
    public String novoLivroForm(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("usuario", usuario);
        return "livro-form";
    }

    @PostMapping("/livros/salvar")
    public String salvarLivro(@AuthenticationPrincipal Usuario usuario,
                              @Valid @ModelAttribute Livro livro,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "livro-form";
        }

        livro.setStatus(StatusEmprestimo.DISPONIVEL);
        livroService.salvar(livro);
        redirectAttributes.addFlashAttribute("sucesso", "Livro cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    @GetMapping("/livros/{id}/emprestar")
    public String emprestarForm(@AuthenticationPrincipal Usuario usuario,
                                @PathVariable String id,
                                Model model) {

        var livro = livroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        model.addAttribute("livro", livro);
        model.addAttribute("usuario", usuario);
        model.addAttribute("dataMinima", LocalDate.now().plusDays(1).toString());
        return "emprestimo-form";
    }

    @PostMapping("/livros/{id}/emprestar")
    public String confirmarEmprestimo(@AuthenticationPrincipal Usuario usuario,
                                      @PathVariable String id,
                                      @RequestParam String rgm,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoPrevista,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {

        try {
            livroService.registrarEmprestimo(id, rgm, dataDevolucaoPrevista);
            redirectAttributes.addFlashAttribute("sucesso", "Empréstimo registrado com sucesso!");
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            var livro = livroService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
            model.addAttribute("livro", livro);
            model.addAttribute("usuario", usuario);
            model.addAttribute("dataMinima", LocalDate.now().plusDays(1).toString());
            model.addAttribute("erroRgm", e.getMessage());
            return "emprestimo-form";
        }
    }

    @PostMapping("/livros/{id}/devolver")
    public String devolverLivro(@PathVariable String id, RedirectAttributes redirectAttributes) {
        livroService.registrarDevolucao(id);
        redirectAttributes.addFlashAttribute("sucesso", "Devolução registrada com sucesso!");
        return "redirect:/dashboard";
    }

    @PostMapping("/livros/{id}/disponivel")
    public String marcarDisponivel(@PathVariable String id, RedirectAttributes redirectAttributes) {
        livroService.marcarDisponivel(id);
        redirectAttributes.addFlashAttribute("sucesso", "Livro marcado como disponível!");
        return "redirect:/dashboard";
    }

    @PostMapping("/livros/{id}/deletar")
    public String deletarLivro(@PathVariable String id, RedirectAttributes redirectAttributes) {
        livroService.deletar(id);
        redirectAttributes.addFlashAttribute("sucesso", "Livro removido com sucesso!");
        return "redirect:/dashboard";
    }
}
