package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal do Sistema de Gerenciamento de Biblioteca.
 *
 * Após iniciar, acesse:
 *   API:        http://localhost:8080/api/livros
 *   H2 Console: http://localhost:8080/h2-console
 *     - JDBC URL:  jdbc:h2:mem:bibliotecadb
 *     - Username:  sa
 *     - Password:  (em branco)
 */
@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }
}
