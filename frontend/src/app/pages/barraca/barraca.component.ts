import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Barraca, BarracaService } from '../../services/barraca.service';
import { Produto, ProdutoService } from '../../services/produto.service';
import { CarrinhoService } from '../../services/carrinho.service';

@Component({
  selector: 'app-barraca',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './barraca.component.html',
  styleUrl: './barraca.component.css'
})
export class BarracaComponent implements OnInit {
  barraca: Barraca | null = null;
  produtos: Produto[] = [];

  constructor(
    private route: ActivatedRoute,
    private barracaService: BarracaService,
    private produtoService: ProdutoService,
    private carrinhoService: CarrinhoService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      const barracaId = Number(id);
      this.barracaService.getBarracaById(barracaId).subscribe(data => this.barraca = data);
      this.produtoService.getProdutosPorBarraca(barracaId).subscribe(data => this.produtos = data);
    }
  }

  adicionarAoCarrinho(produto: Produto): void {
    this.carrinhoService.adicionarItem(produto, 1);
    alert(`${produto.nome} foi adicionado ao carrinho!`);
  }
}
