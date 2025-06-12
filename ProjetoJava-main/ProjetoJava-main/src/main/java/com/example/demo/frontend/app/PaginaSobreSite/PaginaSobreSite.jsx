import React from "react";
import styles from "./PaginaSobreSite.module.css";

export default function SobreSite() {
  return (
    <div className="fundo">
      <h2 className={styles.tit}>Sobre o Site</h2>
      <p className={styles.texto}>
        A ideia de criar um site de gestão de OKRs nasceu de uma conversa entre
        três amigos Gabriel, Gustavo e Caio que enfrentavam dificuldades em
        definir metas claras e acompanhar seu progresso de forma prática. Cada
        um, com suas experiências distintas, percebeu que esse era um problema
        comum, tanto para eles quanto para muitas equipes e empresas. Decidiram
        então criar uma plataforma intuitiva e funcional que ajudasse a
        transformar objetivos em resultados concretos. Para isso, estudaram o
        modelo OKR, conversaram com profissionais de várias áreas e uniram suas
        habilidades: Caio focou na experiência do usuário, Gustavo no
        desenvolvimento técnico e Gabriel no design e usabilidade. Assim, o site
        foi criado para oferecer clareza, simplicidade e eficiência, atendendo a
        uma necessidade real de organização, alinhamento e conquista de metas.
      </p>
    </div>
  );
}
