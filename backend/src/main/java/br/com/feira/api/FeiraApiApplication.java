package br.com.feira.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.feira.api.domain.StatusPedido;
import br.com.feira.api.repository.StatusPedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.util.Arrays;

/**
 * Ponto de entrada da aplicação Spring Boot.
 * Sprint 1 — Ambiente e Backend Inicial.
 */
@SpringBootApplication
public class FeiraApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeiraApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(StatusPedidoRepository statusPedidoRepository) {
        return args -> {
            if (statusPedidoRepository.count() == 0) {
                statusPedidoRepository.saveAll(Arrays.asList(
                        new StatusPedido("Recebido"),
                        new StatusPedido("Preparando"),
                        new StatusPedido("Pronto para Retirada"),
                        new StatusPedido("A Caminho"),
                        new StatusPedido("Entregue")));
            }
        };
    }
}
