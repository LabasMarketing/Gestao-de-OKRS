"use client";
import styles from "./PaginaCadastroObjetivos.module.css";
import React, { useState } from "react";

export default function CadastroObjetivos() {
  // Estado para novo objetivo
  const [novoObjetivo, setNovoObjetivo] = useState({
    titulo: "",
    descricao: "",
    porcentagem_conclusao_obj: 0,
    resultadosChaves: []
  });

  // Função para postar novo Objetivo
  async function chamarAPIPostObjetivos() {
    const URL = "http://localhost:8080/objetivos";
    try {
      const resp = await fetch(URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(novoObjetivo),
      });
      if (resp.ok) {
        alert("Objetivo cadastrado com sucesso!");
        setNovoObjetivo({ 
            titulo: "",
            descricao: "",
            porcentagem_conclusao_obj: 0,
            resultadosChaves: []
        });
        chamarAPIObjetivo();
      } else {
        alert("Erro ao adicionar objetivo");
      }
    } catch (error) {
      console.error("Erro na requisição POST:", error);
    }
  }
  return (
    <div className="fundo">
      <h1 className={styles.objetivos}>Objetivos</h1>

      {/* Formulário para adicionar novo Objetivo */}
      <form id="formPostObjetivos" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPostObjetivos(); }}>
        <h2 className={styles.titulo}>Cadastrar Novo Objetivo</h2>

        <input id="titulo" type="text" placeholder="Digite o título" value={novoObjetivo.titulo} onChange={(e) => setNovoObjetivo({ ...novoObjetivo, titulo: e.target.value })} className={styles.input} />

        <input id="descricao" type="text" placeholder="Digite a descrição" value={novoObjetivo.descricao} onChange={(e) => setNovoObjetivo({ ...novoObjetivo, descricao: e.target.value })} className={styles.input} />

        <button type="submit" className={styles.botao}>Cadastrar</button>
      </form>
    </div>
  );
}
