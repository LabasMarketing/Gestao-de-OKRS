package com.example.demo.backend.Controller;

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

    /**
     * Retorna o título do objetivo.
     * 
     * @return título do objetivo.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título do objetivo.
     * 
     * @param titulo Novo título do objetivo.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Retorna a descrição do objetivo.
     * 
     * @return descrição do objetivo.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do objetivo.
     * 
     * @param descricao Nova descrição do objetivo.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a porcentagem de conclusão do objetivo.
     * 
     * @return porcentagem concluída.
     */
    public double getPorcentagem() {
        return porcentagem_conclusao_obj;
    }

    /**
     * Define a porcentagem de conclusão do objetivo.
     * 
     * @param porcentagem_conclusao_obj Novo valor da porcentagem.
     */
    public void setPorcentagem(double porcentagem_conclusao_obj) {
        this.porcentagem_conclusao_obj = porcentagem_conclusao_obj;
    }

    /**
     * Retorna a lista de IDs dos resultados chave associados ao objetivo.
     * 
     * @return lista de IDs dos resultados chave.
     */
    public List<Long> getResultadosChaves() {
        return resultadosChaves;
    }

    /**
     * Define a lista de IDs dos resultados chave associados ao objetivo.
     * 
     * @param resultadosChaves Nova lista de IDs dos resultados chave.
     */
    public void setResultadosChaves(List<Long> resultadosChaves) {
        this.resultadosChaves = resultadosChaves;
    }
}