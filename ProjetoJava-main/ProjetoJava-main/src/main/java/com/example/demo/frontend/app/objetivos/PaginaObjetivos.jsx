"use client"
import Link from "next/link";
import styles from "./PaginaObjetivos.module.css";
import { useEffect, useState } from "react";

export default function PaginaObjetivos() {
  // Criação do estado para armazenar os Objetivos
  const [objetivos, setObjetivos] = useState([]);
  const [selectedId, setSelectedId] = useState("Todos");

  // Função para chamar a API e buscar os Objetivos
  async function chamarAPIObjetivo() {
    const URL = "http://localhost:8080/objetivos";
    try {
      const resp = await fetch(URL);
      if (resp.status === 200) {
        const data = await resp.json();
        console.log("Objetivos recebidos:", data);
        setObjetivos(data);
      } else {
        console.error('Erro ao buscar objetivos:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Função para dar o delete de algum objetivo 
  async function chamarAPIDeleteObjetivo(id) { // Precisa receber como parâmetro qual id o usuário quer apagar
    const URL = `http://localhost:8080/objetivos/${id}`;
    try {
      const resp = await fetch(URL, {
        method: "DELETE",
      });

      if (resp.ok) {
        alert("Objetivo deletado com sucesso!");
        chamarAPIObjetivo(); // da o get novamente para recarregar a página
      } else {
        console.error('Erro ao dar delete de um objetivo:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Função para desassociar um Resultado Chave
    async function chamarAPIDesassociarKR(id, id_kr) {
      const URL = `http://localhost:8080/objetivos/${id}/desassociarKR/${id_kr}`;
      try {
        const resp = await fetch(URL, { 
          method: 'DELETE' 
        });
        if (resp.status === 200 || resp.status === 204) {
          alert("Resultado Chave desassociado com sucesso!");
          setIdResultadosChaveDesassociar('');
          chamarAPIObjetivo();
        } else {
          console.error('Erro ao desassociar Resultado Chave:', resp.status);
        }
      } catch (error) {
        console.error('Erro na requisição:', error);
      }
    }

  useEffect(() => {
    chamarAPIObjetivo();
  }, []);

  const filtragem = (e) => {
    setSelectedId(e.target.value);
  };

  return (
    <div className="fundo">
      <h1 className={styles.objetivos}>Objetivos</h1>

      <Link href="/CadastroObjetivos">
        <button className={styles.add_button}>+</button>
      </Link>

      <strong>
        <p className={styles.add_obj}>Adicionar Objetivo</p>
      </strong>

      {/* Map para cada Objetivo*/}
      {objetivos.filter((objetivo) => selectedId === "Todos" || objetivo.id === Number(selectedId)).map((objetivo) => (
      <div className={styles.card} key={objetivo.id}>
        <div className={styles.header}>
          <div className={styles.buttons}>
            <Link href="/AtualizarObjetivos"><button>Editar</button></Link>
            <button onClick={() => chamarAPIDeleteObjetivo(objetivo.id)}>Excluir</button>
          </div>
        </div>
        <strong>ID: </strong>{objetivo.id}
        <div className={styles.objective_title}>
          {objetivo.titulo} | {objetivo.descricao} <span className="percentage">{objetivo.porcentagem_conclusao_obj}%</span>
        </div>
        <div className={styles.progress_bar_obj}>
          <div className={styles.progress} style={{ width: `${objetivo.porcentagem_conclusao_obj}%` }}></div>
        </div>
        {objetivo.resultadosChaves.map((resultadoChave) => (
          <div className={styles.kr} key={resultadoChave.id_kr}>
            <div className={styles.kr_titulo}>
              {resultadoChave.descricao} <span> {resultadoChave.meta}</span>{" "}
              <span className={styles.porcentagem_kr}>{resultadoChave.porcentagem_conclusao_kr}%</span>
            </div>
            <div className={styles.progress_bar_kr}>
              <div className={styles.progress} style={{ width: `${resultadoChave.porcentagem_conclusao_kr}%` }}></div>
            </div>
            {/* Campo adicional para desassociar uma iniciativa */}
            <div className={styles.button_desa}>
              <button onClick={() => chamarAPIDesassociarKR(objetivo.id, resultadoChave.id_kr)}>Desassociar</button>
            </div>
            <ul className={styles.lista_bola}>
             {resultadoChave.iniciativas.map((iniciativas) => (
              <li className={styles.lista_num} key={iniciativas.id_iniciativas}>
                <p className={styles.ini_titulo}>-{iniciativas.titulo}</p>
                <span className={styles.porcentagem_ini}>{iniciativas.porcentagem_conclusao_iniciativa}%</span>
                <div className={styles.progress_bar_ini}>
                  <div className={styles.progress} style={{ width: `${iniciativas.porcentagem_conclusao_iniciativa}%` }}></div>
                </div>
              </li>
              ))}
          </ul>
          </div>
        ))}
      </div>
    ))}
      {/* Filtragem */}
        <div className={styles.filtroContainer}>
          <h3 className={styles.filtroTitulo}>Filtrar por ID</h3>
          <select
            id="filtro"
            name="objetivo"
            onChange={filtragem}
            value={selectedId}
            className={styles.select}
          >
            <option key="Todos" value="Todos">Todos</option>
            {objetivos.map((objetivo) => (
              <option key={objetivo.id} value={objetivo.id}>
                {objetivo.id}
              </option>
            ))}
          </select>
        </div>
    </div>
  );
}
