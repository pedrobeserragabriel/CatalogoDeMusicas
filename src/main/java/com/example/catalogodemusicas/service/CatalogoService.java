package com.example.catalogodemusicas.service;

import com.example.catalogodemusicas.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {
    @Autowired
    private CatalogoRepository catalogoRepository;


}
