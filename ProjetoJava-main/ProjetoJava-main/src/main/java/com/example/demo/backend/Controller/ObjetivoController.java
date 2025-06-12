package com.example.demo.backend.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.backend.Entidades.Objetivo;
import com.example.demo.backend.Service.ObjetivoService;


/**
 * Controller responsável por tratar as requisições HTTP relacionadas aos objetivos.
 * 
 * Essa classe atua como intermediária entre o front-end e a classe {@link ObjetivoService},
 * recebendo as requisições HTTP, delegando a lógica de negócio para a service e retornando as respostas apropriadas.
 * 
 * As operações disponíveis são:
 * - Criar um novo objetivo (POST)
 * - Buscar objetivo por ID (GET)
 * - Listar todos os objetivos (GET)
 * - Deletar objetivo por ID (DELETE)
 * - Atualizar objetivo por ID (PUT)
 * - Desassociar um Resultado Chave de um objetivo (DELETE)
 * 
 * Segue o padrão REST, utilizando as anotações do Spring Boot para mapear
 * os endpoints no caminho "/objetivos".
 * 
 * Fluxo geral: Controller -> Service -> Banco de Dados
 */

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

    /**
     * Endpoint para obter um objetivo pelo seu ID ("/{id_objetivo}").
     * 
     * @param id_objetivo ID do objetivo a ser buscado.
     * @return Resposta HTTP com status 200 OK e o objetivo, ou 404 Not Found caso não exista.
     */
    @GetMapping("/{id_objetivo}")
    public ResponseEntity<Objetivo> getObjetivo(@PathVariable("id_objetivo") long id_objetivo){
        Optional<Objetivo> objetivo = objetivoService.getObjetivo(id_objetivo);
        if (objetivo.isPresent()){
            return ResponseEntity.ok(objetivo.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para listar todos os objetivos cadastrados ("/objetivos").
     * 
     * @return Resposta HTTP com status 200 OK e lista de objetivos.
     */
    @GetMapping
    public ResponseEntity<List<Objetivo>> listarObjetivos(){
        var objetivos = objetivoService.listarObjetivos();
        return ResponseEntity.ok(objetivos);
    }

    /**
     * Endpoint para deletar um objetivo pelo seu ID ("/{id_objetivo}").
     * 
     * @param id_objetivo ID do objetivo a ser deletado.
     * @return Resposta HTTP com status 204 No Content.
     */
    @DeleteMapping("/{id_objetivo}")
    public ResponseEntity<Void> deleteById(@PathVariable("id_objetivo") long id_objetivo){
        objetivoService.deleteById(id_objetivo);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para atualizar os dados de um objetivo existente pelo ID ("/{id_objetivo}").
     * 
     * @param id_objetivo ID do objetivo a ser atualizado.
     * @param dto Objeto contendo os novos dados do objetivo.
     * @return Resposta HTTP com status 200 OK e mensagem de sucesso, ou 404 Not Found caso não exista.
     */
    @PutMapping("/{id_objetivo}")
    public ResponseEntity<String> updateById(@PathVariable("id_objetivo") long id_objetivo,@RequestBody CreateObjetivoDTO dto) {
        Optional<Objetivo> objetivoExistente = objetivoService.getObjetivo(id_objetivo);

        if (objetivoExistente.isPresent()) {
            objetivoService.atualizarById(id_objetivo, dto);
            return ResponseEntity.ok("Objetivo atualizado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para desassociar um Resultado Chave de um objetivo ("/{id_objetivo}/disassociarKR/{id_kr}").
     * 
     * @param id_kr ID do Resultado Chave a ser desassociado.
     * @param id_objetivo ID do objetivo do qual o Resultado Chave será desassociado.
     * @return Resposta HTTP com status 200 OK e mensagem de sucesso.
     */
    @DeleteMapping("/{id_objetivo}/desassociarKR/{id_kr}")
    public ResponseEntity<String> deleteKR(@PathVariable("id_kr") long id_kr, @PathVariable("id_objetivo") long id_objetivo) {
        objetivoService.desassociarKR(id_objetivo, id_kr);
        return ResponseEntity.ok("Resultado Chave desassociado com sucesso.");
    }
}