package com.example.demo.backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.backend.Controller.CreateIniciativasDTO;
import com.example.demo.backend.Entidades.Iniciativas;
import com.example.demo.backend.Entidades.ResultadosChave;
import com.example.demo.backend.Interfaces.IniciativasRepository;
import com.example.demo.backend.Interfaces.ResultadosChaveRepository;

/**
 * Serviço responsável por implementar a lógica de negócio relacionada às Iniciativas.
 * 
 * Essa classe atua como intermediária entre a Controller e o repositório, realizando operações
 * como criação, leitura, atualização e exclusão de iniciativas, além de gerenciar a associação
 * com os Resultados Chave.
 * 
 * O serviço faz a conversão entre DTOs e entidades e mantem a integridade dos dados.
 */

// Essa classe vai ter regras de negocio, então devemos avisar pro Spring (usar a anotacao "Service")
@Service
public class IniciativasService {

    private IniciativasRepository iniciativasRepository;
    private ResultadosChaveRepository resultadosChaveRepository;

    /**
     * Construtor que injeta as dependências dos repositórios de Iniciativas e Resultados Chave.
     * 
     * @param iniciativasRepository Repositório que contém as operações CRUD de Iniciativas.
     * @param resultadosChaveRepository Repositório que contém as operações CRUD de Resultados Chave.
     */
    public IniciativasService(IniciativasRepository iniciativasRepository, ResultadosChaveRepository resultadosChaveRepository){
        this.iniciativasRepository = iniciativasRepository;
        this.resultadosChaveRepository = resultadosChaveRepository;
    }

    /**
     * Cria uma nova iniciativa a partir dos dados recebidos no DTO.
     * 
     * @param dto Objeto contendo os dados da iniciativa a ser criada.
     * @return A entidade Iniciativas salva no banco de dados.
     */
    // Aqui o dto é juntado tudo em um objeto armazenado na variável "entity"
    public Iniciativas createIniciativas(CreateIniciativasDTO dto){
        var entity = new Iniciativas(
            dto.getTitulo(),
            dto.getDescricao(),
            dto.getPorcentagem(),
            dto.getResultadosChaveId()
        );
        return iniciativasRepository.save(entity);
    }

    /**
     * Busca uma iniciativa pelo seu ID.
     * 
     * @param id_iniciativas ID da iniciativa a ser buscada.
     * @return Um Optional contendo a iniciativa caso encontrada, ou vazio caso contrário.
     */
    public Optional<Iniciativas> getIniciativas(long id_iniciativas) {
        Optional<Iniciativas> resultado = iniciativasRepository.findById(id_iniciativas);
        if (resultado.isPresent()) {
            return resultado;
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retorna uma lista com todas as iniciativas cadastradas.
     * 
     * @return Lista de todas as entidades Iniciativas.
     */
    public List<Iniciativas> listarIniciativas(){
        return iniciativasRepository.findAll();
    }

    /**
     * Deleta uma iniciativa pelo seu ID, caso ela exista.
     * 
     * @param id_iniciativas ID da iniciativa a ser deletada.
     */
    public void deleteById(long id_iniciativas){
        boolean existe_iniciativa = iniciativasRepository.existsById(id_iniciativas);
        if (existe_iniciativa){
            iniciativasRepository.deleteById(id_iniciativas);
        } 
    }

    /**
     * Atualiza os dados de uma iniciativa existente, identificada pelo ID,
     * utilizando os dados fornecidos no DTO.
     * 
     * @param id_iniciativas ID da iniciativa a ser atualizada.
     * @param dto Objeto contendo os novos dados da iniciativa.
     * @throws RuntimeException Caso o Resultado Chave associado não seja encontrado soltará a frase: ("Objetivo não encontrado com o ID fornecido").
     */
    public void atualizarById(long id_iniciativas, CreateIniciativasDTO dto) {
        Optional<Iniciativas> iniciativaOptional = iniciativasRepository.findById(id_iniciativas);

        Iniciativas iniciativaExistente = iniciativaOptional.get();
        iniciativaExistente.setTitulo(dto.getTitulo());
        iniciativaExistente.setDescricao(dto.getDescricao());
        iniciativaExistente.setPorcentagem_conclusao_iniciativa(dto.getPorcentagem());

        // Aqui você só tenta buscar o Resultado-Chave se o ID for diferente de null
        if (dto.getResultadosChaveId() != null) {
            Optional<ResultadosChave> krOptional = resultadosChaveRepository.findById(dto.getResultadosChaveId());

            ResultadosChave kr = krOptional.get();

            iniciativaExistente.setResultadosChave(kr);

            if (kr.getIniciativas().isEmpty()) {
                kr.setPorcentagem_conclusao_kr(0.0);
            }

            kr.atualizarPorcentagemConclusaoKR();
            resultadosChaveRepository.save(kr);
        }

        iniciativasRepository.save(iniciativaExistente);

        // Método para atualizar a porcentagem do KR se ele não for trocado
        if (dto.getResultadosChaveId() == null && iniciativaExistente.getResultadosChave() != null) {
            ResultadosChave krAtual = iniciativaExistente.getResultadosChave();
            krAtual.atualizarPorcentagemConclusaoKR();
            resultadosChaveRepository.save(krAtual);
        }
    }
}