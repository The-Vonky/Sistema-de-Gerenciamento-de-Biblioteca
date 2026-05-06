package com.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidade que representa uma Categoria de livros.
 * Mapeada para a tabela tb_categoria no banco de dados.
 */
@Entity
@Table(name = "tb_categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria não pode ser vazio")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /**
     * Relacionamento 1:N — Uma categoria possui muitos livros.
     * mappedBy aponta para o campo "categoria" em Livro.
     * cascade = REMOVE: ao deletar categoria, deleta livros vinculados.
     * fetch = LAZY: livros carregados somente quando solicitados.
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros;
}
