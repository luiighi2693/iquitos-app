import {Component, ElementRef, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder, Validators} from '@angular/forms';
import {IVenta} from "../../models/venta.model";
import {VentaService} from "./venta.service";
import { JhiDataUtils } from 'ng-jhipster';
import {MatTableDataSource} from "@angular/material";
import {BaseVenta} from "./BaseVenta";
import {IProducto} from "../../models/producto.model";
import {ProductoService} from "../../inventory/product/producto.service";
import {IVariante} from "../../models/variante.model";

export interface ProductoDetalle {
  cantidad: number;
  producto: string;
  precioVenta: number;
}

@Component({
  selector: 'app-sell-update',
  templateUrl: './sell-update.component.html',
  styleUrls: ['./sell-update.component.scss']
})
export class SellUpdateComponent extends BaseVenta implements OnInit {

  private editFlag: boolean;
  currentSearchProduct: any;
  productos: IProducto[];
  displayedColumnsProductosDetalles = ['cantidad', 'producto', 'precioVenta', 'quitar'];

  productosDetalles: ProductoDetalle[]  = [
    {
      cantidad: 2,
      producto: 'harina',
      precioVenta: 4
    }
  ];
  dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.productosDetalles);

  constructor(public dataUtils: JhiDataUtils,
              public service: VentaService,
              public productoService: ProductoService,
              public activatedRoute: ActivatedRoute,
              public elementRef: ElementRef,
              public fb: FormBuilder) {
    super(service, null,null,null, dataUtils, elementRef);
  }

  ngOnInit() {
    console.log(this.productosDetalles);

    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;

      this.form = this.fb.group({
      });

      if (this.entity.id) {
        console.log(this.entity);
        this.editFlag = true;

      } else {
      }
    });
  }

  save() {
    this.updateEntity();
    super.save();
  }

  public subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
    result.subscribe((res: HttpResponse<IVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  public onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  public updateEntity() {
    if (this.entity.codigo === undefined) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }

    if (this.entity.codigo.length === 0) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }
  }

  searchProduct(query) {
    if (!query) {
      return this.clear();
    } else {
      this.productoService.search({
        query: query,
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      }).subscribe(
        (res: HttpResponse<IProducto[]>) => {
          this.productos = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }


  saveChangesInProductosDetallesRow($event: FocusEvent, i: any, producto: string) {
    
  }

  deleteProductosDetalles(i: any, row: any) {
    
  }
}
