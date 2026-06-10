import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PedidoService } from '../../services/pedido.service';
import { StatusPedidoService } from '../../services/status-pedido.service';
import { Pedido, ItemPedidoDTO } from '../../models/pedido.model';
import { StatusPedido } from '../../models/status-pedido.model';

@Component({
  selector: 'app-meus-pedidos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './meus-pedidos.component.html',
  styleUrl: './meus-pedidos.component.css'
})
export class MeusPedidosComponent implements OnInit {
  pedidos: Pedido[] = [];
  statusList: StatusPedido[] = [];
  
  pedidoEditando: Pedido | null = null;
  loading: boolean = true;

  constructor(
    private pedidoService: PedidoService,
    private statusPedidoService: StatusPedidoService
  ) {}

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.loading = true;
    this.statusPedidoService.listarTodos().subscribe(status => {
      this.statusList = status;
      this.pedidoService.listarTodos().subscribe(pedidos => {
        this.pedidos = pedidos;
        this.loading = false;
      });
    });
  }

  editarPedido(pedido: Pedido): void {
    // Clone para edição local (deep copy simples)
    this.pedidoEditando = JSON.parse(JSON.stringify(pedido));
  }

  cancelarEdicao(): void {
    this.pedidoEditando = null;
  }

  removerItem(index: number): void {
    if (this.pedidoEditando && this.pedidoEditando.itens) {
      this.pedidoEditando.itens.splice(index, 1);
    }
  }

  salvarPedido(): void {
    if (this.pedidoEditando && this.pedidoEditando.id) {
      this.pedidoService.atualizar(this.pedidoEditando.id, this.pedidoEditando).subscribe({
        next: (atualizado) => {
          // Atualizar lista
          const index = this.pedidos.findIndex(p => p.id === atualizado.id);
          if (index !== -1) {
            this.pedidos[index] = atualizado;
          }
          this.pedidoEditando = null;
          // Recarregar os dados do servidor para ter certeza que o cálculo final está batendo
          this.carregarDados();
        },
        error: (err) => {
          console.error('Erro ao atualizar pedido:', err);
          alert('Erro ao atualizar o pedido.');
        }
      });
    }
  }

  getNomeStatus(statusId: number): string {
    const s = this.statusList.find(x => x.id === statusId);
    return s ? s.nome : 'Desconhecido';
  }
}
