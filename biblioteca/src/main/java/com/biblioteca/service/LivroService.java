package com.biblioteca.service;

import com.biblioteca.dto.DTOs.*;
import com.biblioteca.model.Categoria;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // -----------------------------------------------------------------------
    // CRUD básico
    // -----------------------------------------------------------------------

    /** Lista todos os livros */
    @Transactional(readOnly = true)
    public List<LivroResponse> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Busca livro por ID */
    @Transactional(readOnly = true)
    public LivroResponse buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
        return toResponse(livro);
    }

    /** Cria novo livro */
    @Transactional
    public LivroResponse criar(LivroRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + request.getCategoriaId()));

        Livro livro = new Livro();
        livro.setTitulo(request.getTitulo());
        livro.setIsbn(request.getIsbn());
        livro.setCategoria(categoria);

        return toResponse(livroRepository.save(livro));
    }

    /** Atualiza livro existente */
    @Transactional
    public LivroResponse atualizar(Long id, LivroRequest request) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + request.getCategoriaId()));

        livro.setTitulo(request.getTitulo());
        livro.setIsbn(request.getIsbn());
        livro.setCategoria(categoria);

        return toResponse(livroRepository.save(livro));
    }

    /** Remove livro por ID */
    @Transactional
    public void deletar(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado com ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    // -----------------------------------------------------------------------
    // ETAPA 2 — Query Methods
    // -----------------------------------------------------------------------

    /**
     * 1. Busca por TÍTULO EXATO.
     * Utiliza: findByTitulo(String titulo)
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorTituloExato(String titulo) {
        return livroRepository.findByTitulo(titulo)
                .map(livro -> List.of(toResponse(livro)))
                .orElse(List.of());
    }

    /**
     * 2. Busca por PARTE DO TÍTULO (case-insensitive).
     * Utiliza: findByTituloContainingIgnoreCase(String titulo)
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorTituloContendo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 3. Busca por CATEGORIA (por ID).
     * Utiliza: findByCategoriaId(Long categoriaId)
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorCategoria(Long categoriaId) {
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + categoriaId));

        return livroRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca por NOME DA CATEGORIA (JPQL customizada).
     * Utiliza: findByNomeCategoria(String nomeCategoria)
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorNomeCategoria(String nomeCategoria) {
        return livroRepository.findByNomeCategoria(nomeCategoria)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca por ISBN.
     * Utiliza: findByIsbn(String isbn)
     */
    @Transactional(readOnly = true)
    public LivroResponse buscarPorIsbn(String isbn) {
        Livro livro = livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ISBN: " + isbn));
        return toResponse(livro);
    }

    // -----------------------------------------------------------------------
    // Utilitários
    // -----------------------------------------------------------------------

    /** Converte entidade Livro para DTO de resposta */
    private LivroResponse toResponse(Livro livro) {
        CategoriaSummary categoriaSummary = new CategoriaSummary(
                livro.getCategoria().getId(),
                livro.getCategoria().getNome()
        );
        return new LivroResponse(livro.getId(), livro.getTitulo(), livro.getIsbn(), categoriaSummary);
    }
}
