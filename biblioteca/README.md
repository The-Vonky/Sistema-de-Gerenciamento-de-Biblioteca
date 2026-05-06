# 📚 Sistema de Gerenciamento de Biblioteca
### API REST — Spring Boot + Spring Data JPA + H2

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2.5 | Framework base |
| Spring Data JPA | 3.2.5 | Persistência e Query Methods |
| Hibernate | 6.x | Implementação JPA |
| H2 Database | runtime | Banco em memória |
| Lombok | latest | Redução de boilerplate |
| Maven | 3.x | Gerenciamento de dependências |

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Passos

```bash
# 1. Clone ou extraia o projeto
cd sistema-biblioteca

# 2. Compile e execute
mvn spring-boot:run

# 3. Acesse a API
# API:        http://localhost:8080/api/livros
# H2 Console: http://localhost:8080/h2-console
```

### Acessar o H2 Console
Abra no navegador: **http://localhost:8080/h2-console**

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:bibliotecadb` |
| Username | `sa` |
| Password | *(em branco)* |

Tabelas que devem aparecer: **TB_LIVRO** e **TB_CATEGORIA** ✅

---

## 🗂️ Estrutura do Projeto

```
src/main/java/com/biblioteca/
├── BibliotecaApplication.java      ← Classe principal (@SpringBootApplication)
├── model/
│   ├── Categoria.java              ← Entidade JPA → tb_categoria
│   └── Livro.java                  ← Entidade JPA → tb_livro
├── repository/
│   ├── CategoriaRepository.java    ← JpaRepository + Query Methods
│   └── LivroRepository.java        ← JpaRepository + Query Methods (Etapa 2)
├── service/
│   ├── CategoriaService.java       ← Lógica de negócio de categorias
│   └── LivroService.java           ← Lógica de negócio de livros
├── controller/
│   ├── CategoriaController.java    ← Endpoints REST /api/categorias
│   ├── LivroController.java        ← Endpoints REST /api/livros
│   └── GlobalExceptionHandler.java ← Tratamento global de erros
└── dto/
    └── DTOs.java                   ← Request/Response objects

src/main/resources/
├── application.yml                 ← Configuração H2, JPA, servidor
└── data.sql                        ← Dados iniciais (16 livros, 6 categorias)
```

---

## 🗄️ Modelo de Dados

### Relacionamento: Categoria (1) ←→ (N) Livro

```
tb_categoria                    tb_livro
┌─────────────────┐            ┌──────────────────────────────┐
│ id     BIGINT PK│◄───┐       │ id           BIGINT PK       │
│ nome   VARCHAR  │    └───────│ categoria_id BIGINT FK (NN)  │
└─────────────────┘            │ titulo       VARCHAR (NN)    │
                               │ isbn         VARCHAR (UQ,NN) │
                               └──────────────────────────────┘
```

### Anotações JPA utilizadas

```java
// Categoria.java
@Entity           // marca como entidade JPA
@Table            // define nome da tabela
@Id               // chave primária
@GeneratedValue   // auto-incremento
@Column           // configuração da coluna
@OneToMany        // lado "um" do relacionamento

// Livro.java
@Entity
@Table
@Id
@GeneratedValue
@Column
@ManyToOne        // lado "muitos" — obrigatório
@JoinColumn       // define a FK (categoria_id)
```

---

## 📋 Etapa 1 — Entidades e Repositórios

### Categoria.java
```java
@Entity
@Table(name = "tb_categoria")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros;
}
```

### Livro.java
```java
@Entity
@Table(name = "tb_livro")
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
```

---

## 🔍 Etapa 2 — Query Methods (LivroRepository)

### 3 métodos obrigatórios + bônus

```java
// 1. Busca por título EXATO
Optional<Livro> findByTitulo(String titulo);

// 2. Busca por PARTE do título (case-insensitive)
List<Livro> findByTituloContainingIgnoreCase(String titulo);

// 3. Busca por categoria específica (objeto Categoria)
List<Livro> findByCategoria(Categoria categoria);

// BÔNUS — por ID da categoria
List<Livro> findByCategoriaId(Long categoriaId);

// BÔNUS — por ISBN
Optional<Livro> findByIsbn(String isbn);

