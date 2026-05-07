package com.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_livro")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;

    public Livro() {}
    public Livro(Long id, String titulo, String isbn, Categoria categoria) {
        this.id = id; this.titulo = titulo; this.isbn = isbn; this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
