-- ============================================
-- Script de população inicial do banco de dados
-- Sistema de Gerenciamento de Biblioteca
-- ============================================

-- Inserção de Categorias
INSERT INTO tb_categoria (nome) VALUES ('Ficção Científica');
INSERT INTO tb_categoria (nome) VALUES ('Romance');
INSERT INTO tb_categoria (nome) VALUES ('Tecnologia');
INSERT INTO tb_categoria (nome) VALUES ('História');
INSERT INTO tb_categoria (nome) VALUES ('Filosofia');
INSERT INTO tb_categoria (nome) VALUES ('Biografia');

-- Inserção de Livros (categoria_id referencia tb_categoria)
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Duna', '978-8576570400', 1);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Fundação', '978-8576572619', 1);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('O Guia do Mochileiro das Galáxias', '978-8599296004', 1);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Neuromancer', '978-8576574118', 1);

INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Dom Casmurro', '978-8508130047', 2);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Orgulho e Preconceito', '978-8544001820', 2);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('O Amor nos Tempos do Cólera', '978-8501400710', 2);

INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Clean Code', '978-0132350884', 3);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('The Pragmatic Programmer', '978-0135957059', 3);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Design Patterns', '978-0201633610', 3);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Spring Boot in Action', '978-1617292545', 3);

INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Sapiens', '978-8535919851', 4);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Uma Breve História do Tempo', '978-8580576962', 4);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('O Mundo de Sofia', '978-8522502318', 5);

INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Steve Jobs', '978-8535919479', 6);
INSERT INTO tb_livro (titulo, isbn, categoria_id) VALUES ('Elon Musk', '978-8551010587', 6);