// BÔNUS — JPQL customizada por nome da categoria
@Query("SELECT l FROM Livro l WHERE LOWER(l.categoria.nome) = LOWER(:nomeCategoria)")
List<Livro> findByNomeCategoria(@Param("nomeCategoria") String nomeCategoria);
```

---

## 🌐 Endpoints da API

### Categorias — `/api/categorias`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/categorias` | Lista todas as categorias |
| GET | `/api/categorias/{id}` | Busca categoria por ID |
| POST | `/api/categorias` | Cria nova categoria |
| PUT | `/api/categorias/{id}` | Atualiza categoria |
| DELETE | `/api/categorias/{id}` | Remove categoria |

### Livros — `/api/livros`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/livros` | Lista todos os livros |
| GET | `/api/livros/{id}` | Busca livro por ID |
| POST | `/api/livros` | Cria novo livro |
| PUT | `/api/livros/{id}` | Atualiza livro |
| DELETE | `/api/livros/{id}` | Remove livro |

### Buscas — Etapa 2

| Método | URL | Query Method |
|--------|-----|-------------|
| GET | `/api/livros/buscar/titulo-exato?titulo=Duna` | `findByTitulo` |
| GET | `/api/livros/buscar/titulo?titulo=clean` | `findByTituloContainingIgnoreCase` |
| GET | `/api/livros/buscar/categoria/3` | `findByCategoriaId` |
| GET | `/api/livros/buscar/categoria/nome?nome=Tecnologia` | `findByNomeCategoria` (JPQL) |
| GET | `/api/livros/buscar/isbn?isbn=978-0132350884` | `findByIsbn` |

---

## 📦 Exemplos de Requisições

### Criar uma Categoria
```http
POST http://localhost:8080/api/categorias
Content-Type: application/json

{
  "nome": "Autoajuda"
}
```

### Criar um Livro
```http
POST http://localhost:8080/api/livros
Content-Type: application/json

{
  "titulo": "O Problema dos Três Corpos",
  "isbn": "978-8580442823",
  "categoriaId": 1
}
```

### Buscar por parte do título
```http
GET http://localhost:8080/api/livros/buscar/titulo?titulo=clean
```

**Resposta:**
```json
[
  {
    "id": 8,
    "titulo": "Clean Code",
    "isbn": "978-0132350884",
    "categoria": {
      "id": 3,
      "nome": "Tecnologia"
    }
  }
]
```

### Buscar por categoria
```http
GET http://localhost:8080/api/livros/buscar/categoria/3
```

**Resposta:**
```json
[
  { "id": 8,  "titulo": "Clean Code",              "isbn": "978-0132350884", "categoria": { "id": 3, "nome": "Tecnologia" } },
  { "id": 9,  "titulo": "The Pragmatic Programmer", "isbn": "978-0135957059", "categoria": { "id": 3, "nome": "Tecnologia" } },
  { "id": 10, "titulo": "Design Patterns",          "isbn": "978-0201633610", "categoria": { "id": 3, "nome": "Tecnologia" } },
  { "id": 11, "titulo": "Spring Boot in Action",    "isbn": "978-1617292545", "categoria": { "id": 3, "nome": "Tecnologia" } }
]
```

---

## 📬 Insomnia

Importe o arquivo **`insomnia-biblioteca.json`** no Insomnia:

1. Abra o Insomnia
2. Clique em **Import** (ícone de seta para baixo)
3. Selecione o arquivo `insomnia-biblioteca.json`
4. Todas as requisições estarão organizadas em pastas

---

## 🗃️ Dados Iniciais (data.sql)

O banco é populado automaticamente ao iniciar a aplicação:

**6 Categorias:** Ficção Científica, Romance, Tecnologia, História, Filosofia, Biografia

**16 Livros pré-cadastrados**, incluindo:
- Duna, Fundação, Neuromancer (Ficção Científica)
- Clean Code, Design Patterns, Spring Boot in Action (Tecnologia)
- Sapiens, Dom Casmurro, Steve Jobs... e mais!

---

## ✅ Critérios de Sucesso

| Critério | Status |
|----------|--------|
| Tabelas `tb_livro` e `tb_categoria` no H2 Console | ✅ |
| Relacionamento 1:N mapeado corretamente | ✅ |
| CategoriaRepository estende JpaRepository | ✅ |
| LivroRepository estende JpaRepository | ✅ |
| Busca por título exato | ✅ `findByTitulo` |
| Busca por parte do título (case-insensitive) | ✅ `findByTituloContainingIgnoreCase` |
| Busca por categoria específica | ✅ `findByCategoriaId` |
| Arquivo Insomnia exportado | ✅ `insomnia-biblioteca.json` |
| Banco populado automaticamente | ✅ `data.sql` |
