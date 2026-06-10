import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PedidoService } from './services/pedido.service';
import { Pedido } from './models/pedido.model';
import { StatusPedidoService } from './services/status-pedido.service';
import { StatusPedido } from './models/status-pedido.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'Gestão de Feira Livre';
  
  pedidos: Pedido[] = [];
  statusList: StatusPedido[] = [];

  // Form Model
  novoPedido: Partial<Pedido> = {
    nome: '',
    statusPedidoId: undefined
  };

  isSubmitting = false;
  mensagemSucesso = '';
  mensagemErro = '';

  // Estado de edição
  editandoId: number | null = null;
  statusEdicaoId: number | undefined = undefined;

  constructor(
    private pedidoService: PedidoService,
    private statusPedidoService: StatusPedidoService
  ) {}

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados() {
    this.statusPedidoService.listarTodos().subscribe({
      next: (dados) => this.statusList = dados,
      error: (err) => console.error('Erro ao buscar status', err)
    });

    this.carregarPedidos();
  }

  carregarPedidos() {
    this.pedidoService.listarTodos().subscribe({
      next: (dados) => this.pedidos = dados,
      error: (err) => console.error('Erro ao buscar pedidos', err)
    });
  }

  criarPedido() {
    if (!this.novoPedido.nome || this.novoPedido.nome.trim().length < 3) {
      this.mensagemErro = 'O nome do pedido deve ter ao menos 3 caracteres.';
      this.mensagemSucesso = '';
      return;
    }

    if (!this.novoPedido.statusPedidoId) {
      this.mensagemErro = 'Por favor, selecione o status do pedido.';
      this.mensagemSucesso = '';
      return;
    }

    this.isSubmitting = true;
    this.mensagemErro = '';
    this.mensagemSucesso = '';

    this.pedidoService.salvar(this.novoPedido as Pedido).subscribe({
      next: (pedidoCriado) => {
        this.mensagemSucesso = `Pedido "${pedidoCriado.nome}" criado com sucesso!`;
        this.isSubmitting = false;
        // Limpa o form
        this.novoPedido = { nome: '', statusPedidoId: undefined };
        // Recarrega a lista
        this.carregarPedidos();
      },
      error: (err) => {
        console.error(err);
        this.mensagemErro = err.error?.mensagem || 'Erro ao criar pedido. Verifique os dados.';
        this.isSubmitting = false;
      }
    });
  }

  // Lógica de Edição
  iniciarEdicao(pedido: Pedido) {
    if (!pedido.id) return;
    this.editandoId = pedido.id;
    this.statusEdicaoId = pedido.statusPedidoId;
  }

  cancelarEdicao() {
    this.editandoId = null;
    this.statusEdicaoId = undefined;
  }

  salvarEdicao(pedido: Pedido) {
    if (!pedido.id || !this.statusEdicaoId) return;

    const pedidoAtualizado: Pedido = {
      ...pedido,
      statusPedidoId: this.statusEdicaoId
    };

    this.pedidoService.atualizar(pedido.id, pedidoAtualizado).subscribe({
      next: () => {
        this.mensagemSucesso = `Status do pedido "${pedido.nome}" atualizado!`;
        this.mensagemErro = '';
        this.cancelarEdicao();
        this.carregarPedidos();
      },
      error: (err) => {
        console.error(err);
        this.mensagemErro = err.error?.mensagem || 'Erro ao atualizar o pedido.';
        this.mensagemSucesso = '';
        this.cancelarEdicao();
      }
    });
  }
}
