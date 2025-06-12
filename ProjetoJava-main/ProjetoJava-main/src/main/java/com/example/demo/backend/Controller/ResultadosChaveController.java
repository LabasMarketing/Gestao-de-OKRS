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

import com.example.demo.backend.Entidades.ResultadosChave;
import com.example.demo.backend.Service.ResultadosChaveService;

/**
 * Controller responsável por tratar as requisições HTTP relacionadas aos Resultados Chave.
 * 
 * Essa classe atua como intermediária entre o front-end e a classe {@link ResultadosChaveService},
 * recebendo as requisições, fazendo com que a lógica de negócio seja implementada na service e
 * retornando as respostas HTTP.
 * 
 * As operações disponíveis são:
 * - Criar um novo Resultado Chave (POST)
 * - Buscar Resultado Chave por ID (GET)
 * - Listar todos os Resultados Chave (GET)
 * - Deletar Resultado Chave por ID (DELETE)
 * - Atualizar Resultado Chave por ID (PUT)
 * - Desassociar uma iniciativa de um Resultado Chave (DELETE)
 * 
 * Segue o padrão REST, utilizando as anotações do Spring Boot para mapear
 * os endpoints no caminho "/resultadosChave".
 * 
 * Fluxo geral: Controller -> Service -> Banco de Dados
 */
@CrossOrigin(origins = "http://localhost:3000") // Front ta rodando na porta 3000
@RestController
@RequestMapping("/resultadosChave")
public class ResultadosChaveController {
    private ResultadosChaveService krService;

    /**
     * Construtor que injeta a dependência do serviço de Resultados Chave.
     * 
     * @param resultadosChaveService Serviço responsável pela lógica dos Resultados Chave.
     */
    public ResultadosChaveController(ResultadosChaveService resultadosChaveService){
        this.krService = resultadosChaveService;
    }

    /**
     * Endpoint para criar um novo Resultado Chave ("/resultadosChave").
     * 
     * @param createResultadosChaveDTO Objeto contendo os dados do Resultado Chave a ser criado.
     * @return Resposta HTTP com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<ResultadosChave> createResultadosChave(@RequestBody CreateResultadosChaveDTO createResultadosChaveDTO){
        krService.createResultadosChave(createResultadosChaveDTO); 
        return ResponseEntity.created(URI.create("/resultadosChave")).build(); 
    }

    /**
     * Endpoint para obter um Resultado Chave pelo seu ID ("/{id_kr}").
     * 
     * @param id_kr ID do Resultado Chave a ser buscado.
     * @return Resposta HTTP com status 200 OK e o Resultado Chave, ou 404 Not Found caso não exista.
     */
    @GetMapping("/{id_kr}")
    public ResponseEntity<ResultadosChave> getResuladosChave(@PathVariable("id_kr") long id_kr){
        Optional<ResultadosChave> resultadoChave = krService.getResultadosChave(id_kr);
        if (resultadoChave.isPresent()){
            return ResponseEntity.ok(resultadoChave.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para listar todos os Resultados Chave cadastrados ("/resultadosChave").
     * 
     * @return Resposta HTTP com status 200 OK e lista de Resultados Chave.
     */
    @GetMapping
    public ResponseEntity<List<ResultadosChave>> listarResultadosChave(){
        var resultadoChave = krService.listarResultadosChaves();
        return ResponseEntity.ok(resultadoChave);
    }

    /**
     * Endpoint para deletar um Resultado Chave pelo seu ID ("/{id_kr}").
     * 
     * @param id_kr ID do Resultado Chave a ser deletado.
     * @return Resposta HTTP com status 204 No Content.
     */
    @DeleteMapping("/{id_kr}")
    public ResponseEntity<Void> deleteById(@PathVariable("id_kr") long id_kr){
        krService.deleteById(id_kr);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para atualizar os dados de um Resultado Chave existente pelo ID ("/{id_kr}").
     * 
     * @param id_kr ID do Resultado Chave a ser atualizado.
     * @param dto Objeto contendo os novos dados do Resultado Chave.
     * @return Resposta HTTP com status 200 OK e mensagem de sucesso, ou 404 Not Found caso não exista.
     */
    @PutMapping("/{id_kr}")
    public ResponseEntity<String> updateById(@PathVariable("id_kr") long id_kr,@RequestBody CreateResultadosChaveDTO dto) {
        Optional<ResultadosChave> krExistente = krService.getResultadosChave(id_kr);

        if (krExistente.isPresent()) {
            krService.atualizarById(id_kr, dto);
            return ResponseEntity.ok("Resultado Chave atualizado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para desassociar uma iniciativa de um Resultado Chave ("/{id_kr}/disassociarIni/{id_iniciativa}").
     * 
     * @param id_kr ID do Resultado Chave do qual a iniciativa será desassociada.
     * @param id_iniciativa ID da iniciativa a ser desassociada.
     * @return Resposta HTTP com status 200 OK e mensagem de sucesso.
     */
    @DeleteMapping("/{id_kr}/desassociarIni/{id_iniciativa}")
    public ResponseEntity<String> deleteIniciativa(@PathVariable("id_kr") long id_kr, @PathVariable("id_iniciativa") long id_iniciativa) {
        krService.desassociarIniciativa(id_kr, id_iniciativa);
        return ResponseEntity.ok("Iniciativa desassociada com sucesso.");
    }
}
