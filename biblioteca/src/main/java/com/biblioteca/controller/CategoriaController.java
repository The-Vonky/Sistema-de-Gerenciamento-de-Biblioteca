package com.biblioteca.controller;

import com.biblioteca.dto.DTOs.*;
import com.biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para o recurso Categoria.
 * Base URL: /api/categorias
 */
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * GET /api/categorias
     * Lista todas as categorias.
     */
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    /**
     * GET /api/categorias/{id}
     * Busca categoria por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    /**
     * POST /api/categorias
     * Cria nova categoria.
     * Body: { "nome": "Ficção Científica" }
     */
    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse criada = categoriaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    /**
     * PUT /api/categorias/{id}
     * Atualiza categoria existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.atualizar(id, request));
    }

    /**
     * DELETE /api/categorias/{id}
     * Remove categoria por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
