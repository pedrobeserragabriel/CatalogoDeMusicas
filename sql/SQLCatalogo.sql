CREATE DATABASE catalogo_musicas;

USE catalogo_musicas;

CREATE TABLE musicas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    album VARCHAR(100) NOT NULL,
    artistas VARCHAR(100) NOT NULL,
    tempo_segundos INT NOT NULL,
    arquivo_audio VARCHAR(255)
);