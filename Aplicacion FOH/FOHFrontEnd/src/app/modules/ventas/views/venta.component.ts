import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Response } from '../../../models/response';
import { Paginacion } from '../../../models/paginacion';
import { Venta }  from '../../../models/venta';
import { VentaRequest }  from '../../../models/venta-request';
import { esLocale } from 'ngx-bootstrap/locale';
import { BsLocaleService } from 'ngx-bootstrap/datepicker';
import { defineLocale } from 'ngx-bootstrap/chronos';
import { VentaService } from '../../../services/venta-service';

@Component({
    selector: 'venta',
    templateUrl: 'venta.component.template.html',
    styleUrls: ['venta.component.scss'],
    providers: [VentaService]
  })

  export class VentaComponent implements OnInit {
    ventas: Venta[] = new Array<Venta>();
    venta: Venta = new Venta();
    request: VentaRequest = new VentaRequest();
    selectedRow: number = 0;
    selectedObject: Venta = new Venta();;
    loading: boolean = false;
  
    public paginacion: Paginacion = new Paginacion;
   
    constructor(private localeService: BsLocaleService,
        private router: Router, private service: VentaService) {
        this.loading = false;
        this.selectedRow = -1;
        this.ventas = [];
      }
   
    ngOnInit(){
        this.request.fecha = new Date().toISOString();
        this.request.pagina = 1;
        this.request.registros = 10;

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
  }