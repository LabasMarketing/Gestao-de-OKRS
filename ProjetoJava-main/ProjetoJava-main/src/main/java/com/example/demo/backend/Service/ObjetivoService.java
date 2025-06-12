package com.example.demo.backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.backend.Controller.CreateObjetivoDTO;
import com.example.demo.backend.Entidades.Objetivo;
import com.example.demo.backend.Entidades.ResultadosChave;
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

    /**
     * Busca um objetivo pelo seu ID.
     * 
     * @param id_objetivo ID do objetivo a ser buscado.
     * @return Optional contendo o objetivo encontrado ou vazio se não encontrado.
     */
    public Optional<Objetivo> getObjetivo(long id_objetivo) {
        return objetivoRepository.findById(id_objetivo);
    }

    /**
     * Retorna uma lista com todos os objetivos cadastrados.
     * 
     * @return Lista de entidades Objetivo.
     */
    public List<Objetivo> listarObjetivos(){
        return objetivoRepository.findAll();
    }

    /**
     * Deleta um objetivo pelo seu ID, caso ele exista.
     * 
     * @param id_objetivo ID do objetivo a ser deletado.
     */
    // Verifica se objetivo existe antes de deletar
    public void deleteById(long id_objetivo){
        if (objetivoRepository.existsById(id_objetivo)) {
            objetivoRepository.deleteById(id_objetivo);
        } 
    }

    /**
     * Atualiza os dados de um objetivo existente, identificado pelo ID,
     * utilizando os dados fornecidos no DTO.
     * 
     * @param id_objetivo ID do objetivo a ser atualizado.
     * @param dto Objeto contendo os novos dados do objetivo.
     */
    public void atualizarById(long id_objetivo, CreateObjetivoDTO dto) {
        Optional<Objetivo> objetivoOptional = objetivoRepository.findById(id_objetivo);
        if (objetivoOptional.isPresent()) {
            Objetivo objetivo = objetivoOptional.get();

            objetivo.setTitulo(dto.getTitulo());
            objetivo.setDescricao(dto.getDescricao());
            objetivo.setPorcentagem_conclusao_obj(dto.getPorcentagem());

            // Se há resultados chave no DTO, associa ao objetivo
            if (dto.getResultadosChaves() != null && !dto.getResultadosChaves().isEmpty()) {
                List<ResultadosChave> novosResultadosChave = resultadosChaveRepository.findAllById(dto.getResultadosChaves());
                for (ResultadosChave kr : novosResultadosChave) {
                    if (!objetivo.getResultadosChaves().contains(kr)) {
                        kr.setObjetivo(objetivo);
                        objetivo.getResultadosChaves().add(kr);
                    }
                }
            }

            // Se não tem resultados chave, zera a porcentagem do objetivo
            if (objetivo.getResultadosChaves().isEmpty()){
                objetivo.setPorcentagem_conclusao_obj(0.0);
            }

            // Atualiza a porcentagem de conclusão de cada KR e salva
            for (ResultadosChave kr : objetivo.getResultadosChaves()) {
                kr.atualizarPorcentagemConclusaoKR();
                resultadosChaveRepository.save(kr);
            }

            // Atualiza a porcentagem do objetivo com base nas KRs e salva
            objetivo.atualizarPorcentagemConclusaoObjetivo();
            objetivoRepository.save(objetivo);
        }
    }

    /**
     * Desassocia um Resultado Chave de um Objetivo, removendo a associação entre eles.
     * 
     * @param id_objetivo ID do objetivo.
     * @param id_kr ID do Resultado Chave a ser desassociado.
     */
    public void desassociarKR(long id_objetivo, long id_kr) {
        Optional<ResultadosChave> resultadoChaveOptional = resultadosChaveRepository.findById(id_kr);
        Optional<Objetivo> objetivoOptional = objetivoRepository.findById(id_objetivo);

        if (resultadoChaveOptional.isPresent() && objetivoOptional.isPresent()) {
            ResultadosChave resultadoChave = resultadoChaveOptional.get();
            Objetivo objetivo = objetivoOptional.get();

            // Remove a associação entre objetivo e resultado chave, se existir
            if (objetivo.getResultadosChaves().contains(resultadoChave)) {
                objetivo.getResultadosChaves().remove(resultadoChave);
                resultadoChave.setObjetivo(null);

                resultadosChaveRepository.save(resultadoChave);
                objetivoRepository.save(objetivo);
            }

            // Atualiza porcentagens após a remoção
            if (objetivo.getResultadosChaves().isEmpty()){
                objetivo.setPorcentagem_conclusao_obj(0.0);
            }
            for (ResultadosChave kr : objetivo.getResultadosChaves()) {
                kr.atualizarPorcentagemConclusaoKR();
                resultadosChaveRepository.save(kr);
            }
            objetivo.atualizarPorcentagemConclusaoObjetivo();
            objetivoRepository.save(objetivo);
        }
    }
}
