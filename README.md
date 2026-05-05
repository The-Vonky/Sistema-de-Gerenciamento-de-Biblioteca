# Sistema-de-Gerenciamento-de-Biblioteca (API REST):

Objetivo: Desenvolver o módulo de persistência e consulta de uma biblioteca utilizando Java, Spring Boot e Spring Data JPA. 

---

## Contexto do Projeto:
Criar uma API que permita o cadastro de Livros e sua organização por Categorias. Um livro deve obrigatoriamente pertencer a uma categoria (Relacionamento 1:N).

---

### Etapa 1: Estrutura e Mapeamento (A- 14/05) 14 pontos
Nesta fase inicial, o foco é a configuração do ambiente e a criação das classes que representarão as tabelas no banco de dados.

O que deve ser entregue:
- Configuração: Arquivo application.yml ou application.properties configurado para usar o banco de dados H2 (em memória) com o console ativo.
- Entidades (Models):
- Categoria: Com os atributos id (Long) e nome (String).
- Livro: Com os atributos id (Long), titulo (String), isbn (String) e o relacionamento com a classe Categoria.
- Mapeamento JPA: Uso correto das anotações (@Entity, @Id, @GeneratedValue, @Column, @ManyToOne).
- Repositórios: Interfaces CategoriaRepository e LivroRepository estendendo JpaRepository.

Critério de Sucesso: O projeto deve iniciar sem erros e as tabelas tb_livro e tb_categoria devem aparecer corretamente no H2 Console (localhost:8080/h2-console).

---

### Etapa 2: Consultas e Testes (A- 11/06) 7 pontos
Nesta fase final, vocês implementarão a lógica de busca e testarão a aplicação como se fosse um sistema real.

O que deve ser entregue:
Consultas (Query Methods): No repositório de Livros, implementar pelo menos 3 métodos de busca por nome:
- Busca por título exato.
- Busca por parte do título (ignorando maiúsculas/minúsculas).
- Busca de livros por uma categoria específica.
- Coleção do Insomnia: Exportar o arquivo de projeto do Insomnia contendo as requisições (Requests) criadas para testar cada funcionalidade da sua API (incluindo requisições iniciais para popular o seu banco de dados se necessário, ou garanta que o banco seja populado inicialmente de alguma outra forma).

Critério de Sucesso: Ser possível importar o seu arquivo do Insomnia, disparar uma busca e receber a lista de livros no formato JSON com sucesso

---
