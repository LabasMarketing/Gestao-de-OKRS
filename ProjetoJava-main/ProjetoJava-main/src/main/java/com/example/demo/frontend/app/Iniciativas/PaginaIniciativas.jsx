"use client";
import Link from "next/link";
import styles from "./PaginaIniciativas.module.css";
import { useEffect, useState } from "react";

export default function PaginaIniciativas() {
  // Criação do estado para armazenar as iniciativas
  const [iniciativas, setIniciativas] = useState([]);
  const [selectedId, setSelectedId] = useState("Todos");

  // Função para chamar a API e buscar as iniciativas
  async function chamarAPIIniciativas() {
    const URL = "http://localhost:8080/iniciativas";
    try {
      const resp = await fetch(URL);
      if (resp.status === 200) {
        const iniciativas = await resp.json(); // Resposta da API
        setIniciativas(iniciativas); // aqui salva os dados no estado
        console.log(iniciativas)
      } else {
        console.error('Erro ao buscar iniciativas:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Função para dar o delete de alguma iniciativa iniciativa(DELETE)
  async function chamarAPIDeleteIniciativa(id) { // Precisa receber como parâmetro qual id o usuário quer apagar
    const URL = `http://localhost:8080/iniciativas/${id}`;
    try {
      const resp = await fetch(URL, {
        method: "DELETE",
      });

      if (resp.ok) {
        alert("Iniciativa deletada com sucesso!");
        chamarAPIGetIniciativas(); // da o get novamente para recarregar a página
      } else {
        console.error('Erro ao dar delete de uma iniciativa:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Hook useEffect para chamar a API assim que o componente for montado (isso faz com que não precise chamar toda hora a API pra recarregar a pagina)
  useEffect(() => {
    chamarAPIIniciativas();
  }, []);

  {/*Função auxiliar na filtragem*/}
    const filtragem = (e) => {
      setSelectedId(e.target.value);
      console.log("ID Selecionado:", e.target.value); // Exibe o ID selecionado no console
    };

  return (
    <div className="fundo">
      <h1 className={styles.iniciativas}>Iniciativas</h1>
      <Link href="/CadastroIniciativas">
        <button className={styles.add_button}>+</button>
      </Link>
      <strong>
        <p className={styles.add_ini}>Adicionar Iniciativa</p>
      </strong>

      {/*Iniciativas*/}
      {iniciativas.filter(iniciativa => selectedId === "Todos" || iniciativa.id_iniciativas === Number(selectedId)).map((iniciativa) => (
      <div className={styles.card}>
        <div className={styles.header}>
          <div className={styles.buttons}>
            <Link href="/AtualizarIniciativas">
              <button>Editar</button>
            </Link>
            <button onClick={() => chamarAPIDeleteIniciativa(iniciativa.id_iniciativas)}>Excluir</button>
          </div>
        </div>
        <div className={styles.kr}>
          <ul className={styles.lista_bola}>
            <li className={styles.lista_num}>
              <strong>ID: </strong>{iniciativa.id_iniciativas}
            </li>
            <strong>Título: </strong>{iniciativa.titulo}
            <span className={styles.percentage}>{iniciativa.porcentagem_conclusao_iniciativa} %</span>
            <div className={styles.progress_bar_ini}>
              <div className={styles.progress} style={{ width: `${iniciativa.porcentagem_conclusao_iniciativa}%` }}></div>
            </div>
            <strong>Descrição: </strong>{iniciativa.descricao}
            <br></br>
            <br></br>
            <strong>Resultado Chave Id: </strong>{iniciativa.resultadosChaveId}
          </ul>
        </div>
      </div>
      ))}

      {/* Filtragem */}
      <div className={styles.filtroContainer}>
        <h3 className={styles.filtroTitulo}>Filtrar por ID</h3>
        <select
          id="filtro"
          name="iniciativa"
          onChange={filtragem}
          value={selectedId}
          className={styles.select}
        >
          <option key="Todos">Todos</option>
          {iniciativas.map((iniciativa) => (
            <option key={iniciativa.id_iniciativas}>{iniciativa.id_iniciativas}</option>
          ))}
        </select>
      </div>
    </div>
  );
}
