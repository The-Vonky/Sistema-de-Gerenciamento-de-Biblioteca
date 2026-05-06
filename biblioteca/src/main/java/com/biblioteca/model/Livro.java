package com.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Livro.
 * Mapeada para a tabela tb_livro no banco de dados.
 */
@Entity
@Table(name = "tb_livro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título não pode ser vazio")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @NotBlank(message = "O ISBN não pode ser vazio")
    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    /**
     * Relacionamento N:1 — Muitos livros pertencem a uma categoria.
     * @ManyToOne: lado "muitos" do relacionamento.
     * @JoinColumn: coluna de chave estrangeira na tb_livro.
     * nullable = false: todo livro DEVE ter uma categoria.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;
}
