
# HelpMeNow

Sistema de chamados técnicos desenvolvido com Spring Boot e Thymeleaf. Idealizado para auxiliar na organização e gerenciamento de solicitações de suporte dentro de empresas ou instituições.

## 💡 Visão Geral

O **HelpMeNow** é uma aplicação web que permite aos usuários cadastrarem chamados técnicos (tickets), visualizarem seu status e acompanharem o andamento. Administradores e técnicos podem gerenciar os chamados, atribuir responsáveis e atualizar os status conforme o progresso.

## 🚀 Funcionalidades

- Login com diferentes perfis: Administrador e Cliente
- Abertura de chamados técnicos com título, descrição e prioridade
- Acompanhamento do status dos chamados (Aberto, Em Andamento, Resolvido)
- Comentários nos chamados, com registro do autor e data/hora
- Administração de usuários (criação, edição, ativação/inativação)
- Gerenciamento de departamentos com nome, ramal e localização
- Interface limpa e responsiva usando Bootstrap e Thymeleaf

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.4**
- **Spring MVC**
- **Spring Security**
- **Thymeleaf**
- **H2 Database** (memória)
- **Maven**
- **Bootstrap 5**
- **HTML5 / CSS / JS**

## 🗂️ Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.br.helpmenow
│   │       ├── config         # Configurações globais do projeto (ex: inicialização de dados)
│   │       ├── controller     # Controllers que recebem e tratam as requisições
│   │       ├── dto            # Objetos de transferência de dados entre camadas
│   │       ├── model          # Entidades (JPA) representando as tabelas do banco
│   │       ├── repository     # Interfaces de acesso a dados (Spring Data JPA)
│   │       ├── security       # Configurações e classes de autenticação/autorização
│   │       └── service        # Regras de negócio do sistema
│   └── resources
│       ├── static             # Arquivos estáticos (CSS, JS, ícones)
│       ├── templates          # Páginas HTML com Thymeleaf
│       └── application.properties  # Configurações da aplicação
└── test
```

## ⚙️ Como rodar localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/victor-delmondes/helpmenow.git
   ```
2. Abra o projeto em sua IDE (preferencialmente IntelliJ)
3. Rode a aplicação a partir da classe `HelpmenowApplication.java`
4. Acesse no navegador: `http://localhost:8080`

> O banco H2 é em memória e já vem configurado no projeto.

## 🔐 Acesso

- Login padrão:
  - **Admin:** `admin@exemple.com` / `123456`
  - **Client:** `carlos@empresa.com` / `123456`
  - **Client:** `fernanda@empresa.com` / `123456`
  - (Dados fictícios pré-cadastrados no `DataInitializer`)

## 📄 Licença

Este projeto é acadêmico e não possui uma licença definida. Sinta-se livre para estudar, modificar e adaptar conforme necessário.
