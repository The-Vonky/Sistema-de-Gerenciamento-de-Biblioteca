package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ============================================================
// DTOs (Data Transfer Objects)
// Separam a camada de transporte (JSON) das entidades JPA,
// evitando problemas de serialização circular (Livro <-> Categoria)
// ============================================================

public class DTOs {

    // ----- REQUEST DTOs (entrada) -----

    /** Payload para criar/atualizar uma Categoria */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaRequest {
        @NotBlank(message = "O nome da categoria é obrigatório")
        private String nome;
    }

    /** Payload para criar/atualizar um Livro */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LivroRequest {
        @NotBlank(message = "O título é obrigatório")
        private String titulo;

        @NotBlank(message = "O ISBN é obrigatório")
        private String isbn;

        @NotNull(message = "O ID da categoria é obrigatório")
        private Long categoriaId;
    }

    // ----- RESPONSE DTOs (saída) -----

    /** Representação de Categoria retornada ao cliente */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaResponse {
        private Long id;
        private String nome;
        private long totalLivros;
    }

    /** Representação simplificada de Categoria (usada dentro de LivroResponse) */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaSummary {
        private Long id;
        private String nome;
    }

    /** Representação de Livro retornada ao cliente */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LivroResponse {
        private Long id;
        private String titulo;
        private String isbn;
        private CategoriaSummary categoria;
    }
}
