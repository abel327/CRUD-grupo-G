import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Categoria } from './categoria.service';
import { Barraca } from './barraca.service';

export interface Produto {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  unidade: string;
  emOferta: boolean;
  imageUrl: string;
  categoria?: Categoria;
  barraca?: Barraca;
}

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private apiUrl = `${environment.apiUrl}/produtos`;

  constructor(private http: HttpClient) {}

  getOfertas(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/ofertas`);
  }

  getProdutosPorBarraca(barracaId: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/barraca/${barracaId}`);
  }
}
