package com.example.demo.backend.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.backend.Entidades.ResultadosChave;

/**
 * Interface que serve como um repositório da classe {@link ResultadosChave}, ele é responsável por armazenar os métodos que serão usados na execução do CRUD no banco de dados.
 * Usamos nele a anotação "@Repository" que faz com que o Spring entenda que essa classe é um repositório e cria essa implementação em runtime
 */
@Repository
public interface ResultadosChaveRepository extends JpaRepository<ResultadosChave, Long>{
}
