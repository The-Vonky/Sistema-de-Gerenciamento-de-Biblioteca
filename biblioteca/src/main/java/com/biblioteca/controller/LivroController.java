package com.biblioteca.controller;

import com.biblioteca.dto.DTOs.*;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // -----------------------------------------------------------------------
    // CRUD básico
    // -----------------------------------------------------------------------

    /**
     * GET /api/livros
     * Lista todos os livros.
     */
    @GetMapping
    public ResponseEntity<List<LivroResponse>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    /**
     * GET /api/livros/{id}
     * Busca livro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    /**
     * POST /api/livros
     * Cria novo livro.
     * Body: { "titulo": "...", "isbn": "...", "categoriaId": 1 }
     */
    @PostMapping
    public ResponseEntity<LivroResponse> criar(@Valid @RequestBody LivroRequest request) {
        LivroResponse criado = livroService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * PUT /api/livros/{id}
     * Atualiza livro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LivroRequest request) {
        return ResponseEntity.ok(livroService.atualizar(id, request));
    }

    /**
     * DELETE /api/livros/{id}
     * Remove livro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------------------------------------------
    // ETAPA 2 — Endpoints de busca (Query Methods)
    // -----------------------------------------------------------------------

    /**
     * 1. GET /api/livros/buscar/titulo-exato?titulo=Duna
     * Busca por TÍTULO EXATO.
     * Query Method: findByTitulo(String titulo)
     */
    @GetMapping("/buscar/titulo-exato")
    public ResponseEntity<List<LivroResponse>> buscarPorTituloExato(
            @RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTituloExato(titulo));
    }

    /**
     * 2. GET /api/livros/buscar/titulo?titulo=fun
     * Busca por PARTE DO TÍTULO (case-insensitive).
     * Query Method: findByTituloContainingIgnoreCase(String titulo)
     */
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<LivroResponse>> buscarPorTituloContendo(
            @RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTituloContendo(titulo));
    }

    /**
     * 3. GET /api/livros/buscar/categoria/1
     * Busca por CATEGORIA (por ID).
     * Query Method: findByCategoriaId(Long categoriaId)
     */
    @GetMapping("/buscar/categoria/{categoriaId}")
    public ResponseEntity<List<LivroResponse>> buscarPorCategoria(
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(livroService.buscarPorCategoria(categoriaId));
    }

    /**
     * BÔNUS — GET /api/livros/buscar/categoria/nome?nome=Tecnologia
     * Busca por NOME DA CATEGORIA (JPQL customizada).
     * Query Method: findByNomeCategoria(String nomeCategoria)
     */
    @GetMapping("/buscar/categoria/nome")
    public ResponseEntity<List<LivroResponse>> buscarPorNomeCategoria(
            @RequestParam String nome) {
        return ResponseEntity.ok(livroService.buscarPorNomeCategoria(nome));
    }

    /**
     * BÔNUS — GET /api/livros/buscar/isbn?isbn=978-0132350884
     * Busca por ISBN.
     * Query Method: findByIsbn(String isbn)
     */
    @GetMapping("/buscar/isbn")
    public ResponseEntity<LivroResponse> buscarPorIsbn(@RequestParam String isbn) {
        return ResponseEntity.ok(livroService.buscarPorIsbn(isbn));
    }
}
