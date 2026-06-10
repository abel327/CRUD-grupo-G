import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StatusPedidoService } from '../../services/status-pedido.service';
import { StatusPedido } from '../../models/status-pedido.model';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {
  statusList: StatusPedido[] = [];
  loading: boolean = true;
  
  // Create / Edit Form state
  showForm: boolean = false;
  formStatus: Partial<StatusPedido> = { nome: '' };
  isEditing: boolean = false;
  isSubmitting: boolean = false;

  constructor(private statusPedidoService: StatusPedidoService) {}

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.loading = true;
    this.statusPedidoService.listarTodos().subscribe({
      next: (dados) => {
        this.statusList = dados;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar status', err);
        this.loading = false;
      }
    });
  }

  iniciarCriacao(): void {
    this.showForm = true;
    this.isEditing = false;
    this.formStatus = { nome: '' };
  }

  iniciarEdicao(status: StatusPedido): void {
    this.showForm = true;
    this.isEditing = true;
    // deep copy
    this.formStatus = { ...status };
  }

  cancelarForm(): void {
    this.showForm = false;
    this.formStatus = { nome: '' };
    this.isEditing = false;
  }

  salvar(): void {
    if (!this.formStatus.nome || this.formStatus.nome.trim().length === 0) {
      alert('O nome do status não pode ser vazio.');
      return;
    }

    this.isSubmitting = true;

    if (this.isEditing && this.formStatus.id) {
      this.statusPedidoService.atualizar(this.formStatus.id, this.formStatus as StatusPedido).subscribe({
        next: (atualizado) => {
          const idx = this.statusList.findIndex(s => s.id === atualizado.id);
          if (idx !== -1) {
            this.statusList[idx] = atualizado;
          }
          this.isSubmitting = false;
          this.cancelarForm();
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao atualizar o status.');
          this.isSubmitting = false;
        }
      });
    } else {
      this.statusPedidoService.salvar(this.formStatus as StatusPedido).subscribe({
        next: (criado) => {
          this.statusList.push(criado);
          this.isSubmitting = false;
          this.cancelarForm();
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao criar o status.');
          this.isSubmitting = false;
        }
      });
    }
  }

  excluir(id: number | undefined): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir este status? Essa ação não pode ser desfeita.')) {
      this.statusPedidoService.excluir(id).subscribe({
        next: () => {
          this.statusList = this.statusList.filter(s => s.id !== id);
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao excluir o status. Ele pode estar em uso por algum pedido.');
        }
      });
    }
  }
}
