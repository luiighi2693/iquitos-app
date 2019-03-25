import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {FullService} from '../../layouts/full/full.service';
import {VentaService} from "./venta.service";
import {BaseVenta} from "./BaseVenta";
import {IVenta, Venta} from "../../models/venta.model";
import {Router} from "@angular/router";
import {HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {ITipoDeDocumento} from "../../models/tipo-de-documento.model";

declare var require: any;

@Component({
  selector: 'app-sell',
  templateUrl: './sell.component.html',
  styleUrls: ['./sell.component.scss']
})
export class SellComponent extends BaseVenta implements OnInit, OnDestroy {
  public entitySelected: Venta = undefined;
  public actions: string[] = ['Editar', 'Eliminar'];
  public status: string[] = ['Pendiente', 'Completado'];
  public action: string = undefined;
  statusSelected: string = undefined;

  dateRange: any;

  constructor(public service: VentaService, public fullService: FullService, public dialog: MatDialog, public router: Router) {
    super(
      service,
      fullService,
      ['codigo', 'fecha', 'clienteNombre', 'total', 'estado'],
      dialog, null, null, require('../menu.json'), 'VENTAS');
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
  }

  selectEntity(row: Venta) {
    this.entitySelected = row;
    this.action = undefined;
  }

  doAction($event) {
    if ($event === 'Editar') {
      this.router.navigate(['/ventas/sell', this.entitySelected.id, 'edit']);
      // this.router.navigate(['/universal-service/address-date'], { replaceUrl: true });
    } else {
      this.openDialog(this.entitySelected);
    }
  }

  search(query){
    this.statusSelected = undefined;
    this.dateRange = undefined;
    super.search(query);
  }

  searchByStatus(est: string) {
    console.log(est);

    this.service
      .search({
        query: est,
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IVenta[]>) => this.paginate(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  searchByRangeDates(range: any) {
    console.log(range);

    this.service.searchByDateRange(range).subscribe(
      (res: HttpResponse<IVenta[]>) => this.paginate(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  paginate(data: Venta[], headers: HttpHeaders) {
    console.log(data);
    data.sort((a, b) =>  b.id - a.id);
    super.paginate(data, headers);
  }
}
