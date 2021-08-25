import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ModalManager } from 'ngb-modal';
import { DetalleVenta } from './models/detalle-venta';
import { Paginacion } from './models/paginacion';
import { Venta }  from './models/venta';
import { VentaRequest }  from './models/venta-request';
import { VentaService } from './services/venta-service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  detalleVenta: DetalleVenta[] = new Array<DetalleVenta>();
  ventas: Venta[] = new Array<Venta>();
  venta: Venta = new Venta();
  request: VentaRequest = new VentaRequest();
  selectedRow: number = 0;
  selectedObject: Venta = new Venta();;
  loading: boolean = false;
  totalVenta: number = 0;

  public paginacion: Paginacion = new Paginacion;
  title = 'fohApplication';
  private modalRef: any;
  @ViewChild('myModal') myModal: any;

  constructor(
    private service: VentaService,
    private modalService: ModalManager) {
    this.loading = false;
    this.selectedRow = -1;
    this.ventas = [];
  }

  

ngOnInit(){
    this.request.id = 0;
    this.request.fecha = new Date().toISOString().substr(8,2) + "/" + new Date().toISOString().substr(5,2) + "/" + new Date().toISOString().substring(0,4);
    this.request.pagina = 1;
    this.request.registros = 10;
    this.getVentas();

};

getVentas(): void {
  this.loading = true;
  this.service.buscarVentas(this.request).subscribe(
      response => {
          this.ventas = response.resultado;
          this.paginacion = response.paginacion;
          this.loading = false;
      }
  )
}

OnRowClick(i: any, item: any) {
  console.log("nada");
}

abrirModal(item: Venta) {
        this.totalVenta = 0;
        this.service.detalleVentas(item).subscribe(
          response => {
            this.detalleVenta = response.resultado;
            for( let e=0; e<this.detalleVenta.length; e++) {
              this.totalVenta = this.totalVenta + (this.detalleVenta[e].precio * this.detalleVenta[e].cantidad);
            }
            this.modalRef = this.modalService.open(this.myModal, {
              size: "lg",
              modalClass: 'mymodal',
              hideCloseButton: false,
              centered: false,
              backdrop: true,
              animation: true,
              keyboard: false,
              closeOnOutsideClick: true,
              backdropClass: "modal-backdrop"
          })
          }
        )

}
closeModal(){
  this.modalService.close(this.modalRef);
}
}
