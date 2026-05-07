package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOs {

    // ----- REQUEST DTOs -----

    public static class CategoriaRequest {
        @NotBlank(message = "O nome da categoria é obrigatório")
        private String nome;

        public CategoriaRequest() {}
        public CategoriaRequest(String nome) { this.nome = nome; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    public static class LivroRequest {
        @NotBlank(message = "O título é obrigatório")
        private String titulo;

        @NotBlank(message = "O ISBN é obrigatório")
        private String isbn;

        @NotNull(message = "O ID da categoria é obrigatório")
        private Long categoriaId;

        public LivroRequest() {}
        public LivroRequest(String titulo, String isbn, Long categoriaId) {
            this.titulo = titulo; this.isbn = isbn; this.categoriaId = categoriaId;
        }
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public Long getCategoriaId() { return categoriaId; }
        public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    }

    // ----- RESPONSE DTOs -----

    public static class CategoriaResponse {
        private Long id;
        private String nome;
        private long totalLivros;

        public CategoriaResponse() {}
        public CategoriaResponse(Long id, String nome, long totalLivros) {
            this.id = id; this.nome = nome; this.totalLivros = totalLivros;
        }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public long getTotalLivros() { return totalLivros; }
        public void setTotalLivros(long totalLivros) { this.totalLivros = totalLivros; }
    }

    public static class CategoriaSummary {
        private Long id;
        private String nome;

        public CategoriaSummary() {}
        public CategoriaSummary(Long id, String nome) { this.id = id; this.nome = nome; }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    public static class LivroResponse {
        private Long id;
        private String titulo;
        private String isbn;
        private CategoriaSummary categoria;

        public LivroResponse() {}
        public LivroResponse(Long id, String titulo, String isbn, CategoriaSummary categoria) {
            this.id = id; this.titulo = titulo; this.isbn = isbn; this.categoria = categoria;
        }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public CategoriaSummary getCategoria() { return categoria; }
        public void setCategoria(CategoriaSummary categoria) { this.categoria = categoria; }
    }
}
