"use client";
import styles from "./PaginaAtualizarObjetivos.module.css";
import { useState, useEffect } from "react"; // Adicionado useEffect

export default function AtualizarObjetivos() {
  const [novoObjetivo, setNovoObjetivo] = useState({
    id: '', 
    titulo: '',
    descricao: '',
    resultadosChaves: []
  });

  const BASE_URL = "/api";

  // Nova Função que busca dados pelo id
  useEffect(() => {
  async function buscarObjetivoPorId() {
    const id = novoObjetivo.id;

    if (!id) {
      setNovoObjetivo(prev => ({ ...prev, titulo: '', descricao: '', resultadosChaves: [] }));
      return;
    }

    try {
      const response = await fetch(`${BASE_URL}/objetivos/${id}`);
      if (response.ok) {
        const dados = await response.json();
        
        setNovoObjetivo(prev => ({
          ...prev,
          titulo: dados.titulo || '',
          descricao: dados.descricao || '',
          // Verificamos se 'resultadosChaves' existe e mapeamos os ids
          resultadosChaves: dados.resultadosChaves ? dados.resultadosChaves.map(item => item.id_kr) : []
        }));
      } else {
        setNovoObjetivo(prev => ({ ...prev, titulo: '', descricao: '', resultadosChaves: [] }));
      }
    } catch (error) {
      console.error("Erro ao buscar objetivo:", error);
    }
  }

  buscarObjetivoPorId();
}, [novoObjetivo.id]);

  // Função para atualizar via PUT
  async function chamarAPIPutObjetivos() {
    const URL = `${BASE_URL}/objetivos/${novoObjetivo.id}`;
    try {
      const resp = await fetch(URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoObjetivo),
      });

      if (resp.status === 200 || resp.status === 201) {
        alert("Objetivo atualizado com sucesso!");
        setNovoObjetivo({ id: '', titulo: '', descricao: '', resultadosChaves: [] });
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

      <form id="formPutObjetivos" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPutObjetivos(); }}>
        <h2 className={styles.titulo}>Atualizar Objetivo</h2>

        <input 
          id="id" 
          type="number" 
          placeholder="Digite o ID do Objetivo" 
          value={novoObjetivo.id} 
          onChange={(e) => setNovoObjetivo({ ...novoObjetivo, id: e.target.value })} 
          className={styles.input} 
          required
        />

        <input 
          id="titulo" 
          type="text" 
          placeholder="Digite o título" 
          value={novoObjetivo.titulo} 
          onChange={(e) => setNovoObjetivo({ ...novoObjetivo, titulo: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="descricao" 
          type="text" 
          placeholder="Digite a descrição" 
          value={novoObjetivo.descricao} 
          onChange={(e) => setNovoObjetivo({ ...novoObjetivo, descricao: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="resultadosChaves" 
          type="text" 
          placeholder="IDs dos resultados" 
          // O .join transforma o array [1, 2] em "1, 2" para o usuário ver
          value={Array.isArray(novoObjetivo.resultadosChaves) ? novoObjetivo.resultadosChaves.join(', ') : ''} 
          onChange={(e) => setNovoObjetivo({ 
            ...novoObjetivo, 
            resultadosChaves: e.target.value.split(',').map((id) => Number(id.trim())) 
          })} 
          className={styles.input} 
        />

        <button type="submit" className={styles.botao}>Atualizar</button>
      </form>
    </div>
  );
}