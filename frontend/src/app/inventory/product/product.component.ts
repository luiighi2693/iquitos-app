import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {FullService} from '../../layouts/full/full.service';
import {ProductoService} from "./producto.service";
import {BaseProducto} from "./BaseProducto";

declare var require: any;

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent extends BaseProducto implements OnInit, OnDestroy {

  constructor(public service: ProductoService, public fullService: FullService, public dialog: MatDialog) {
    super(
      service,
      fullService,
      ['select', 'codigo', 'descripcion', 'stock', 'star'],
      dialog, null, null);
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
  }
}
