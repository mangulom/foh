import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {VentaComponent} from './modules/ventas/views/venta.component';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {NgSelectModule} from '@ng-select/ng-select';
import {ToastrModule} from 'ngx-toastr';
import {PaginacionModule} from './components/common/paginacion/paginacion.module';
import { HttpClientModule } from '@angular/common/http';
import { ModalModule } from 'ngb-modal';

@NgModule({
  declarations: [
    AppComponent,
    VentaComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    RouterModule,
    BsDatepickerModule,
    BsDropdownModule,
    PaginacionModule,
    PaginationModule,
    TooltipModule,
    NgSelectModule,
    ToastrModule,
    HttpClientModule,
    ModalModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
