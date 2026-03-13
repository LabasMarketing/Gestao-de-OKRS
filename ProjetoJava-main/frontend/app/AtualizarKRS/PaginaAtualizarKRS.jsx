"use client";
import styles from "./PaginaAtualizarKRS.module.css";
import { useState } from "react";

export default function AtualizarKRS() {
  const [novoResultadoChave, setNovoResultadoChave] = useState({
        id_kr: '', // String vaiza para o usuário digitar antes de atualizar
        descricao: '',
        meta: '',
        objetivoId: '',
        iniciativas: []
      });
  
  const [idIniciativaDesassociar, setIdIniciativaDesassociar] = useState('');
  
    // Função só para atualizar via PUT
    async function chamarAPIPutKRS() {
      const URL = `http://localhost:8080/resultadosChave/${novoResultadoChave.id_kr}`;
      try {
        const resp = await fetch(URL, {
        method: 'PUT', // O padrão vem como GET, por isso precisamos, nesse caso, especificar que o tipo que queremos é o PUT
        headers: {
        'Content-Type': 'application/json', // Informando que o conteúdo é JSON
        },
        body: JSON.stringify(novoResultadoChave), // Passando os dados do novoResultadoChave como corpo da requisição
        });
        if (resp.status === 200 || resp.status === 201) {
          alert("Resultado Chave atualizado com sucesso!");
          setNovoResultadoChave({ id_kr:'', descricao: '',  meta: '', objetivoId: '', iniciativas: ''}); // Limpando o conteúdo / Voltando o estado da novaIniciativa para como era antes (vazio)
          await chamarAPIGetResultadosChave();  // Atualiza a lista do GET mas agora com o novo item adicionado 
        } else {
          console.error('Erro ao cadastrar iniciativas:', resp.status);
        }
      } catch (error) {
        console.error('Erro na requisição:', error);
      }
      }

  return (
    <div className="fundo">
      <h1 className={styles.KRS}>Resultados Chave</h1>

      {/* Formulário para adicionar novo Resultado Chave */}
      <form id="formPutResultadoChave" className={styles.formulario} onSubmit={(e) => { e.preventDefault(); chamarAPIPutKRS(); }}>
        <h2 className={styles.titulo}>Atualizar Resultado Chave</h2>

        <input id="id_kr" type="number" placeholder="Digite o ID do Resultado Chave" value={novoResultadoChave.id_kr} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, id_kr: e.target.value })} className={styles.input} required/>

        <input id="descricao" type="text" placeholder="Digite a descrição" value={novoResultadoChave.descricao} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, descricao: e.target.value })} className={styles.input} />

        <input id="meta" type="text" placeholder="Digite a meta" value={novoResultadoChave.meta} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, meta: e.target.value })} className={styles.input} />

        <input id="objetivo" type="text" placeholder="Digite o objetivo" value={novoResultadoChave.objetivoId} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, objetivoId: e.target.value })} className={styles.input} />

        <input id="iniciativas" type="text" placeholder="Digite o ID da iniciativa/s" value={novoResultadoChave.iniciativas} onChange={(e) => setNovoResultadoChave({ ...novoResultadoChave, iniciativas: e.target.value.split(',').map((id) => Number(id.trim()))})} className={styles.input} />

        <button type="submit" className={styles.botao}>Atualizar</button>
      </form>
    </div>
  );
}
