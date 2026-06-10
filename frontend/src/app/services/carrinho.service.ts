import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Produto } from './produto.service';

export interface ItemCarrinho {
  produto: Produto;
  quantidade: number;
}

@Injectable({
  providedIn: 'root'
})
export class CarrinhoService {
  private itensSubject = new BehaviorSubject<ItemCarrinho[]>([]);
  itens$ = this.itensSubject.asObservable();

  constructor() {
    // Inicializar do localStorage caso exista (Opcional, omitido aqui para manter simples)
  }

  adicionarItem(produto: Produto, quantidade: number = 1) {
    const itensAtuais = this.itensSubject.value;
    const itemExistente = itensAtuais.find(i => i.produto.id === produto.id);

    if (itemExistente) {
      itemExistente.quantidade += quantidade;
      this.itensSubject.next([...itensAtuais]);
    } else {
      this.itensSubject.next([...itensAtuais, { produto, quantidade }]);
    }
  }

  removerItem(produtoId: number) {
    const itensAtuais = this.itensSubject.value.filter(i => i.produto.id !== produtoId);
    this.itensSubject.next(itensAtuais);
  }

  atualizarQuantidade(produtoId: number, quantidade: number) {
    if (quantidade <= 0) {
      this.removerItem(produtoId);
      return;
    }
    
    const itensAtuais = this.itensSubject.value;
    const itemExistente = itensAtuais.find(i => i.produto.id === produtoId);
    
    if (itemExistente) {
      itemExistente.quantidade = quantidade;
      this.itensSubject.next([...itensAtuais]);
    }
  }

  limparCarrinho() {
    this.itensSubject.next([]);
  }

  getValorTotal(): number {
    return this.itensSubject.value.reduce((total, item) => total + (item.produto.preco * item.quantidade), 0);
  }
  
  getQuantidadeTotal(): number {
    return this.itensSubject.value.reduce((total, item) => total + item.quantidade, 0);
  }
  
  getItensAtuais(): ItemCarrinho[] {
    return this.itensSubject.value;
  }
}
