"use client";
import styles from "./PaginaAtualizarObjetivos.module.css";
import { useState } from "react";

export default function AtualizarObjetivos() {
  const [novoObjetivo, setNovoObjetivo] = useState({
        id: '', // String vaiza para o usuário digitar antes de atualizar
        titulo: '',
        descricao: '',
        resultadosChaves: []
      });
    
    // Função só para atualizar via PUT
    async function chamarAPIPutObjetivos() {
      const URL = `http://localhost:8080/objetivos/${novoObjetivo.id}`;
      try {
        const resp = await fetch(URL, {
        method: 'PUT', // O padrão vem como GET, por isso precisamos, nesse caso, especificar que o tipo que queremos é o PUT
        headers: {
        'Content-Type': 'application/json', // Informando que o conteúdo é JSON
        },
        body: JSON.stringify(novoObjetivo), // Passando os dados do novoObjetivo como corpo da requisição
        });
        if (resp.status === 200 || resp.status === 201) {
          alert("Objetivo atualizado com sucesso!");
          setNovoObjetivo({ id:'', titulo: '',  desricao: '', resultadosChaves: ''}); // Limpando o conteúdo / Voltando o estado da novaIniciativa para como era antes (vazio)
          await chamarAPIGetObjetivos();  // Atualiza a lista do GET mas agora com o novo item adicionado 
        } else {
          console.error('Erro ao atualizar objetivo:', resp.status);
        }
      } catch (error) {
        console.error('Erro na requisição:', error);
      }
      }

  return (
    <div className="fundo">
      <h1 className={styles.objetivos}>Objetivos</h1>

      {/* Formulário para adicionar novo Objetivo */}
      <form id="formPutObjetivos" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPutObjetivos(); }}>
        <h2 className={styles.titulo}>Atualizar Objetivo</h2>

        <input id="id" type="number" placeholder="Digite o ID do Objetivo" value={novoObjetivo.id_kr} onChange={(e) => setNovoObjetivo({ ...novoObjetivo, id: e.target.value })} className={styles.input} required/>

        <input id="titulo" type="text" placeholder="Digite o título" value={novoObjetivo.titulo} onChange={(e) => setNovoObjetivo({ ...novoObjetivo, titulo: e.target.value })} className={styles.input} />

        <input id="descricao" type="text" placeholder="Digite a descrição" value={novoObjetivo.descricao} onChange={(e) => setNovoObjetivo({ ...novoObjetivo, descricao: e.target.value })} className={styles.input} />

        <input id="resultadosChaves" type="text" placeholder="Digite o ID do resultado/s Chave" value={novoObjetivo.iniciativas} onChange={(e) => setNovoObjetivo({ ...novoObjetivo, resultadosChaves: e.target.value.split(',').map((id) => Number(id.trim()))})} className={styles.input} />

        <button type="submit" className={styles.botao}>Atualizar</button>
      </form>
    </div>
  );
}
