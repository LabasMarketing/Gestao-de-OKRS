"use client";
import styles from "./PaginaAtualizarKRS.module.css";
import { useState, useEffect } from "react"; // 1. Importamos o useEffect

export default function AtualizarKRS() {
  const [novoResultadoChave, setNovoResultadoChave] = useState({
    id_kr: '', 
    descricao: '',
    meta: '',
    objetivoId: '',
    iniciativas: []
  });

  const BASE_URL = "/api";

  // Nova Função que busca dados pelo id
  useEffect(() => {
    async function buscarKR() {
      const id = novoResultadoChave.id_kr;

      if (!id) {
        // 'prev' para manter o ID que está no campo enquanto preenchemos o resto
        setNovoResultadoChave(prev => ({ 
          ...prev, 
          descricao: '', 
          meta: '', 
          objetivoId: '', 
          iniciativas: [] 
        }));
        return;
      }

      try {
        // Ajuste a URL para bater com seu endpoint (vi que é /resultadosChave no print)
        const response = await fetch(`${BASE_URL}/resultadosChave/${id}`);
        
        if (response.ok) {
          const dados = await response.json();
          
          setNovoResultadoChave(prev => ({
            ...prev,
            descricao: dados.descricao || '',
            meta: dados.meta || '',
            // O objetivo é um objeto, então pegamos o ID dele
            objetivoId: dados.objetivo ? dados.objetivo.id : '',
            // Pegamos apenas os IDs das iniciativas para o campo de texto
            iniciativas: dados.iniciativas ? dados.iniciativas.map(ini => ini.id_iniciativas) : []
          }));
        } else {
          // Se não achar, limpa os campos
          setNovoResultadoChave(prev => ({ ...prev, descricao: '', meta: '', objetivoId: '', iniciativas: [] }));
        }
      } catch (error) {
        console.error("Erro ao buscar KR:", error);
      }
    }

    buscarKR();
  }, [novoResultadoChave.id_kr]); // Vigia o ID da KR

  // Função original de PUT
  async function chamarAPIPutKRS() {
    const URL = `${BASE_URL}/resultadosChave/${novoResultadoChave.id_kr}`;
    try {
      const resp = await fetch(URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoResultadoChave),
      });

      if (resp.status === 200 || resp.status === 201) {
        alert("Resultado Chave atualizado com sucesso!");
        setNovoResultadoChave({ id_kr: '', descricao: '', meta: '', objetivoId: '', iniciativas: [] });
      } else {
        console.error('Erro ao atualizar:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  return (
    <div className="fundo">
      <h1 className={styles.KRS}>Resultados Chave</h1>

      <form id="formPutResultadoChave" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPutKRS(); }}>
        <h2 className={styles.titulo}>Atualizar Resultado Chave</h2>

        <input 
          id="id_kr" 
          type="number" 
          placeholder="Digite o ID do Resultado Chave" 
          value={novoResultadoChave.id_kr} 
          onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, id_kr: e.target.value })} 
          className={styles.input} 
          required
        />

        <input 
          id="descricao" 
          type="text" 
          placeholder="Digite a descrição" 
          value={novoResultadoChave.descricao} 
          onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, descricao: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="meta" 
          type="text" 
          placeholder="Digite a meta" 
          value={novoResultadoChave.meta} 
          onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, meta: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="objetivo" 
          type="text" 
          placeholder="Digite o ID do objetivo" 
          value={novoResultadoChave.objetivoId} 
          onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, objetivoId: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="iniciativas" 
          type="text" 
          placeholder="IDs das iniciativas (ex: 1, 2, 3)" 
          value={novoResultadoChave.iniciativas.join(', ')} 
          onChange={(e) => setNovoResultadoChave({ 
            ...novoResultadoChave, 
            iniciativas: e.target.value.split(',').map((id) => Number(id.trim()))
          })} 
          className={styles.input} 
        />

        <button type="submit" className={styles.botao}>Atualizar</button>
      </form>
    </div>
  );
}