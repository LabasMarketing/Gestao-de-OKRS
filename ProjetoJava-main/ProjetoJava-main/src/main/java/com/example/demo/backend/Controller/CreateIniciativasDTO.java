package com.example.demo.backend.Controller;

/**
 * DTO (Data Transfer Object) usado para criação de uma nova Iniciativa via requisição HTTP.
 * 
 * Essa classe serve como um objeto só para ser usado na requisicao HTTP.
 * Ela permite que mudanças no banco de dados ou nas entidades não quebrem diretamente a API.
 * 
 * Contém informações como título, descrição, porcentagem de conclusão,
 * ID do resultado chave associado e status de conclusão.
 * 
 */
public class CreateIniciativasDTO {
    
    /** Título da iniciativa a ser criada */
    private String titulo;

    /** Descrição detalhada da iniciativa */
    private String descricao;

    /** Porcentagem de conclusão da iniciativa (0.0 a 100.0) */
    private double porcentagem;

    /** Identificador do resultado chave ao qual essa iniciativa está vinculada */
    private Long resultadosChaveId;

    /** Indica se a iniciativa já foi concluída */
    private boolean concluida;

    /**
     * Construtor padrão sem parâmetros.
     */
    public CreateIniciativasDTO() {
    }

    /**
     * Construtor com parâmetros para facilitar a criação do DTO.
     * 
     * @param titulo Título da iniciativa.
     * @param descricao Descrição da iniciativa.
     * @param porcentagem Porcentagem de conclusão da iniciativa.
     * @param resultadosChaveId ID do resultado chave associado.
     */
    public CreateIniciativasDTO(String titulo, String descricao, double porcentagem, Long resultadosChaveId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.porcentagem = porcentagem;
        this.resultadosChaveId = resultadosChaveId;
    }

    /**
     * Retorna o título da iniciativa.
     * 
     * @return título da iniciativa.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título da iniciativa.
     * 
     * @param titulo Novo título da iniciativa.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Retorna a descrição da iniciativa.
     * 
     * @return descrição da iniciativa.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da iniciativa.
     * 
     * @param descricao Nova descrição da iniciativa.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a porcentagem de conclusão da iniciativa.
     * 
     * @return porcentagem concluída.
     */
    public double getPorcentagem() {
        return porcentagem;
    }

    /**
     * Define a porcentagem de conclusão da iniciativa.
     * 
     * @param porcentagem Novo valor da porcentagem.
     */
    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }

    /**
     * Retorna o ID do resultado chave associado à iniciativa.
     * 
     * @return ID do resultado chave.
     */
    public Long getResultadosChaveId() {
        return resultadosChaveId;
    }

    /**
     * Define o ID do resultado chave associado à iniciativa.
     * 
     * @param resultadosChaveId Novo ID do resultado chave.
     */
    public void setResultadosChaveId(Long resultadosChaveId) {
        this.resultadosChaveId = resultadosChaveId;
    }

    /**
     * Retorna o status de conclusão da iniciativa.
     * 
     * @return true se concluída, false caso contrário.
     */
    public boolean isConcluida() {
        return concluida;
    }

    /**
     * Define o status de conclusão da iniciativa.
     * 
     * @param concluida Novo status de conclusão.
     */
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}