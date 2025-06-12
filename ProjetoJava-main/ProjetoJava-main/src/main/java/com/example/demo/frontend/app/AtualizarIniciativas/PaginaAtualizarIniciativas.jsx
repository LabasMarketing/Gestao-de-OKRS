"use client";
import styles from "./PaginaAtualizarIniciativas.module.css";
import { useState } from "react";

export default function AtualizarIniciativas() {
  const [novaIniciativa, setNovaIniciativa] = useState({
      id_iniciativas: '', // String vaiza para o usuário digitar antes de atualizar
      titulo: '',
      descricao: '',
      porcentagem: ''
    });

  // Função só para atualizar via PUT
  async function chamarAPIPutIniciativas() {
    const URL = `http://localhost:8080/iniciativas/${novaIniciativa.id_iniciativas}`;
    try {
      const resp = await fetch(URL, {
      method: 'PUT', // O padrão vem como GET, por isso precisamos, nesse caso, especificar que o tipo que queremos é o PUT
      headers: {
      'Content-Type': 'application/json', // Informando que o conteúdo é JSON
      },
      body: JSON.stringify(novaIniciativa), // Passando os dados do novaIniciativa como corpo da requisição
      });
      if (resp.status === 200 || resp.status === 201) {
        alert("Iniciativa atualizada com sucesso!");
        setNovaIniciativa({ titulo: '', descricao: '', porcentagem: ''}); // Limpando o conteúdo / Voltando o estado da novaIniciativa para como era antes (vazio)
        await chamarAPIGetIniciativas();  // Atualiza a lista do GET mas agora com o novo item adicionado 
      } else {
        console.error('Erro ao cadastrar iniciativas:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
    }

  return (
    <div className="fundo">
      <h1 className={styles.iniciativas}>Iniciativas</h1>

      {/* Formulário para adicionar nova iniciativa */}
      <form id="formPost" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPutIniciativas(); }}>
        <h2 className={styles.titulo}>Atualizar Iniciativa</h2>

        <input type="number" placeholder="Digite o ID da iniciativa" value={novaIniciativa.id_iniciativas} onChange={(e) => setNovaIniciativa({ ...novaIniciativa, id_iniciativas: e.target.value })} className={styles.input} required/>

        <input id="titulo" type="text" placeholder="Digite o título" value={novaIniciativa.titulo} onChange={(e) => setNovaIniciativa({ ...novaIniciativa, titulo: e.target.value })} className={styles.input} />

        <input id="descricao" type="text" placeholder="Digite a descrição" value={novaIniciativa.descricao} onChange={(e) => setNovaIniciativa({ ...novaIniciativa, descricao: e.target.value })} className={styles.input} />

        <input id = "porcentagem" type="number" placeholder="Digite o numero da porcentagem de conclusão" value={novaIniciativa.porcentagem} onChange={(e) => setNovaIniciativa({ ...novaIniciativa, porcentagem: e.target.value })} className={styles.input}/>

        <button type="submit" className={styles.botao}>Atualizar</button>
      </form>
    </div>
  );
}
