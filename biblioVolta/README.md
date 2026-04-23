# BiblioVolta — Sistema de Gestão de Empréstimos de Livros

Aplicação web para gerenciar o acervo e os empréstimos de livros em uma biblioteca universitária.

## Tecnologias

- Java 17
- Spring Boot 4
- MongoDB Atlas (banco de dados na nuvem)
- Thymeleaf (templates HTML)
- Maven
- bcrypt
- spring security
---

## Pré-requisitos

- JDK 17 ou superior instalado
- Maven 3.8+ instalado (ou usar o `mvnw` incluído no projeto)
- Uma conta no [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) com um cluster criado

---

## Configuração do banco de dados

1. Crie um cluster gratuito no MongoDB Atlas.
2. Crie um usuário de banco de dados com permissão de leitura e escrita.
3. Libere o seu IP nas configurações de rede do cluster (Network Access).
4. Copie a connection string do seu cluster. Ela terá o formato:

```
mongodb+srv://<usuario>:<senha>@<cluster>.mongodb.net/<banco>?retryWrites=true&w=majority
```

5. Abra o arquivo `src/main/resources/application.properties` e substitua o valor da propriedade:

```properties
spring.data.mongodb.uri=mongodb+srv://<usuario>:<senha>@<cluster>.mongodb.net/biblioteca?retryWrites=true&w=majority&appName=<appName>
```

> O banco `biblioteca` será criado automaticamente na primeira execução.

---

## Como executar

Clone o repositório e entre na pasta do projeto:

```bash
git clone <url-do-repositorio>
cd biblioVolta
```

Execute com o Maven Wrapper (não precisa ter Maven instalado globalmente):

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

## Primeiro acesso

Na primeira execução, um usuário administrador padrão é criado automaticamente:

| Campo | Valor    |
|-------|----------|
| Login | `admin`  |
| Senha | `admin123` |

> Recomenda-se alterar a senha após o primeiro acesso.

---

## Funcionalidades

- **Login** com autenticação por sessão e senha hasheada com BCrypt
- **Dashboard** com resumo do acervo (total, disponíveis, emprestados) e filtros por status
- **Cadastro de livros** (título, autor, ISBN)
- **Cadastro de alunos** (nome, RGM, curso)
- **Registro de empréstimo** — vincula o livro ao aluno pelo RGM com data de devolução prevista
- **Registro de devolução** — marca o livro como devolvido
- **Busca** por título do livro

---

## Estrutura do projeto

```
src/main/java/com/biblioteca/gestao/
├── controller/
│   ├── AuthController.java        # Login e logout
│   ├── AlunoController.java       # Cadastro e busca de alunos
│   ├── LivroController.java       # Dashboard, livros, empréstimos
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Aluno.java
│   ├── Livro.java
│   ├── Usuario.java
│   └── StatusEmprestimo.java      # Enum: DISPONIVEL, EMPRESTADO, DEVOLVIDO
├── repository/                    # Interfaces Spring Data MongoDB
├── service/                       # Regras de negócio
└── DataInitializer.java           # Cria usuário admin na inicialização

src/main/resources/
├── templates/                     # Páginas HTML (Thymeleaf)
└── application.properties         # Configurações da aplicação
```
