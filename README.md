# 🧭 Sistema de Gestão de OKRs

Projeto desenvolvido para a disciplina de **Programação de Sistemas II**, com foco na criação de uma aplicação full-stack para gerenciar **OKRs (Objectives and Key Results)**.

## 👨‍💻 Desenvolvedores

- Gabriel Labarca Del Bianco  
- [Gustavo Netto de Carvalho](https://github.com/gustavonc05)
- [Caio Caramés Lanzelotti da Silva](https://github.com/caiocarames)

---

## 🛠️ Etapas do Desenvolvimento
### 🗄️ Banco de Dados com Supabase

O projeto utiliza o [Supabase](https://supabase.com/) como provedor de banco de dados. Ele oferece uma instância de PostgreSQL gerenciado na nuvem, à qual o back-end em Spring Boot se conecta via JDBC.

A string de conexão está configurada no arquivo `application.properties`, contendo:
- A URL do banco (`jdbc:postgresql://<host>:<porta>/<database>`)
- Usuário e senha fornecidos pelo Supabase

Isso nos permitiu utilizar todas as vantagens do PostgreSQL sem necessidade de hospedar o banco manualmente.

![image](https://github.com/user-attachments/assets/c532b070-3cf0-4b0a-8890-f0c77a99dc53)

### ⚙️ Back-end

#### 📌 1. Modelagem das Entidades

Definimos as entidades principais: `Objetivo`, `ResultadoChave` e `Iniciativa`. Cada uma foi modelada com JPA para refletir as relações entre os dados no banco.

 *Exemplo de modelagem:*
```java
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa um objetivo que está ligado a vários resultados chaves (OKRs).
 * Contém informações como identificador único (id), título, descrição e
 * porcentagem de conclusão, que indica o progresso geral do objetivo de 0.0 a 100.0.
 * 
 * Um objetivo pode conter múltiplos resultados chave, que são armazenados em uma lista,
 * permitindo o acompanhamento e cálculo do progresso total do objetivo com base
 * nas porcentagens de conclusão de cada resultado chave associado.
 */
@Entity
@Table(name = "Objetivos")
public class Objetivo {
    /** Identificador único do objetivo */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id_objetivo;

    /** Título do objetivo */
    private String titulo;

    /** Descrição do objetivo */
    private String descricao;

    /**
     * Porcentagem da conclusão do objetivo, calculada a partir da média
     * das porcentagens de conclusão dos resultados chave relacionados.
     */
    private double porcentagem_conclusao_obj;

    /**
     * Lista de resultados chave associados a este objetivo.
     * Um objetivo pode ter vários resultados chave (KRs).
     */
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.ALL) 
    private List<ResultadosChave> resultadosChaves; 

    /**
     * Construtor padrão necessário para o funcionamento do framework Spring.
     */
    public Objetivo() {
    }
```
---

#### 📌 2. Criação das Classes de Serviço

Para cada entidade, criamos uma **classe de serviço** contendo a lógica de negócio. Essas classes foram responsáveis por intermediar os dados entre o controller e o repositório.

 *Exemplo de classe de serviço:*
```java
import com.example.demo.backend.Interfaces.ObjetivoRepository;
import com.example.demo.backend.Interfaces.ResultadosChaveRepository;

/**
 * Serviço responsável por implementar a lógica de negócio relacionada aos Objetivos.
 * 
 * Essa classe atua como intermediária entre a Controller e os repositórios, realizando operações
 * como criação, leitura, atualização, exclusão de objetivos e gerenciamento das associações
 * com os Resultados Chave.
 * 
 * Realiza a conversão entre DTOs e entidades, mantendo a integridade dos dados.
 */

// Essa classe vai ter regras de negocio, então devemos avisar pro Spring (usar a anotacao "Service")
@Service
public class ObjetivoService {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Autowired
    private ResultadosChaveRepository resultadosChaveRepository;

    /**
     * Construtor que injeta as dependências dos repositórios de Objetivos e Resultados Chave.
     * 
     * @param objetivoRepository Repositório que contém as operações CRUD de Iniciativas.
     * @param resultadosChaveRepository Repositório que contém as operações CRUD de Resultados Chave
     */
    public ObjetivoService(ObjetivoRepository objetivoRepository, ResultadosChaveRepository resultadosChaveRepository){
        this.objetivoRepository = objetivoRepository;
        this.resultadosChaveRepository = resultadosChaveRepository;
    }

    /**
     * Cria um novo objetivo a partir dos dados recebidos no DTO.
     * 
     * @param dto Objeto contendo os dados do objetivo a ser criado.
     * @return A entidade Objetivo salva no banco de dados.
     */
    // Aqui o dto é juntado tudo em um objeto armazenado na variável "entity"
    public Objetivo createObjetivo(CreateObjetivoDTO dto){
        var entity = new Objetivo(
            dto.getTitulo(),
            dto.getDescricao(),
            dto.getPorcentagem()
        );
        entity.setPorcentagem_conclusao_obj(0);
        return objetivoRepository.save(entity);
    }
```

---

#### 📌 3. Criação dos Controllers e DTOs

Criamos as classes `Controller`, que expõem **endpoints REST** para as operações CRUD. Também implementamos **DTOs (Data Transfer Objects)** para manipular os dados com segurança, sem afetar diretamente as entidades.

 *Exemplo de Controller:*
```java
// Serve como o porteiro, tudo que vem do front passa primeiro por aqui
// Trata as requisições HTTP por aqui
    // A controller vai precisar chamar primeiramente a Service para depois a service vai chamar o banco de dados
    // Controller -> Service -> DB
@CrossOrigin(origins = "http://localhost:3000") // Front ta rodando na porta 3000
@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {
    private ObjetivoService objetivoService;

    /**
     * Construtor que injeta a dependência do serviço de objetivos.
     * 
     * @param objetivoService Serviço responsável pela lógica dos objetivos.
     */
    public ObjetivoController(ObjetivoService objetivoService){
        this.objetivoService = objetivoService;
    }

    /**
     * Endpoint para criar um novo objetivo ("/objetivos").
     * 
     * @param createObjetivoDTO Objeto contendo os dados do objetivo a ser criado.
     * @return Resposta HTTP com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Objetivo> createObjetivo(@RequestBody CreateObjetivoDTO createObjetivoDTO){
        objetivoService.createObjetivo(createObjetivoDTO); 
        return ResponseEntity.created(URI.create("/objetivos")).build(); 
    }
```

 *Exemplo de DTO:*
```java
import java.util.ArrayList;
import java.util.List;

/**
 * DTO (Data Transfer Object) usado para criação de um novo Objetivo via requisição HTTP.
 * 
 * Essa classe serve como um objeto exclusivamente para uso na requisição HTTP.
 * Ela permite que mudanças no banco de dados ou nas entidades não quebrem diretamente a API.
 * 
 * Contém informações essenciais como título, descrição, porcentagem de conclusão
 * e uma lista de IDs dos resultados chave associados a esse objetivo.
 * 
 */
public class CreateObjetivoDTO {
    
    /** Título do objetivo a ser criado */
    private String titulo;
    
    /** Descrição detalhada do objetivo */
    private String descricao;
    
    /** Porcentagem de conclusão do objetivo (0.0 a 100.0) */
    private double porcentagem_conclusao_obj;
    
    /** Lista com IDs dos resultados chave associados a este objetivo */
    private List<Long> resultadosChaves;

    /**
     * Construtor padrão sem parâmetros.
     */
    public CreateObjetivoDTO() {
        this.resultadosChaves = new ArrayList<>();
    }

    /**
     * Construtor com parâmetros para facilitar a criação do DTO.
     * 
     * @param titulo Título do objetivo.
     * @param descricao Descrição do objetivo.
     * @param porcentagem_conclusao_obj Porcentagem de conclusão do objetivo.
     */
    public CreateObjetivoDTO(String titulo, String descricao, double porcentagem_conclusao_obj) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.porcentagem_conclusao_obj = porcentagem_conclusao_obj;
        this.resultadosChaves = new ArrayList<>();
    }
```


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

<img src="https://github.com/user-attachments/assets/ddba0607-6948-41ad-b83e-6edadc11afa9" width="700px"/>

---

#### 📌 2. Integração com a API

Criamos funções para **consumir os endpoints do back-end**, permitindo ao usuário interagir com os dados em tempo real (criar, listar, editar e deletar OKRs).

📷 *Exemplo de consumo de API:*
```javascript
"use client"
import Link from "next/link";
import styles from "./PaginaObjetivos.module.css";
import { useEffect, useState } from "react";

export default function PaginaObjetivos() {
  // Criação do estado para armazenar os Objetivos
  const [objetivos, setObjetivos] = useState([]);
  const [selectedId, setSelectedId] = useState("Todos");

  // Função para chamar a API e buscar os Objetivos
  async function chamarAPIObjetivo() {
    const URL = "http://localhost:8080/objetivos";
    try {
      const resp = await fetch(URL);
      if (resp.status === 200) {
        const data = await resp.json();
        console.log("Objetivos recebidos:", data);
        setObjetivos(data);
      } else {
        console.error('Erro ao buscar objetivos:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Função para dar o delete de algum objetivo 
  async function chamarAPIDeleteObjetivo(id) { // Precisa receber como parâmetro qual id o usuário quer apagar
    const URL = `http://localhost:8080/objetivos/${id}`;
    try {
      const resp = await fetch(URL, {
        method: "DELETE",
      });

      if (resp.ok) {
        alert("Objetivo deletado com sucesso!");
        chamarAPIObjetivo(); // da o get novamente para recarregar a página
      } else {
        console.error('Erro ao dar delete de um objetivo:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }
```


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
