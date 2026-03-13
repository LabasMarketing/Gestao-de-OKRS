"use client";
import styles from "./PaginaCadastroKRS.module.css";
import React, { useState } from "react";

export default function CadastroKRS() {
  // Estado para novo resultado chave
  const [novoResultadoChave, setNovoResultadoChave] = useState({
    descricao: "",
    titulo: "",
    objetivoId: "",
    iniciativas: []
  });

  // Função para postar novo resultado chave
  async function chamarAPIPostResultadoChave() {
    const URL = "http://localhost:8080/resultadosChave";
    try {
      const resp = await fetch(URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(novoResultadoChave),
      });
      if (resp.ok) {
        alert("Resultado Chave cadastrado com sucesso!");
        setNovoResultadoChave({ descricao: "", meta: "", objetivoId: "", iniciativas: [] });
        chamarAPIResultadosChave();
      } else {
        alert("Erro ao cadastrar Resultado Chave");
      }
    } catch (error) {
      console.error("Erro na requisição POST:", error);
    }
  }

  return (
    <div className="fundo">
      <h1 className={styles.KRS}>Resultados Chave</h1>

      {/* Formulário para adicionar novo Resultado Chave */}
      <form id="formPostResultadoChave" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPostResultadoChave(); }}>
        <h2 className={styles.titulo}>Cadastrar Novo Resultado Chave</h2>

        <input id="descricao" type="text" placeholder="Digite a descrição" value={novoResultadoChave.descricao} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, descricao: e.target.value })} className={styles.input} />

        <input id="meta" type="text" placeholder="Digite a meta" value={novoResultadoChave.meta} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, meta: e.target.value })} className={styles.input} />

        <button type="submit" className={styles.botao}>Cadastrar</button>
      </form>
    </div>
  );
}
