package com.example.catalogodemusicas.repository;

import com.example.catalogodemusicas.model.CatalogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogoRepository extends JpaRepository<CatalogoModel, Long> {

    // Pesquisa por nome da música e álbum
    List<CatalogoModel> findByNomeContainingIgnoreCase(String nome);

    List<CatalogoModel> findByAlbumContainingIgnoreCase(String album);

    List<CatalogoModel> findByArtistasContainingIgnoreCase(String artistas);

}