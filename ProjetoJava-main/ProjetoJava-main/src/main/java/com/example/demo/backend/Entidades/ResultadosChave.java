package com.example.demo.backend.Entidades;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa um resultado chave (KR) que está vinculado a um objetivo.
 * Contém informações como identificador único (id), descrição, meta e
 * porcentagem de conclusão, que indica o progresso do resultado chave de 0.0 a 100.0.
 * 
 * Cada resultado chave está associado a um único objetivo e pode conter múltiplas
 * iniciativas, que contribuem para o seu progresso. As iniciativas são armazenadas
 * em uma lista, permitindo o acompanhamento e cálculo do progresso total do KR
 * com base nas porcentagens de conclusão de cada iniciativa associada.
 */
@Entity
@Table(name = "Resultados_Chave")
public class ResultadosChave {
    
    /** Identificador único do resultado chave */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id_kr;

    /** Descrição do resultado chave */
    private String descricao;

    /** Meta esperada para o resultado chave */
    private String meta;

    /**
     * Porcentagem de conclusão do resultado chave.
     * Calculada com base na média das porcentagens das iniciativas associadas (0.0 a 100.0).
     */
    private double porcentagem_conclusao_kr;

    /**
     * Objetivo ao qual este resultado chave está vinculado.
     * Relação muitos-para-um com a entidade Objetivo.
     */
    @ManyToOne
    @JoinColumn(name = "id_objetivo")
    @JsonIgnoreProperties("resultadosChaves")
    private Objetivo objetivo;

    /**
     * Lista de iniciativas associadas a este resultado chave.
     * Relação um-para-muitos com a entidade Iniciativas.
     */
    @OneToMany(mappedBy = "resultadosChave", cascade = CascadeType.ALL)
    private List<Iniciativas> iniciativas = new ArrayList<>();

    /** Construtor padrão da entidade ResultadosChave */
    public ResultadosChave() {
    }
    
    /**
     * Construtor com todos os parâmetros principais.
     * 
     * @param descricao Descrição do resultado chave.
     * @param meta Meta esperada do resultado chave.
     * @param porcentagem_conclusao_kr Porcentagem inicial de conclusão.
     */
    public ResultadosChave(String descricao, String meta, double porcentagem_conclusao_kr) {
        this.descricao = descricao;
        this.meta = meta;
        this.porcentagem_conclusao_kr = porcentagem_conclusao_kr;
        this.iniciativas = new ArrayList<>();
    }
    
    /**
     * Obtém o ID do resultado chave.
     * 
     * @return ID único do KR.
     */
    public long getId_kr() {
        return id_kr;
    }

    /**
     * Define o ID do resultado chave.
     * 
     * @param id_kr Novo ID a ser atribuído.
     */
    public void setId_kr(long id_kr) {
        this.id_kr = id_kr;
    }

    /**
     * Retorna a descrição do resultado chave.
     * 
     * @return Descrição do KR.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do resultado chave.
     * 
     * @param descricao Nova descrição.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a meta do resultado chave.
     * 
     * @return Meta do KR.
     */
    public String getMeta() {
        return meta;
    }

    /**
     * Define a meta do resultado chave.
     * 
     * @param meta Nova meta.
     */
    public void setMeta(String meta) {
        this.meta = meta;
    }

    /**
     * Retorna a porcentagem de conclusão do resultado chave.
     * 
     * @return Porcentagem concluída.
     */
    public double getPorcentagem_conclusao_kr() {
        return porcentagem_conclusao_kr;
    }

    /**
     * Define a porcentagem de conclusão do resultado chave.
     * 
     * @param porcentagem_conclusao_kr Novo valor da porcentagem.
     */
    public void setPorcentagem_conclusao_kr(double porcentagem_conclusao_kr) {
        this.porcentagem_conclusao_kr = porcentagem_conclusao_kr;
    }

    /**
     * Retorna o objetivo ao qual o KR está vinculado.
     * 
     * @return Objetivo relacionado.
     */
    public Objetivo getObjetivo(){
        return objetivo;
    }

    /**
     * Define o objetivo ao qual o KR está vinculado.
     * 
     * @param objetivo Novo objetivo.
     */
    public void setObjetivo(Objetivo objetivo){
        this.objetivo = objetivo;
    }

    /**
     * Obtém a lista de iniciativas relacionadas ao resultado chave.
     * 
     * @return Lista de iniciativas.
     */
    public List<Iniciativas> getIniciativas() {
        return iniciativas;
    }

    /**
     * Define a lista de iniciativas relacionadas ao resultado chave.
     * 
     * @param iniciativas Nova lista de iniciativas.
     */
    public void setIniciativas(List<Iniciativas> iniciativas) {
        this.iniciativas = iniciativas;
    }

    /**
     * Adiciona uma iniciativa ao resultado chave.
     * Também define a referência reversa na iniciativa.
     * 
     * @param iniciativa Iniciativa a ser adicionada.
     */
    public void adicionarIniciativa(Iniciativas iniciativa) {
        iniciativa.setResultadosChave(this); 
        this.iniciativas.add(iniciativa);    
    }

    /**
     * Atualiza a porcentagem de conclusão do resultado chave
     * com base na média das porcentagens de todas as iniciativas associadas.
     * Se não houver iniciativas, a porcentagem permanece inalterada.
     */
    public void atualizarPorcentagemConclusaoKR() {
        if (iniciativas != null && !iniciativas.isEmpty()) {
            double totalPorcentagem = 0.0;
            int totalIniciativas = iniciativas.size();
            
            for (Iniciativas iniciativa : iniciativas) {
                totalPorcentagem += iniciativa.getPorcentagem_conclusao_iniciativa();
            }
            
            this.porcentagem_conclusao_kr = totalPorcentagem / totalIniciativas;
        }
    }
}
