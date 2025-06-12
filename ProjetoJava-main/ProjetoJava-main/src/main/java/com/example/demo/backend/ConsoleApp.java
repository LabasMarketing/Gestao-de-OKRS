package com.example.demo.backend;

import org.springframework.stereotype.Component;

/**
 * Classe principal de inicialização da aplicação console.
 * 
 * Esta classe representa um componente gerenciado pelo Spring que é usado para
 * executar uma lógica inicial quando a aplicação é iniciada.
 * 
 * Atualmente, ela imprime uma mensagem de sucesso no console, mas pode ser
 * expandida para testes.
 * 
 */
@Component
public class ConsoleApp {

    /**
     * Construtor padrão (vázio) da classe ConsoleApp
     */
    public ConsoleApp() {

    }

    /**
     * Método personalizável para executar quando a aplicação for inicializada.
     */
    public void iniciar() {
        System.out.println("Projeto está rodando com sucesso!");
    }
}
