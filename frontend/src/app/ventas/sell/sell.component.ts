import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {FullService} from '../../layouts/full/full.service';
import {VentaService} from "./venta.service";
import {BaseVenta} from "./BaseVenta";
import {Venta} from "../../models/venta.model";
import {Router} from "@angular/router";

declare var require: any;

@Component({
  selector: 'app-sell',
  templateUrl: './sell.component.html',
  styleUrls: ['./sell.component.scss']
})
export class SellComponent extends BaseVenta implements OnInit, OnDestroy {
  public entitySelected: Venta = undefined;
  public actions: string[] = ['Editar', 'Eliminar'];
  public action: string = undefined;

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
}
