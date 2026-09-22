package com.example.catalogodemusicas.controller;

import com.example.catalogodemusicas.model.CatalogoModel;
import com.example.catalogodemusicas.service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/musicas")
public class CatalogoController {
    private final CatalogoService catalogoService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String Listar(@RequestParam(required = false) String campoBusca,
                         @RequestParam(required = false) String termoBusca,
                         @RequestParam(required = false) String campoOrdenacao,
                         @RequestParam(required = false) String direcaoOrdenacao,
                         Model model){
        List<CatalogoModel> musicas;

        if (termoBusca != null && !termoBusca.isBlank()) {
            musicas = catalogoService.pesquisar(campoBusca, termoBusca);
        } else if (campoOrdenacao != null) {
            musicas = catalogoService.listarOrdenado(campoOrdenacao, direcaoOrdenacao);
        } else {
            musicas = catalogoService.listarTodas();
        }

        model.addAttribute("musicas", musicas);
        return "listagem";
    }

    @GetMapping("/novo")
    public String abrirCadastro(Model model){
        model.addAttribute("musicas", new CatalogoModel());
        return "cadastro";
    }

    @PostMapping
    public String cadastrar(
            @Valid @ModelAttribute CatalogoModel catalogoModel,
            BindingResult erros,
            @RequestParam(value = "arquivo", required = false) MultipartFile arquivo,
            RedirectAttributes ra){

        if (erros.hasErrors()) {
            return "cadastro";
        }

        salvarArquivoSeExistir(catalogoModel, arquivo);
        catalogoService.cadastrarMusica(catalogoModel);

        ra.addFlashAttribute("msg", "Música cadastrada com sucesso!");
        return "redirect:/musicas";
    }

    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable("id") Long id, Model model){
        CatalogoModel musica = catalogoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada com id: " + id));
        model.addAttribute("catalogoModel", musica);
        return "cadastro";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute CatalogoModel catalogoModel,
                            BindingResult erros,
                            @RequestParam(value = "arquivo", required = false) MultipartFile arquivo,
                            RedirectAttributes ra){
        if (erros.hasErrors()) {
            return "cadastro";
        }

        salvarArquivoSeExistir(catalogoModel, arquivo);
        catalogoService.atualizarCliente(id, catalogoModel);

        ra.addFlashAttribute("msg", "Música atualizada com sucesso!");
        return "redirect:/musicas";
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            catalogoService.excluirMusica(id);
            ra.addFlashAttribute("msg", "Música excluída com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/musicas";
    }

    // Função para o upload do arquivo da música
    private void salvarArquivoSeExistir(CatalogoModel catalogoModel, MultipartFile arquivo) {
        if (arquivo != null && !arquivo.isEmpty()) {
            try {
                String extensao = "";
                String nomeOriginal = arquivo.getOriginalFilename();
                if (nomeOriginal != null && nomeOriginal.contains(".")) {
                    extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
                }

                String nomeUnico = UUID.randomUUID() + extensao;

                File pasta = new File(uploadDir);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }

                Path caminhoDestino = Path.of(uploadDir, nomeUnico);
                Files.copy(arquivo.getInputStream(), caminhoDestino);

                catalogoModel.setArquivoAudio(nomeUnico);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar o arquivo de áudio: " + e.getMessage());
            }
        }
    }
}
