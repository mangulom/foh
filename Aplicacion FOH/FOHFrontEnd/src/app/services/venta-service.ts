import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Response} from '../models/response';
import {Observable} from 'rxjs';
import { VentaRequest } from '../models/venta-request';
import { Venta } from '../models/venta';

@Injectable({
    providedIn: 'root',
  })

  export class VentaService {
    private apiEndpointCab: string;
    private apiEndpointDet: string;
    private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  
    constructor(public http: HttpClient) {
      this.apiEndpointCab = environment.apiEndpoint + '/ventas';
      this.apiEndpointDet = environment.apiEndpoint + '/detalle-venta';

    }

    buscarVentas(requestVenta: VentaRequest): Observable<Response>{
        return this.http.post<Response>(this.apiEndpointCab,requestVenta);
    }

    detalleVentas(requestVenta: Venta): Observable<Response>{
      return this.http.post<Response>(this.apiEndpointDet,requestVenta);
  }
  }