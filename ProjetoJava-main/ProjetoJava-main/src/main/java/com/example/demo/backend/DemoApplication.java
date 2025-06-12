package com.example.demo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Classe principal da aplicação Spring Boot.
 * Responsável por inicializar e executar o contexto da aplicação.
 * 
 * Esta classe contém o método `main`, que é o ponto de entrada da aplicação.
 * Ela utiliza o Spring Boot para configurar automaticamente o ambiente
 * (componentes, beans, configurações, etc.) e iniciar a execução do sistema.
 * 
 * Após a inicialização do contexto, a aplicação também cria e executa
 * manualmente uma instância da classe {@link ConsoleApp}.
 * 
 */

@SpringBootApplication
public class DemoApplication {
    /**
     * Método principal da aplicação.
     * Inicia o contexto Spring e executa a lógica da aplicação via {@link ConsoleApp}.
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext aplicacao = SpringApplication.run(DemoApplication.class);

        // Instânciando a ConsoleApp
        ConsoleApp consoleApp = new ConsoleApp();
        consoleApp.iniciar();
    }
}
