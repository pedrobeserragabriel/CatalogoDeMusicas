package com.example.catalogodemusicas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "musicas")
public class CatalogoModel {

    public CatalogoModel() {
    }

    // Construtor

    public CatalogoModel(String nome, String album, String artistas, int tempoSegundos, String arquivoAudio) {
        this.nome = nome;
        this.album = album;
        this.artistas = artistas;
        this.tempoSegundos = tempoSegundos;
        this.arquivoAudio = arquivoAudio;
    }

    // Validações conforme os slides do professor, exceto o de arquivo do áudio da música

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O nome do álbum não pode estar em branco.")
    @Size(max = 100, message = "O nome do álbum deve ter no máximo 100 caracteres.")
    private String album;

    @NotBlank(message = "O nome dos artistas não pode estar em branco.")
    @Size(max = 100, message = "O nome dos artistas deve ter no máximo 100 caracteres.")
    private String artistas;

    @Min(value = 1, message = "O tempo deve ser maior que 0 segundos.")
    private int tempoSegundos;

    @Column(name = "arquivo_audio")
    private String arquivoAudio;

    // Todos os Getters e Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getArtistas() {
        return artistas;
    }
    public void setArtistas(String artistas) {
        this.artistas = artistas;
    }
    public int getTempoSegundos() {
        return tempoSegundos;
    }
    public void setTempoSegundos(int tempoSegundos) {
        this.tempoSegundos = tempoSegundos;
    }
    public String getArquivoAudio() {
        return arquivoAudio;
    }
    public void setArquivoAudio(String arquivoAudio) {
        this.arquivoAudio = arquivoAudio;
    }

    // Mostrar o tempo em Minutos na Tela Depois

    public String getTempoFormatado() {
        int minutos = tempoSegundos / 60;
        int segundos = tempoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }
}
