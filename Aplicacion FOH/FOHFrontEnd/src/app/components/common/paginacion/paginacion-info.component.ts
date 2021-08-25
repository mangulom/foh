import { Component, Input } from '@angular/core';

@Component({
  selector: 'pagination-info',
  template: `
      <small *ngIf="totalRows > 1; else elseOneResults">Se encontraron <strong>{{totalRows}}</strong> resultados.<br>
        Mostrando resultados del {{minIndex}} al {{maxIndex}}.</small>
      <ng-template #elseOneResults><small *ngIf="totalRows == 1; else elseNoResults">Se encontró un resultado.</small></ng-template>
      <ng-template #elseNoResults><small>No se encontraron resultados.</small></ng-template>
  `
})
export class PaginacionInfoComponent {

  minIndex?: number=0;
  maxIndex?: number=0;
  pages: number=0;
  totalRows: number = 100;
  @Input('totalItems')
  rows: number = 0;
  @Input('itemsPerPage')

  _page?: number=0;
  @Input('page')
  set page(page: number) {
    this._page = page;
  }

  constructor() {}

}
