# 🧭 Sistema de Gestão de OKRs

Projeto desenvolvido para a disciplina de **Programação de Sistemas II**, com foco na criação de uma aplicação full-stack para gerenciar **OKRs (Objectives and Key Results)**.

## 👨‍💻 Desenvolvedores

- Gabriel Labarca Del Bianco  
- Gustavo Netto de Carvalho  
- Caio Caramés Lanzelotti da Silva  

---

## 🛠️ Etapas do Desenvolvimento
#### 🗄️ Banco de Dados com Supabase

O projeto utiliza o [Supabase](https://supabase.com/) como provedor de banco de dados. Ele oferece uma instância de PostgreSQL gerenciado na nuvem, à qual o back-end em Spring Boot se conecta via JDBC.

A string de conexão está configurada no arquivo `application.properties`, contendo:
- A URL do banco (`jdbc:postgresql://<host>:<porta>/<database>`)
- Usuário e senha fornecidos pelo Supabase

Isso nos permitiu utilizar todas as vantagens do PostgreSQL sem necessidade de hospedar o banco manualmente.

### ⚙️ Back-end

#### 📌 1. Modelagem das Entidades

Definimos as entidades principais: `Objetivo`, `ResultadoChave` e `Iniciativa`. Cada uma foi modelada com JPA para refletir as relações entre os dados no banco.

📷 *Exemplo de modelagem:*
<img src="https://github.com/user-attachments/assets/80023ad2-e7bf-433e-9b6b-cf9de23b2a49" width="900px"/>



---

#### 📌 2. Criação das Classes de Serviço

Para cada entidade, criamos uma **classe de serviço** contendo a lógica de negócio. Essas classes foram responsáveis por intermediar os dados entre o controller e o repositório.

📷 *Exemplo de classe de serviço:*
<img src="https://github.com/user-attachments/assets/69168a63-d7b8-4857-ab8e-c1735e2fffb6" width="900px"/>


---

#### 📌 3. Criação dos Controllers e DTOs

Criamos as classes `Controller`, que expõem **endpoints REST** para as operações CRUD. Também implementamos **DTOs (Data Transfer Objects)** para manipular os dados com segurança, sem afetar diretamente as entidades.

📷 *Exemplo de Controller:*
<img src="https://github.com/user-attachments/assets/d78f5c89-f131-4e6b-9f1b-d9ef08062583" width="900px"/>

📷 *Exemplo de DTO:*
<img src="https://github.com/user-attachments/assets/86185ebd-84d4-4b19-9f73-0c1b00788fd1" width="900px"/>


---

#### 📌 4. Teste dos Endpoints com Thunder Client

Usamos o **Thunder Client**, extensão do VS Code, para testar todos os endpoints da API. Verificamos o correto funcionamento dos métodos GET, POST, PUT e DELETE.

📷 *Exemplo de teste com Thunder Client (GET):*
<img src="https://github.com/user-attachments/assets/5b9ad81d-0a1e-4330-88d5-1c7456a349d6" width="900px"/>


📷 *Exemplo de teste com Thunder Client (POST):*
<img src="https://github.com/user-attachments/assets/e44d4202-ee20-45a5-8122-f73c02263149" width="900px"/>


📷 *Exemplo de teste com Thunder Client (PUT):*
<img src="https://github.com/user-attachments/assets/7d186de8-0660-46d6-b9e9-13d98b0c2a47" width="900px"/>


📷 *Exemplo de teste com Thunder Client (Delete):*
<img src="https://github.com/user-attachments/assets/616e7b36-6fbe-44f9-9b37-cd3b42ad71d9" width="900px"/>


### 💻 Front-end

#### 📌 1. Organização das Páginas

Estruturamos o front-end usando **Next.js**, separando as páginas em pastas organizadas por funcionalidades. Isso facilita a manutenção e a escalabilidade do projeto.

📷 *Estrutura de páginas:*

<img src="https://github.com/user-attachments/assets/ddba0607-6948-41ad-b83e-6edadc11afa9" width="900px"/>

---

#### 📌 2. Integração com a API

Criamos funções para **consumir os endpoints do back-end**, permitindo ao usuário interagir com os dados em tempo real (criar, listar, editar e deletar OKRs).

📷 *Exemplo de consumo de API:*

<img src="https://github.com/user-attachments/assets/20161bd3-e718-411b-b5fe-3b59acd32575" width="900px"/>

---

#### 📌 3. Testes da Interface

Realizamos testes no site para verificar a navegação entre páginas, a atualização de dados e a exibição correta das informações.

📷 *Exemplo de tela principal:*

<img src="https://github.com/user-attachments/assets/15fb5740-6910-434b-abbb-d282e54fbfa3" width="900px"/>

📷 *Exemplo de tela de cadastro:*

<img src="https://github.com/user-attachments/assets/ab7691fa-ec89-4b9d-a7bb-f720991de442" width="900px"/>

📷 *Exemplo de tela de atualização:*

<img src="https://github.com/user-attachments/assets/79bae163-b624-40a6-a106-f095add8b708" width="900px"/>

## 🧠 Aprendizados

Este projeto foi uma excelente oportunidade para:
- Praticar modelagem de entidades com JPA
- Criar APIs REST com Spring Boot
- Integrar back-end com front-end usando Next.js
- Colaborar em equipe usando Git e GitHub
- Documentar e testar APIs de forma prática

---

## 🚀 Como Executar

### Requisitos
- Java 17+
- Node.js 16+
- Supabase (como banco de dados PostgreSQL)
- Git
