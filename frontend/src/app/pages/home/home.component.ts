import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Categoria, CategoriaService } from '../../services/categoria.service';
import { Barraca, BarracaService } from '../../services/barraca.service';
import { Produto, ProdutoService } from '../../services/produto.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  categorias: Categoria[] = [];
  barracas: Barraca[] = [];
  ofertas: Produto[] = [];

  constructor(
    private categoriaService: CategoriaService,
    private barracaService: BarracaService,
    private produtoService: ProdutoService
  ) {}

  ngOnInit(): void {
    this.categoriaService.getCategorias().subscribe(data => this.categorias = data);
    this.barracaService.getBarracas().subscribe(data => this.barracas = data);
    this.produtoService.getOfertas().subscribe(data => this.ofertas = data);
  }
}
