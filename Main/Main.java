package feira.graspcrud;

import feira.graspcrud.controller.PedidoController;
import feira.graspcrud.repository.PedidoRepository;
import feira.graspcrud.repository.StatusPedidoRepository;
import feira.graspcrud.repository.json.PedidoRepositoryJson;
import feira.graspcrud.repository.json.StatusPedidoRepositoryJson;
import feira.graspcrud.service.PedidoService;
import feira.graspcrud.service.StatusPedidoService;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StatusPedidoRepository statusRepo = new StatusPedidoRepositoryJson(Path.of("status.json"));
        PedidoRepository pedidoRepo = new PedidoRepositoryJson(Path.of("pedidos.json"));

        StatusPedidoService statusService = new StatusPedidoService(statusRepo, pedidoRepo);
        PedidoService pedidoService = new PedidoService(pedidoRepo, statusRepo);

        try (Scanner sc = new Scanner(System.in)) {
            new PedidoController(pedidoService, statusService, sc).iniciarMenu();
        }
    }
}