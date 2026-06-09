import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StatusPedido } from '../models/status-pedido.model';

@Injectable({
  providedIn: 'root'
})
export class StatusPedidoService {

  private apiUrl = 'http://localhost:8080/api/status-pedido';

  constructor(private http: HttpClient) { }

  listarTodos(): Observable<StatusPedido[]> {
    return this.http.get<StatusPedido[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<StatusPedido> {
    return this.http.get<StatusPedido>(`${this.apiUrl}/${id}`);
  }

  salvar(statusPedido: StatusPedido): Observable<StatusPedido> {
    return this.http.post<StatusPedido>(this.apiUrl, statusPedido);
  }

  atualizar(id: number, statusPedido: StatusPedido): Observable<StatusPedido> {
    return this.http.put<StatusPedido>(`${this.apiUrl}/${id}`, statusPedido);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
