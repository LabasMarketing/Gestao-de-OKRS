package com.example.demo.backend.Entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa um objetivo que está ligado a vários resultados chaves (OKRs).
 * Contém informações como identificador único (id), título, descrição e
 * porcentagem de conclusão, que indica o progresso geral do objetivo de 0.0 a 100.0.
 * 
 * Um objetivo pode conter múltiplos resultados chave, que são armazenados em uma lista,
 * permitindo o acompanhamento e cálculo do progresso total do objetivo com base
 * nas porcentagens de conclusão de cada resultado chave associado.
 */
@Entity
@Table(name = "Objetivos")
public class Objetivo {
    /** Identificador único do objetivo */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id_objetivo;

    /** Título do objetivo */
    private String titulo;

    /** Descrição do objetivo */
    private String descricao;

    /**
     * Porcentagem da conclusão do objetivo, calculada a partir da média
     * das porcentagens de conclusão dos resultados chave relacionados.
     */
    private double porcentagem_conclusao_obj;

    /**
     * Lista de resultados chave associados a este objetivo.
     * Um objetivo pode ter vários resultados chave (KRs).
     */
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.ALL) 
    private List<ResultadosChave> resultadosChaves; 

    /**
     * Construtor padrão necessário para o funcionamento do framework Spring.
     */
    public Objetivo() {
    }

    /**
     * Construtor para criação de um objetivo com título, descrição e porcentagem de conclusão.
     * Inicializa a lista de resultados chave como vazia.
     * 
     * @param titulo Título do objetivo
     * @param descricao Descrição detalhada do objetivo
     * @param porcentagem_conclusao_obj Porcentagem de conclusão inicial do objetivo
     */
    public Objetivo(String titulo, String descricao, double porcentagem_conclusao_obj) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.porcentagem_conclusao_obj = porcentagem_conclusao_obj;
        this.resultadosChaves = new ArrayList<>();
    }
    
    /**
     * Obtém o identificador único do objetivo.
     * 
     * @return ID do objetivo
     */
    public long getId() {
        return id_objetivo;
    }

    /**
     * Define o identificador único do objetivo.
     * 
     * @param id_objetivo Novo ID do objetivo
     */
    public void setId(long id_objetivo) {
        this.id_objetivo = id_objetivo;
    }

    /**
     * Obtém o título do objetivo.
     * 
     * @return título do objetivo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título do objetivo.
     * 
     * @param titulo Novo título do objetivo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtém a descrição do objetivo.
     * 
     * @return descrição do objetivo
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do objetivo.
     * 
     * @param descricao Nova descrição do objetivo
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a porcentagem de conclusão do objetivo.
     * 
     * @return porcentagem concluída do objetivo
     */
    public double getPorcentagem_conclusao_obj() {
        return porcentagem_conclusao_obj;
    }

    /**
     * Define a porcentagem de conclusão do objetivo.
     * 
     * @param porcentagem_conclusao_obj Novo valor da porcentagem de conclusão
     */
    public void setPorcentagem_conclusao_obj(double porcentagem_conclusao_obj) {
        this.porcentagem_conclusao_obj = porcentagem_conclusao_obj;
    }

    /**
     * Obtém a lista de resultados chave associados a este objetivo.
     * 
     * @return lista de resultados chave
     */
    public List<ResultadosChave> getResultadosChaves() {
        return resultadosChaves;
    }

    /**
     * Define a lista de resultados chave associados a este objetivo.
     * 
     * @param resultadosChaves Nova lista de resultados chave
     */
    public void setResultadosChaves(List<ResultadosChave> resultadosChaves) {
        this.resultadosChaves = resultadosChaves;
    }

    /**
     * Atualiza a porcentagem de conclusão do objetivo calculando a média
     * das porcentagens de conclusão dos resultados chave relacionados.
     * Se não houver resultados chave, a porcentagem permanece inalterada.
     */
    public void atualizarPorcentagemConclusaoObjetivo() {
        if (resultadosChaves != null && !resultadosChaves.isEmpty()) {
            double totalPorcentagem = 0.0;
            int totalkrs = resultadosChaves.size();
            
            for (ResultadosChave kr : resultadosChaves) {
                totalPorcentagem += kr.getPorcentagem_conclusao_kr();
            }
            
            this.porcentagem_conclusao_obj = totalPorcentagem / totalkrs;
        }
    }
}
