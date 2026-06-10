import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { CarrinhoService, ItemCarrinho } from '../../services/carrinho.service';
import { PedidoService } from '../../services/pedido.service';
import { Pedido, ItemPedidoDTO } from '../../models/pedido.model';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {
  itens: ItemCarrinho[] = [];
  valorTotal: number = 0;
  nomeCliente: string = '';

  constructor(
    private carrinhoService: CarrinhoService,
    private pedidoService: PedidoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carrinhoService.itens$.subscribe(itens => {
      this.itens = itens;
      this.valorTotal = this.carrinhoService.getValorTotal();
    });
  }

  removerItem(produtoId: number): void {
    this.carrinhoService.removerItem(produtoId);
  }

  aumentarQtd(produtoId: number, qtd: number): void {
    this.carrinhoService.atualizarQuantidade(produtoId, qtd + 1);
  }

  diminuirQtd(produtoId: number, qtd: number): void {
    this.carrinhoService.atualizarQuantidade(produtoId, qtd - 1);
  }

  finalizarPedido(): void {
    if (this.itens.length === 0) {
      alert('Seu carrinho está vazio!');
      return;
    }
    
    if (!this.nomeCliente.trim()) {
      alert('Por favor, informe seu nome para o pedido.');
      return;
    }

    const novoPedido: Pedido = {
      nome: `Pedido de ${this.nomeCliente}`,
      statusPedidoId: 1, // 1 = Recebido
      itens: this.itens.map(i => ({
        produtoId: i.produto.id,
        quantidade: i.quantidade
      }))
    };

    this.pedidoService.salvar(novoPedido).subscribe({
      next: (res) => {
        alert('Pedido realizado com sucesso! ID: ' + res.id);
        this.carrinhoService.limparCarrinho();
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao realizar o pedido.');
      }
    });
  }
}
