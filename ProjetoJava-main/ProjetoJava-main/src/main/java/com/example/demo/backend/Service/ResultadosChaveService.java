package com.example.demo.backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.backend.Controller.CreateResultadosChaveDTO;
import com.example.demo.backend.Entidades.Iniciativas;
import com.example.demo.backend.Entidades.Objetivo;
import com.example.demo.backend.Entidades.ResultadosChave;
import com.example.demo.backend.Interfaces.IniciativasRepository;
import com.example.demo.backend.Interfaces.ObjetivoRepository;
import com.example.demo.backend.Interfaces.ResultadosChaveRepository;

/**
 * Serviço responsável por implementar a lógica de negócio relacionada aos Resultados Chave.
 * 
 * Atua como intermediário entre Controller e repositórios, realizando operações
 * como criação, leitura, atualização, exclusão e gerenciamento das associações com
 * Iniciativas e Objetivos.
 * 
 * Converte DTOs para entidades e mantém a integridade dos dados.
 */

 // Essa classe vai ter regras de negocio, então devemos avisar pro Spring (usar a anotacao "Service")
@Service
public class ResultadosChaveService {

    private ResultadosChaveRepository resultadosChaveRepository;
    private IniciativasRepository iniciativasRepository;
    private ObjetivoRepository objetivoRepository;

    /**
     * Construtor que injeta os repositórios necessários.
     * 
     * @param resultadosChaveRepository Repositório que contém as operações CRUD de Resultados Chave.
     * @param iniciativasRepository Repositório que contém as operações CRUD de Iniciativas.
     * @param objetivoRepository Repositório que contém as operações CRUD de Objetivos.
     */
    public ResultadosChaveService(ResultadosChaveRepository resultadosChaveRepository, 
                                 IniciativasRepository iniciativasRepository,
                                 ObjetivoRepository objetivoRepository) {
        this.resultadosChaveRepository = resultadosChaveRepository;
        this.iniciativasRepository = iniciativasRepository;
        this.objetivoRepository = objetivoRepository;
    }

    /**
     * Cria um novo Resultado Chave a partir do DTO.
     * 
     * @param dto Dados para criação do Resultado Chave.
     * @return Entidade ResultadosChave salva.
     */
    // Aqui o dto é juntado tudo em um objeto armazenado na variável "entity"
    public ResultadosChave createResultadosChave(CreateResultadosChaveDTO dto) {
        var entity = new ResultadosChave(
            dto.getDescricao(),
            dto.getMeta(),
            dto.getPorcentagem()
        );
        return resultadosChaveRepository.save(entity);
    }

    /**
     * Busca um Resultado Chave pelo seu ID.
     * 
     * @param id_kr ID do Resultado Chave.
     * @return Optional com o Resultado Chave ou vazio.
     */
    public Optional<ResultadosChave> getResultadosChave(long id_kr) {
        return resultadosChaveRepository.findById(id_kr);
    }

    /**
     * Retorna todos os Resultados Chave cadastrados.
     * 
     * @return Lista de ResultadosChave.
     */
    public List<ResultadosChave> listarResultadosChaves() {
        return resultadosChaveRepository.findAll();
    }

    /**
     * Deleta um Resultado Chave pelo ID, se existir.
     * 
     * @param id_kr ID do Resultado Chave a ser deletado.
     */
    public void deleteById(long id_kr) {
        Optional<ResultadosChave> optionalKR = resultadosChaveRepository.findById(id_kr);
        optionalKR.ifPresent(resultadosChaveRepository::delete);
    }

    /**
     * Atualiza um Resultado Chave existente pelo ID, usando os dados do DTO.
     * 
     * @param id_kr ID do Resultado Chave a ser atualizado.
     * @param dto Novos dados do Resultado Chave.
     * @throws RuntimeException Caso o Objetivo associado não seja encontrado e soltará a mensagem: ("Objetivo não encontrado com o ID fornecido").
     */
    public void atualizarById(long id_kr, CreateResultadosChaveDTO dto) {
        Optional<ResultadosChave> optionalKR = resultadosChaveRepository.findById(id_kr);

        if (optionalKR.isPresent()) {
            ResultadosChave krExistente = optionalKR.get();

            krExistente.setDescricao(dto.getDescricao());
            krExistente.setMeta(dto.getMeta());

            // Associa Objetivo, se informado
            if (dto.getObjetivoId() != null) {
                Optional<Objetivo> objetivoOptional = objetivoRepository.findById(dto.getObjetivoId());
                if (objetivoOptional.isPresent()) {
                    krExistente.setObjetivo(objetivoOptional.get());
                } else {
                    throw new RuntimeException("Objetivo não encontrado com o ID fornecido");
                }
            }

            // Associa Iniciativas, se informadas
            if (dto.getIniciativas() != null && !dto.getIniciativas().isEmpty()) {
                List<Iniciativas> novasIniciativas = iniciativasRepository.findAllById(dto.getIniciativas());

                for (Iniciativas iniciativa : novasIniciativas) {
                    if (!krExistente.getIniciativas().contains(iniciativa)) {
                        iniciativa.setResultadosChave(krExistente);
                        iniciativa.setResultadosChaveId(id_kr);
                        krExistente.getIniciativas().add(iniciativa);
                    }
                }
            }

            if (krExistente.getIniciativas().isEmpty()) {
                krExistente.setPorcentagem_conclusao_kr(0.0);
            }

            krExistente.atualizarPorcentagemConclusaoKR();
            resultadosChaveRepository.save(krExistente);

            krExistente.getObjetivo().atualizarPorcentagemConclusaoObjetivo();
            objetivoRepository.save(krExistente.getObjetivo());
        }
    }

    /**
     * Remove a associação entre uma Iniciativa e um Resultado Chave.
     * 
     * @param id_kr ID do Resultado Chave.
     * @param id_iniciativa ID da Iniciativa a ser desassociada.
     */
    public void desassociarIniciativa(long id_kr, long id_iniciativa) {
        Optional<ResultadosChave> resultadoChaveOptional = resultadosChaveRepository.findById(id_kr);
        Optional<Iniciativas> iniciativaOptional = iniciativasRepository.findById(id_iniciativa);

        if (resultadoChaveOptional.isPresent() && iniciativaOptional.isPresent()) {
            ResultadosChave resultadoChave = resultadoChaveOptional.get();
            Iniciativas iniciativa = iniciativaOptional.get();

            if (resultadoChave.getIniciativas().contains(iniciativa)) {
                resultadoChave.getIniciativas().remove(iniciativa);
                iniciativa.setResultadosChaveId(null);
                iniciativa.setResultadosChave(null);

                resultadosChaveRepository.save(resultadoChave);
                iniciativasRepository.save(iniciativa);
            }

            if (resultadoChave.getIniciativas().isEmpty()) {
                resultadoChave.setPorcentagem_conclusao_kr(0.0);
            }

            resultadoChave.atualizarPorcentagemConclusaoKR();
            resultadosChaveRepository.save(resultadoChave);

        }
    }
}
