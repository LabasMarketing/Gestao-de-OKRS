"use client"
import styles from "./PaginaCadastroIniciativas.module.css";
import { useState } from "react";

export default function CadastroIniciativas() {
  const [novaIniciativa, setNovaIniciativa] = useState({ // Criando um objeto vázio que serve como "esqueleto"
    titulo: '',
    descricao: ''
  });
  // Função para chamar a API e postar as iniciativas(POST)
  async function chamarAPIPostIniciativas() {
    const URL = "http://localhost:8080/iniciativas";
    try {
      const resp = await fetch(URL,{
      method: 'POST',  // O padrão vem como GET, por isso precisamos, nesse caso, especificar que o tipo que queremos é o post
      headers: {
      'Content-Type': 'application/json', // Informando que o conteúdo é JSON
      },
      body: JSON.stringify(novaIniciativa), // Passando os dados do novaIniciativa como corpo da requisição   
      });
      if (resp.status === 200 || resp.status === 201) {
        alert("Iniciativa cadastrada com sucesso!");
        setNovaIniciativa({ titulo: '', descricao: '' }); // Limpando o conteúdo / Voltando o estado da novaIniciativa para como era antes (vazio)
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
      <form id="formPost" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPostIniciativas(); }}>
        <h2 className={styles.titulo}>Cadastrar Nova Iniciativa</h2>

        <input id="titulo" type="text" placeholder="Digite o título" value={novaIniciativa.titulo} onChange={(e) => setNovaIniciativa({ ...novaIniciativa, titulo: e.target.value })} className={styles.input} />

        <input id="descricao" type="text" placeholder="Digite a descrição" value={novaIniciativa.descricao} onChange={(e) => setNovaIniciativa({ ...novaIniciativa, descricao: e.target.value })} className={styles.input} />

        <button type="submit" className={styles.botao}>Cadastrar</button>
      </form>
    </div>
  );
}
