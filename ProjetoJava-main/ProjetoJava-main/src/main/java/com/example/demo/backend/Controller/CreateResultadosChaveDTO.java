package com.example.demo.backend.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO (Data Transfer Object) usado para criação de um novo Resultado Chave via requisição HTTP.
 * 
 * Essa classe serve como um objeto exclusivamente para uso na requisição HTTP.
 * Ela permite que mudanças no banco de dados ou nas entidades não quebrem diretamente a API.
 * 
 * Contém informações essenciais como descrição, meta, porcentagem de conclusão,
 * ID do objetivo associado e uma lista de IDs das iniciativas vinculadas a este resultado chave.
 * 
 */
public class CreateResultadosChaveDTO {
    
    /** ID do objetivo ao qual este resultado chave está associado */
    private Long objetivo;
    
    /** Descrição detalhada do resultado chave */
    private String descricao;
    
    /** Meta ou objetivo específico do resultado chave */
    private String meta;
    
    /** Porcentagem de conclusão do resultado chave (0.0 a 100.0) */
    private double porcentagem_conclusao_kr;
    
    /** Lista com IDs das iniciativas associadas a este resultado chave */
    private List<Long> iniciativas;

    /**
     * Construtor padrão sem parâmetros.
     */
    public CreateResultadosChaveDTO() {
        this.iniciativas = new ArrayList<>();
    }

    /**
     * Construtor com parâmetros para facilitar a criação do DTO.
     * 
     * @param descricao Descrição do resultado chave.
     * @param meta Meta do resultado chave.
     * @param porcentagem_conclusao_kr Porcentagem de conclusão do resultado chave.
     * @param objetivo ID do objetivo associado.
     */
    public CreateResultadosChaveDTO(String descricao, String meta, double porcentagem_conclusao_kr, Long objetivo) {
        this.descricao = descricao;
        this.meta = meta;
        this.porcentagem_conclusao_kr = porcentagem_conclusao_kr;
        this.objetivo = objetivo;
        this.iniciativas = new ArrayList<>();
    }

    /**
     * Retorna a descrição do resultado chave.
     * 
     * @return descrição do resultado chave.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do resultado chave.
     * 
     * @param descricao Nova descrição do resultado chave.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a meta do resultado chave.
     * 
     * @return meta do resultado chave.
     */
    public String getMeta() {
        return meta;
    }

    /**
     * Define a meta do resultado chave.
     * 
     * @param meta Nova meta do resultado chave.
     */
    public void setMeta(String meta) {
        this.meta = meta;
    }

    /**
     * Retorna a porcentagem de conclusão do resultado chave.
     * 
     * @return porcentagem concluída.
     */
    public double getPorcentagem() {
        return porcentagem_conclusao_kr;
    }

    /**
     * Define a porcentagem de conclusão do resultado chave.
     * 
     * @param porcentagem_conclusao_kr Novo valor da porcentagem.
     */
    public void setPorcentagem(double porcentagem_conclusao_kr) {
        this.porcentagem_conclusao_kr = porcentagem_conclusao_kr;
    }

    /**
     * Retorna a lista de IDs das iniciativas associadas a este resultado chave.
     * 
     * @return lista de IDs das iniciativas.
     */
    public List<Long> getIniciativas() {
        return iniciativas;
    }

    /**
     * Define a lista de IDs das iniciativas associadas a este resultado chave.
     * 
     * @param iniciativas Nova lista de IDs das iniciativas.
     */
    public void setIniciativas(List<Long> iniciativas) {
        this.iniciativas = iniciativas;
    }

    /**
     * Retorna o ID do objetivo associado a este resultado chave.
     * 
     * @return ID do objetivo.
     */
    public Long getObjetivoId() {
        return objetivo;
    }

    /**
     * Define o ID do objetivo associado a este resultado chave.
     * 
     * @param objetivo Novo ID do objetivo.
     */
    public void setObjetivoId(Long objetivo) {
        this.objetivo = objetivo;
    }
}
