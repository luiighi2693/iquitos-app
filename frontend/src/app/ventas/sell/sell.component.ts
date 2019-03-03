import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {FullService} from '../../layouts/full/full.service';
import {VentaService} from "./venta.service";
import {BaseVenta} from "./BaseVenta";

declare var require: any;

@Component({
  selector: 'app-sell',
  templateUrl: './sell.component.html',
  styleUrls: ['./sell.component.scss']
})
export class SellComponent extends BaseVenta implements OnInit, OnDestroy {

  constructor(public service: VentaService, public fullService: FullService, public dialog: MatDialog) {
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
}
