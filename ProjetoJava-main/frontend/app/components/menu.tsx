"use client";
import Link from "next/link";
import "../globals.css";

export default function Menu() {
  return (
    <header>
      <Link href="/" className="titulo">
        <h1 className="tituloTexto">GESTÃO DE OKRS</h1>
      </Link>
      <i className="fa-solid fa-user-plus icon"></i>
      <p className="login">log in</p>
      <nav className="nav">
        <ul className="navbar">
          <li>
            <Link href="/">Home</Link>
          </li>
          <li>
            <Link href="/PaginaSobreSite">Sobre o site</Link>
          </li>
          <li>
            <Link href="/objetivos">Objetivos</Link>
          </li>
          <li>
            <Link href="/resultadosChave">Resultados Chave</Link>
          </li>
          <li>
            <Link href="/Iniciativas">Iniciativas</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}
