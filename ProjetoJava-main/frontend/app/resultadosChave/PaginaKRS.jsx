"use client"
import Link from "next/link";
import styles from "./PaginaKRS.module.css";
import { useEffect, useState } from "react";

export default function PaginaKRS() {
  // Criação do estado para armazenar os resultadosChave
  const [resultadosChave, setResultadosChave] = useState([]);
  const [selectedId, setSelectedId] = useState("Todos");

  // Função para chamar a API e buscar os resultadosChave
  async function chamarAPIResultadosChave() {
    const URL = "http://localhost:8080/resultadosChave";
    try {
      const resp = await fetch(URL);
      if (resp.status === 200) {
        const resultadosChave = await resp.json(); // Resposta da API
        setResultadosChave(resultadosChave); // aqui salva os dados no estado
        console.log(resultadosChave)
      } else {
        console.error('Erro ao buscar iniciativas:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Função para dar o delete de algum resultados chave
  async function chamarAPIDeleteResultadosChave(id) { // Precisa receber como parâmetro qual id o usuário quer apagar
    const URL = `http://localhost:8080/resultadosChave/${id}`;
    try {
      const resp = await fetch(URL, {
        method: "DELETE",
      });

      if (resp.ok) {
        alert("Resultado Chave deletado com sucesso!");
        chamarAPIResultadosChave(); // da o get novamente para recarregar a página
      } else {
        console.error('Erro ao dar delete de um resultado chave:', resp.status);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  }

  // Função para desassociar uma iniciativa
    async function chamarAPIDesassociarIni(id_kr, id_ini) {
      const URL = `http://localhost:8080/resultadosChave/${id_kr}/desassociarIni/${id_ini}`;
      try {
        const resp = await fetch(URL, { 
          method: 'DELETE' 
        });
        if (resp.status === 200 || resp.status === 204) {
          alert("Iniciativa desassociada com sucesso!");
          setIdIniciativaDesassociar('');
          chamarAPIResultadosChave();
        } else {
          console.error('Erro ao desassociar iniciativa:', resp.status);
        }
      } catch (error) {
        console.error('Erro na requisição:', error);
      }
    }

  // Hook useEffect para chamar a API assim que o componente for montado (isso faz com que não precise chamar toda hora a API pra recarregar a pagina)
  useEffect(() => {
    chamarAPIResultadosChave();
  }, []);

  // Função para filtrar pelo select
  const filtragem = (e) => {
    setSelectedId(e.target.value);
  };

  return (
    <div className="fundo">
      <h1 className={styles.KRS}>Resultados Chave</h1>
      <Link href="/CadastroKRS">
        <button className={styles.add_button}>+</button>
      </Link>
      <strong>
        <p className={styles.add_obj}>Adicionar Resultado Chave</p>
      </strong>

      {/*KR 1*/}
      {resultadosChave.filter(resultadosChave => selectedId === "Todos" || resultadosChave.id_kr === Number(selectedId)).map((resultadosChave) => (
      <div className={styles.card}>
        <div className={styles.header}>
          <div className={styles.buttons}>
            <Link href="/AtualizarKRS">
              {" "}
              <button>Editar</button>
            </Link>
            <button onClick={() => chamarAPIDeleteResultadosChave(resultadosChave.id_kr)}>Excluir</button>
          </div>
        </div>

        <div className={styles.kr}>
          <strong>ID:</strong>{resultadosChave.id_kr}
          <div className={styles.kr_titulo}>
            {resultadosChave.descricao} <span> {resultadosChave.meta}</span>{" "}
            <span className={styles.porcentagem_kr}>{resultadosChave.porcentagem_conclusao_kr}%</span>
          </div>

          <div className={styles.progress_bar_kr}>
            <div className={styles.progress} style={{ width: `${resultadosChave.porcentagem_conclusao_kr}%` }}></div>
          </div>
          {/* Map para Cada Iniciativa de cada KR */}
          <ul className={styles.lista_bola}>
             {resultadosChave.iniciativas.map((iniciativa) => (
              <li className={styles.lista_num}>
              <p className={styles.ini_titulo}>-{iniciativa.titulo}</p>
              <span className={styles.porcentagem_ini}>{iniciativa.porcentagem_conclusao_iniciativa}%</span>
              {/* Campo adicional para desassociar uma iniciativa */}
              <div className={styles.button_desa}>
                <button onClick={() => chamarAPIDesassociarIni(resultadosChave.id_kr, iniciativa.id_iniciativas)}>Desassociar</button>
              </div>
              <div className={styles.progress_bar_ini}>
                <div className={styles.progress} style={{ width: `${iniciativa.porcentagem_conclusao_iniciativa}%` }}></div>
              </div>
              </li>
              ))}
          </ul>
        </div>
      </div>
      ))}
      {/* Filtragem */}
      <div className={styles.filtroContainer}>
        <h3 className={styles.filtroTitulo}>Filtrar por ID</h3>
        <select
          id="filtro"
          name="resultadosChave"
          onChange={filtragem}
          value={selectedId}
          className={styles.select}
        >
          <option key="Todos" value="Todos">Todos</option>
          {resultadosChave.map((resultadosChave) => (
            <option key={resultadosChave.id_kr} value={resultadosChave.id_kr}>
              {resultadosChave.id_kr}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
}
