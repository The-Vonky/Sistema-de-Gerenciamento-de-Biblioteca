package com.biblioteca.service;

import com.biblioteca.dto.DTOs.*;
import com.biblioteca.model.Categoria;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio das Categorias.
 */
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    /** Lista todas as categorias com o total de livros em cada uma */
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Busca categoria por ID */
    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
        return toResponse(categoria);
    }

    /** Cria nova categoria */
    @Transactional
    public CategoriaResponse criar(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        Categoria salva = categoriaRepository.save(categoria);
        return toResponse(salva);
    }

    /** Atualiza categoria existente */
    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
        categoria.setNome(request.getNome());
        Categoria atualizada = categoriaRepository.save(categoria);
        return toResponse(atualizada);
    }

    /** Remove categoria por ID */
    @Transactional
    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    /** Converte entidade para DTO de resposta */
    private CategoriaResponse toResponse(Categoria categoria) {
        long total = livroRepository.countByCategoriaId(categoria.getId());
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), total);
    }
}
