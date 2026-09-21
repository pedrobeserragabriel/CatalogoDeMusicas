package com.example.catalogodemusicas.service;

import com.example.catalogodemusicas.model.CatalogoModel;
import com.example.catalogodemusicas.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

@Service
public class CatalogoService {
    @Autowired
    private CatalogoRepository catalogoRepository;

    public List<CatalogoModel> listarTodas(){
        return catalogoRepository.findAll();
    }

    public CatalogoModel cadastrarMusica(CatalogoModel catalogoModel){
        return catalogoRepository.save(catalogoModel);
    }

    public CatalogoModel atualizarCliente(Long id, CatalogoModel catalogoModelAtualizado) {
        CatalogoModel musica = catalogoRepository.findById(id).orElse(null);
        if (musica != null) {
            musica.setNome(catalogoModelAtualizado.getNome());
            musica.setAlbum(catalogoModelAtualizado.getAlbum());
            musica.setArtistas(catalogoModelAtualizado.getArtistas());
            musica.setTempoSegundos(catalogoModelAtualizado.getTempoSegundos());
            musica.setArquivoAudio(catalogoModelAtualizado.getArquivoAudio());
            return catalogoRepository.save(musica);
        }
        else {
            throw new RuntimeException("Música não encontrada com id: " + id);
        }
    }

    public void excluirMusica(Long id) {
        if (!catalogoRepository.existsById(id)) {
            throw new RuntimeException("Música não encontrada com id: " + id);
        }
        catalogoRepository.deleteById(id);
    }

    public Optional<CatalogoModel> buscarPorId(Long id) {
        return catalogoRepository.findById(id);
    }

    public List<CatalogoModel> pesquisar(String campo, String termo) {
        switch (campo) {
            case "album":
                return catalogoRepository.findByAlbumContainingIgnoreCase(termo);
            case "artistas":
                return catalogoRepository.findByArtistasContainingIgnoreCase(termo);
            default:
                return catalogoRepository.findByNomeContainingIgnoreCase(termo);
        }
    }
    public List<CatalogoModel> listarOrdenado(String campo, String direcao) {
        Sort sort;
        if ("tempo".equals(campo)) {
            sort = direcao.equals("desc") ? Sort.by("tempoSegundos").descending() : Sort.by("tempoSegundos").ascending();
        } else {
            sort = direcao.equals("desc") ? Sort.by("nome").descending() : Sort.by("nome").ascending();
        }
        return catalogoRepository.findAll(sort);
    }
}
