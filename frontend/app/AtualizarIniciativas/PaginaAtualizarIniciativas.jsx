"use client";
import styles from "./PaginaAtualizarIniciativas.module.css";
import { useState, useEffect } from "react";

export default function AtualizarIniciativas() {
  const [novaIniciativa, setNovaIniciativa] = useState({
    id_iniciativas: '', 
    titulo: '',
    descricao: '',
    porcentagem: ''
  });

  const BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://192.168.0.90:25000";
  
  // Nova Função que busca dados pelo id
  useEffect(() => {
    async function buscarDadosPorId() {
      const id = novaIniciativa.id_iniciativas;
      if (!id) return;

      try {
        const response = await fetch(`${BASE_URL}/iniciativas/${id}`);
        
        if (response.ok) {
          const dados = await response.json(); 

          // 'prev' para manter o ID que está no campo enquanto preenchemos o resto
          setNovaIniciativa(prev => ({
            ...prev,
            titulo: dados.titulo || '',
            descricao: dados.descricao || '',
            porcentagem: dados.porcentagem_conclusao_iniciativa || ''
          }));
        } else {
          // Se o ID não existe limpa os campos para não mostrar dados errados
          setNovaIniciativa(prev => ({ ...prev, titulo: '', descricao: '', porcentagem: '' }));
        }
      } catch (error) {
        console.error("Erro ao buscar iniciativa:", error);
      }
    }

    buscarDadosPorId(); // <--- Nome corrigido aqui para bater com a função acima
  }, [novaIniciativa.id_iniciativas]);

  // Função original de PUT
  async function chamarAPIPutIniciativas() {
    const URL = `${BASE_URL}/iniciativas/${novaIniciativa.id_iniciativas}`;
    try {
      const resp = await fetch(URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novaIniciativa),
      });

      if (resp.status === 200 || resp.status === 201) {
        alert("Iniciativa atualizada com sucesso!");
        setNovaIniciativa({ id_iniciativas: '', titulo: '', descricao: '', porcentagem: '' });
      } else {
        console.error('Erro ao atualizar:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  return (
    <div className="fundo">
      <h1 className={styles.iniciativas}>Iniciativas</h1>

      <form id="formPost" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPutIniciativas(); }}>
        <h2 className={styles.titulo}>Atualizar Iniciativa</h2>

        <input 
          type="number" 
          placeholder="Digite o ID da iniciativa" 
          value={novaIniciativa.id_iniciativas} 
          onChange={(e) => {
            const valor = e.target.value;
            if (valor === "") {
              // Se apagou o ID reseta o objeto todo
              setNovaIniciativa({ id_iniciativas: '', titulo: '', descricao: '', porcentagem: '' });
            } else {
              // Se está digitando apenas atualiza o ID
              setNovaIniciativa({ ...novaIniciativa, id_iniciativas: valor });
            }
          }} 
          className={styles.input} 
          required
        />

        <input 
          id="titulo" 
          type="text" 
          placeholder="Digite o título" 
          value={novaIniciativa.titulo} 
          onChange={(e) => setNovaIniciativa({ ...novaIniciativa, titulo: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="descricao" 
          type="text" 
          placeholder="Digite a descrição" 
          value={novaIniciativa.descricao} 
          onChange={(e) => setNovaIniciativa({ ...novaIniciativa, descricao: e.target.value })} 
          className={styles.input} 
        />

        <input 
          id="porcentagem" 
          type="number" 
          placeholder="Digite o numero da porcentagem de conclusão" 
          value={novaIniciativa.porcentagem} 
          onChange={(e) => setNovaIniciativa({ ...novaIniciativa, porcentagem: e.target.value })} 
          className={styles.input}
        />

        <button type="submit" className={styles.botao}>Atualizar</button>
      </form>
    </div>
  );
}