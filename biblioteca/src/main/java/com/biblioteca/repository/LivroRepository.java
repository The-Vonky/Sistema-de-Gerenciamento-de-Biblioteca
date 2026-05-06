package com.biblioteca.repository;

import com.biblioteca.model.Categoria;
import com.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de Livro.
 * Implementa Query Methods para buscas customizadas.
 * O Spring Data JPA gera automaticamente o SQL a partir dos nomes dos métodos.
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // -----------------------------------------------------------------------
    // ETAPA 2 — Consultas (Query Methods) obrigatórias
    // -----------------------------------------------------------------------

    /**
     * 1. Busca por título EXATO.
     * SQL: SELECT * FROM tb_livro WHERE titulo = ?
     *
     * @param titulo título completo e exato do livro
     * @return Optional<Livro> — presente se encontrado
     */
    Optional<Livro> findByTitulo(String titulo);

    /**
     * 2. Busca por PARTE do título, ignorando maiúsculas/minúsculas.
     * SQL: SELECT * FROM tb_livro WHERE LOWER(titulo) LIKE LOWER('%?%')
     *
     * @param titulo trecho do título a ser pesquisado
     * @return lista de livros cujo título contém o trecho informado
     */
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * 3. Busca de livros por uma CATEGORIA específica (objeto Categoria).
     * SQL: SELECT * FROM tb_livro WHERE categoria_id = ?
     *
     * @param categoria objeto Categoria pelo qual filtrar
     * @return lista de livros da categoria informada
     */
    List<Livro> findByCategoria(Categoria categoria);

    // -----------------------------------------------------------------------
    // Consultas adicionais (bônus)
    // -----------------------------------------------------------------------

    /**
     * Busca livros pelo ID da categoria diretamente (sem precisar carregar o objeto).
     * SQL: SELECT * FROM tb_livro WHERE categoria_id = ?
     *
     * @param categoriaId ID da categoria
     * @return lista de livros da categoria
     */
    List<Livro> findByCategoriaId(Long categoriaId);

    /**
     * Busca livro pelo ISBN (identificador único).
     * SQL: SELECT * FROM tb_livro WHERE isbn = ?
     *
     * @param isbn ISBN do livro
     * @return Optional<Livro>
     */
    Optional<Livro> findByIsbn(String isbn);

    /**
     * Busca livros que começam com determinado prefixo de título.
     * SQL: SELECT * FROM tb_livro WHERE LOWER(titulo) LIKE LOWER('?%')
     *
     * @param prefixo início do título
     * @return lista de livros
     */
    List<Livro> findByTituloStartingWithIgnoreCase(String prefixo);

    /**
     * JPQL customizada: busca livros por nome da categoria (sem precisar do objeto).
     * Útil para filtrar direto pelo nome da categoria como string.
     *
     * @param nomeCategoria nome da categoria
     * @return lista de livros
     */
    @Query("SELECT l FROM Livro l WHERE LOWER(l.categoria.nome) = LOWER(:nomeCategoria)")
    List<Livro> findByNomeCategoria(@Param("nomeCategoria") String nomeCategoria);

    /**
     * Conta quantos livros existem em uma categoria.
     *
     * @param categoriaId ID da categoria
     * @return número de livros
     */
    long countByCategoriaId(Long categoriaId);
}
