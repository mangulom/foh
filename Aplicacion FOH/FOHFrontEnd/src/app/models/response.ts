type Estados = 'OK'|'ERROR';

export class Error {
  codigo: String = "";
  mensaje: String = "";
  mensajeInterno: String = "";
}

/* export class Response {
  estado: Estados;
  paginacion: any;
  acciones?: string[];
  error?: Error;
  resultado?: any;
} */

/*add interface response*/
export interface Response {
  estado: Estados;
  paginacion: any;
  acciones?: string[];
  error?: Error;
  resultado?: any;
}
