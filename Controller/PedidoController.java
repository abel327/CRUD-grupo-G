package feira.graspcrud.controller;

import feira.graspcrud.domain.Pedido;
import feira.graspcrud.domain.StatusPedido;
import feira.graspcrud.dto.PedidoRequest;
import feira.graspcrud.dto.StatusPedidoRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.service.PedidoService;
import feira.graspcrud.service.StatusPedidoService;

import java.util.List;
import java.util.Scanner;

public class PedidoController {
    private final PedidoService pedidoService;
    private final StatusPedidoService statusService;
    private final Scanner scanner;

    public PedidoController(PedidoService pedidoService, StatusPedidoService statusService, Scanner scanner) {
        this.pedidoService = pedidoService;
        this.statusService = statusService;
        this.scanner = scanner;
    }

    public void iniciarMenu() {
        boolean executando = true;
        while (executando) {
            System.out.println("\n--- Gestão de Feira Livre ---");
            System.out.println("1. Cadastrar StatusPedido");
            System.out.println("2. Listar StatusPedido");
            System.out.println("3. Cadastrar Pedido");
            System.out.println("4. Listar Pedido");
            System.out.println("5. Buscar Pedido por id");
            System.out.println("6. Atualizar Pedido");
            System.out.println("7. Excluir Pedido");
            System.out.println("8. Excluir StatusPedido");
            System.out.println("9. Sair");
            System.out.print("Opcao: ");
            
            String opcao = scanner.nextLine();
            try {
                switch (opcao) {
                    case "1" -> cadastrarStatus();
                    case "2" -> listarStatus();
                    case "3" -> cadastrarPedido();
                    case "4" -> listarPedidos();
                    case "5" -> buscarPedido();
                    case "6" -> atualizarPedido();
                    case "7" -> excluirPedido();
                    case "8" -> excluirStatus();
                    case "9" -> executando = false;
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (RegraNegocioException e) {
                System.out.println("Erro de Regra: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private void cadastrarStatus() {
        System.out.print("Nome do status: ");
        String nome = scanner.nextLine();
        StatusPedido sp = statusService.criar(new StatusPedidoRequest(nome));
        System.out.println("Status cadastrado com ID: " + sp.getId());
    }

    private void listarStatus() {
        statusService.listarTodos().forEach(s -> 
            System.out.printf("- ID: %d | Nome: %s%n", s.getId(), s.getNome()));
    }

    private void cadastrarPedido() {
        listarStatus();
        System.out.print("Nome do pedido: ");
        String nome = scanner.nextLine();
        System.out.print("ID do Status: ");
        long statusId = Long.parseLong(scanner.nextLine());
        Pedido p = pedidoService.criar(new PedidoRequest(nome, statusId));
        System.out.println("Pedido criado: " + p.getId());
    }

    private void listarPedidos() {
        pedidoService.listarTodos().forEach(p -> {
            StatusPedido s = statusService.buscarPorId(p.getStatusPedidoId());
            System.out.printf("- ID: %d | Nome: %s | Status: %s%n", p.getId(), p.getNome(), s.getNome());
        });
    }
    
    // ... implementar métodos buscarPedido, atualizarPedido, etc., seguindo a lógica acima
    private void buscarPedido() {
        System.out.print("ID do pedido: ");
        long id = Long.parseLong(scanner.nextLine());
        Pedido p = pedidoService.buscarPorId(id);
        System.out.println("Pedido: " + p.getNome() + " (Status ID: " + p.getStatusPedidoId() + ")");
    }

    private void excluirPedido() {
        System.out.print("ID: ");
        long id = Long.parseLong(scanner.nextLine());
        pedidoService.remover(id);
        System.out.println("Sucesso.");
    }

    private void excluirStatus() {
        System.out.print("ID: ");
        long id = Long.parseLong(scanner.nextLine());
        statusService.remover(id);
        System.out.println("Sucesso.");
    }
}