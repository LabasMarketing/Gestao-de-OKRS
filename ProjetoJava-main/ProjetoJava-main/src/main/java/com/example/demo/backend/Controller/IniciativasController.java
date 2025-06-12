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

import com.example.demo.backend.Entidades.Iniciativas;
import com.example.demo.backend.Service.IniciativasService;


/**
 * Controller responsável por tratar as requisições HTTP relacionadas às Iniciativas.
 * 
 * Essa classe atua como intermediária entre o front-end e a clase {@link IniciativasService},
 * recebendo as requisições, e fazendo com que a lógica de negócio seja implementada na service e
 * aqui seja retornada as respostas HTTP.
 * 
 * As operações disponíveis são:
 * - Criar uma nova iniciativa (POST)
 * - Buscar iniciativa por ID (GET)
 * - Listar todas as iniciativas (GET)
 * - Deletar iniciativa por ID (DELETE)
 * - Atualizar iniciativa por ID (PUT)
 * 
 * Segue o padrão REST, utilizando as anotações do Spring Boot para mapear
 * os endpoints no caminho "/iniciativas".
 * 
 * Fluxo geral: Controller -> Service -> Banco de Dados
 * 
 */

// Serve como o porteiro, tudo que vem do front passa primeiro por aqui
// Trata as requisições HTTP por aqui
    // A controller vai precisar chamar primeiramente a Service para depois a service vai chamar o banco de dados
    // Controller -> Service -> DB
@CrossOrigin(origins = "http://localhost:3000") // Front ta rodando na porta 3000
@RestController
@RequestMapping("/iniciativas")
public class IniciativasController {
    private IniciativasService iniciativasService;

    /**
     * Construtor que injeta a dependência do serviço de iniciativas.
     * 
     * @param iniciativasService Serviço responsável pela lógica das iniciativas.
     */
    public IniciativasController(IniciativasService iniciativasService){
        this.iniciativasService = iniciativasService;
    }

    /**
     * Endpoint para criar uma nova iniciativa ("/iniciativas").
     * 
     * @param createIniciativasDTO Objeto contendo os dados da iniciativa a ser criada.
     * @return Resposta HTTP com status 201.
     */
    @PostMapping
    public ResponseEntity<Iniciativas> createIniciativa(@RequestBody CreateIniciativasDTO createIniciativasDTO){
        iniciativasService.createIniciativas(createIniciativasDTO); 
        return ResponseEntity.created(URI.create("/iniciativas")).build(); 
    }

    /**
     * Endpoint para obter uma iniciativa pelo seu ID ("/id_iniciativas").
     * 
     * @param id_iniciativas ID da iniciativa a ser buscada.
     * @return Resposta HTTP com status 200 OK e a iniciativa, ou 404 Not Found caso não exista.
     */
    @GetMapping("/{id_iniciativas}")
    public ResponseEntity<Iniciativas> getIniciativa(@PathVariable("id_iniciativas") long id_iniciativas){
        Optional<Iniciativas> iniciativa = iniciativasService.getIniciativas(id_iniciativas);
        if (iniciativa.isPresent()){
            return ResponseEntity.ok(iniciativa.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para listar todas as iniciativas cadastradas (/iniciativas).
     * 
     * @return Resposta HTTP com status 200 OK e lista de iniciativas.
     */
    @GetMapping
    public ResponseEntity<List<Iniciativas>> listarIniciativas(){
        var iniciativas = iniciativasService.listarIniciativas();
        return ResponseEntity.ok(iniciativas);
    }

    /**
     * Endpoint para deletar uma iniciativa pelo seu ID (/id_iniciativas).
     * 
     * @param id_iniciativas ID da iniciativa a ser deletada.
     * @return Resposta HTTP com status 204 No Content.
     */
    @DeleteMapping("/{id_iniciativas}")
    public ResponseEntity<Void> deleteById(@PathVariable("id_iniciativas") long id_iniciativas){
        iniciativasService.deleteById(id_iniciativas);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para atualizar os dados de uma iniciativa existente pelo ID (/id_iniciativas).
     * 
     * @param id_iniciativas ID da iniciativa a ser atualizada.
     * @param dto Objeto contendo os novos dados da iniciativa.
     * @return Resposta HTTP com status 200 OK e mensagem de sucesso, ou 404 Not Found caso não exista.
     */
    @PutMapping("/{id_iniciativas}")
    public ResponseEntity<String> updateById(@PathVariable("id_iniciativas") long id_iniciativas,@RequestBody CreateIniciativasDTO dto) {
        Optional<Iniciativas> iniciativaExistente = iniciativasService.getIniciativas(id_iniciativas);

        if (iniciativaExistente.isPresent()) {
            iniciativasService.atualizarById(id_iniciativas, dto);
            return ResponseEntity.ok("Iniciativa atualizada com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}