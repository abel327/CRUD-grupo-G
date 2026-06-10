import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Barraca {
  id: number;
  nome: string;
  descricao: string;
  avaliacao: number;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class BarracaService {
  private apiUrl = `${environment.apiUrl}/barracas`;

  constructor(private http: HttpClient) {}

  getBarracas(): Observable<Barraca[]> {
    return this.http.get<Barraca[]>(this.apiUrl);
  }

  getBarracaById(id: number): Observable<Barraca> {
    return this.http.get<Barraca>(`${this.apiUrl}/${id}`);
  }
}
