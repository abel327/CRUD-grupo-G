import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { BarracaComponent } from './pages/barraca/barraca.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { PainelFeiranteComponent } from './pages/painel-feirante/painel-feirante.component';
import { MeusPedidosComponent } from './pages/meus-pedidos/meus-pedidos.component';
import { AdminComponent } from './pages/admin/admin.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'meus-pedidos', component: MeusPedidosComponent },
  { path: 'barraca/:id', component: BarracaComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'painel-feirante', component: PainelFeiranteComponent },
  { path: '**', redirectTo: '' }
];
