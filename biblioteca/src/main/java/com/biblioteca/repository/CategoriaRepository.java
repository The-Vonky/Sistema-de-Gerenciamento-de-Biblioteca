package com.biblioteca.repository;

import com.biblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de Categoria.
 * Estende JpaRepository para herdar operações CRUD padrão:
 * save(), findById(), findAll(), deleteById(), etc.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Busca uma categoria pelo nome exato.
     * SQL gerado: SELECT * FROM tb_categoria WHERE nome = ?
     */
    Optional<Categoria> findByNome(String nome);

    /**
     * Busca categorias cujo nome contenha o trecho informado,
     * ignorando diferença entre maiúsculas e minúsculas.
     * SQL gerado: SELECT * FROM tb_categoria WHERE LOWER(nome) LIKE LOWER(?)
     */
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}
