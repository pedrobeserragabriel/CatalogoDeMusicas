package com.example.catalogodemusicas.controller;

import com.example.catalogodemusicas.model.CatalogoModel;
import com.example.catalogodemusicas.service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/musicas")
public class CatalogoController {
    private final CatalogoService catalogoService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    public CatalogoController(CatalogoService catalogoService) {}



}
