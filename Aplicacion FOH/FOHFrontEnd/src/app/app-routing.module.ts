import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentaComponent } from './modules/ventas/views/venta.component';

const routes: Routes = [
  { path: '', component: VentaComponent},
  { path: 'heroes', component: VentaComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }