import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {BaseVenta} from "./BaseVenta";
import {MatTableDataSource} from "@angular/material";
import {ProductoDetalle} from "../../models/producto-detalle.model";
import {Amortizacion} from "../../models/amortizacion.model";

declare var require: any;

@Component({
  selector: 'app-sell-detail',
  templateUrl: './sell-detail.component.html',
  styleUrls: ['./sell-detail.component.scss']
})
export class SellDetailComponent extends BaseVenta implements OnInit {

  displayedColumnsProductosDetalles = ['cantidad', 'producto', 'precioVenta', 'precioTotal'];
  displayedColumnsAmortizaciones = ['codigo', 'fecha', 'glosa', 'metodo', 'importe', 'comprobante', 'imprimir'];
  dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(null);
  dataSourceAmortizaciones = new MatTableDataSource<Amortizacion>(null);

  constructor(private activatedRoute: ActivatedRoute) {
    super(null,null,null,null, null, null, require('../menu.json'), 'VENTAS');
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;
      this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.entity.productoDetalles);
      this.dataSourceAmortizaciones = new MatTableDataSource<Amortizacion>(this.entity.amortizacions);
    });
  }

}
