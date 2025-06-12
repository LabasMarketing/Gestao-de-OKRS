package com.example.demo.backend.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa uma iniciativa que está vinculada a um resultado chave (KR).
 * Contém informações como identificador único (id), título, descrição e
 * porcentagem de conclusão, que indica o progresso da iniciativa de 0.0 a 100.0.
 * 
 * Cada iniciativa está associada a um único resultado chave, permitindo o acompanhamento
 * do progresso específico relacionado a esse resultado.
 */

@Entity
@Table(name = "Iniciativas")
public class Iniciativas {
    /** Código único referente a cada iniciativa */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id_iniciativas;

    /** Título de cada iniciativa */
    private String titulo;

    /** Descrição da iniciativa */
    private String descricao;

    /**
     * Porcentagem da conclusão de uma iniciatva, calculada a partir da média
     * das porcentagens de conclusão dos resultados chave relacionados (0.0 a 100.0).
     */
    private double porcentagem_conclusao_iniciativa;

    /** Identificador único da classe resultadosChave */
    private Long resultadoChaveId;

    /**
     * Atributo resultadoChave, que representa um resultado chave relacionado a essa entidade,
     * assim servindo como chave estrangeira de "Iniciativas"
     */
    // Relacionamento Iniciativas com ResultadosChave  
    // Um resultado chave (KRs) pode ter várias iniciativas
    @ManyToOne
    @JoinColumn(name = "id_kr") // Chave estrangeira para ResultadosChave
    @JsonBackReference
    private ResultadosChave resultadosChave;

    /**
     * Construtor padrão da classe Iniciativas.
     */
    public Iniciativas() {
    }
    
    /**
     * Construtor com todos os parâmetros.
     * 
     * @param titulo Título da iniciativa.
     * @param descricao Descrição da iniciativa.
     * @param porcentagem_conclusao_iniciativa Percentual de conclusão da iniciativa.
     * @param resultadoChaveId ID do resultado chave associado.
     */
    public Iniciativas(String titulo, String descricao, double porcentagem_conclusao_iniciativa, Long resultadoChaveId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.porcentagem_conclusao_iniciativa = porcentagem_conclusao_iniciativa;
        this.resultadoChaveId = resultadoChaveId;
    }
    
    // Getters e Setters
    
    /**
     * Obtém o ID único da iniciativa.
     * 
     * @return id da iniciativa.
     */
    public long getId_iniciativas() {
        return id_iniciativas;
    }

    /**
     * Define o ID da iniciativa.
     * 
     * @param id_iniciativas ID a ser definido.
     */
    public void setId_iniciativas(long id_iniciativas) {
        this.id_iniciativas = id_iniciativas;
    }

    /**
     * Obtém o título da iniciativa.
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
     * Obtém a descrição da iniciativa.
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
     * Obtém a porcentagem de conclusão da iniciativa.
     * 
     * @return porcentagem concluída.
     */
    public double getPorcentagem_conclusao_iniciativa() {
        return porcentagem_conclusao_iniciativa;
    }

    /**
     * Define a porcentagem de conclusão da iniciativa.
     * 
     * @param porcentagem_conclusao_iniciativa Novo valor da porcentagem.
     */
    public void setPorcentagem_conclusao_iniciativa(double porcentagem_conclusao_iniciativa) {
        this.porcentagem_conclusao_iniciativa = porcentagem_conclusao_iniciativa;
    }

    /**
     * Obtém o objeto ResultadosChave associado.
     * 
     * @return ResultadosChave relacionado.
     */
    public ResultadosChave getResultadosChave() {
        return resultadosChave;
    }
    
    /**
     * Define o objeto ResultadosChave associado.
     * 
     * @param resultadosChave Objeto ResultadosChave para associar.
     */
    public void setResultadosChave(ResultadosChave resultadosChave) {
        this.resultadosChave = resultadosChave;
    }

    /**
     * Obtém o ID do resultado chave associado.
     * 
     * @return ID do resultado chave.
     */
    public Long getResultadosChaveId() {
        return resultadoChaveId;
    }
    
    /**
     * Define o ID do resultado chave associado.
     * 
     * @param resultadosChaveId Novo ID do resultado chave.
     */
    public void setResultadosChaveId(Long resultadosChaveId) {
        this.resultadoChaveId = resultadosChaveId;
    }
}
