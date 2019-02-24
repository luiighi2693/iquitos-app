import {Component, ElementRef, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder, Validators} from '@angular/forms';
import {JhiDataUtils} from 'ng-jhipster';
import {MatDialog, MatTableDataSource} from "@angular/material";
import {BaseCompra} from "./BaseCompra";
import {ProductoService} from "../../inventory/product/producto.service";
import {FullService} from "../../layouts/full/full.service";
import {CompraService} from "./compra.service";
import {ICompra, PaymentPurchaseType, PurchaseLocation, PurchaseStatus} from "../../models/compra.model";
import {ITipoDeDocumentoDeCompra, TipoDeDocumentoDeCompra} from "../../models/tipo-de-documento-de-compra.model";
import {TipoDeDocumentoDeCompraService} from "../../configuration/documenttypepurchase/tipo-de-documento-de-compra.service";
import {IProducto, Producto} from "../../models/producto.model";
import {ProveedorService} from "../../contact/provider/proveedor.service";
import {IProveedor} from "../../models/proveedor.model";
import {IContactoProveedor} from "../../models/contacto-proveedor.model";
import {AccountTypeProvider, CuentaProveedor, ICuentaProveedor} from "../../models/cuenta-proveedor.model";

declare var require: any;

export interface ProductStockAdded {
  productId: number;
  stockAdded: number;
}

@Component({
  selector: 'app-purchase-update',
  templateUrl: './purchase-update.component.html',
  styleUrls: ['./purchase-update.component.scss']
})
export class PurchaseUpdateComponent extends BaseCompra implements OnInit {

  private editFlag: boolean;
  currentSearchProduct: string;
  productos: IProducto[] = [];
  productosStockAdded: ProductStockAdded[] = [];
  ubicacionList: PurchaseLocation[] = [
    PurchaseLocation.TIENDA
  ];
  tiposDeDocumentosDeCompras: TipoDeDocumentoDeCompra[] = [];
  tipoDePagoDeCompraList: PaymentPurchaseType[] = [
    PaymentPurchaseType.CONTADO,
    PaymentPurchaseType.CREDITO
  ];

  entityFilteredSelectedProveedor = null;
  entityFilteredSelectedInputProveedor: string;
  entitiesFilteredProveedor = [];
  entitySelectFlagProveedor = false;

  entityFilteredSelectedProducto = null;
  entityFilteredSelectedInputProducto: string;
  entitiesFilteredProducto = [];
  entitySelectFlagProducto = false;

  displayedColumnsProductos = ['producto', 'cantidad', 'precio', 'quitar'];
  dataSourceProductos = new MatTableDataSource<IProducto>(null);

  constructor(public dataUtils: JhiDataUtils,
              public service: CompraService,
              public tipoDeDocumentoDeCompraService: TipoDeDocumentoDeCompraService,
              public fullService: FullService,
              public productoService: ProductoService,
              public proveedorService: ProveedorService,
              public activatedRoute: ActivatedRoute,
              public elementRef: ElementRef,
              public fb: FormBuilder,
              public dialog: MatDialog,
              private router: Router) {
    super(service, fullService,null,dialog, dataUtils, elementRef, require('../menu.json'), 'COMPRAS');
  }

  ngOnInit() {
    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;

      if (this.entity.id) {
        console.log(this.entity);
        this.editFlag = true;

        this.proveedorService.find(this.entity.proveedorId).subscribe(
          (res: HttpResponse<IProveedor>) => {
            this.setEntityFilterProveedor(res.body)
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.productos = this.entity.productos;
        this.dataSourceProductos = new MatTableDataSource<ICuentaProveedor>(this.productos);

      } else {
        this.entity.tipoDePagoDeCompra = PaymentPurchaseType.CONTADO;
        this.entity.ubicacion = PurchaseLocation.TIENDA;
        this.entity.montoTotal = 0;
        this.entity.metaData = '{}';
        this.entity.estatus = PurchaseStatus.PENDIENTE;
      }
    });

    // this.productoService.query().subscribe(
    //   (res: HttpResponse<ITipoDeDocumento[]>) => {
    //     this.productos = res.body;
    //     if (this.productos.length > 15){
    //       this.productos = this.productos.slice(0,14);
    //     }
    //   },
    //   (res: HttpErrorResponse) => this.onError(res.message)
    // );

    // this.proveedorService.query().subscribe(
    //   (res: HttpResponse<IProveedor[]>) => {
    //     this.proveedores = res.body;
    //   },
    //   (res: HttpErrorResponse) => this.onError(res.message)
    // );

    this.tipoDeDocumentoDeCompraService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumentoDeCompra[]>) => {
        this.tiposDeDocumentosDeCompras = res.body;
        if (this.tiposDeDocumentosDeCompras.length > 0){
          if (this.entity.id === undefined) {
            this.entity.tipoDeDocumentoDeCompraId = this.tiposDeDocumentosDeCompras[0].id;
          }
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    //
    // this.form = this.fb.group({
    //   proveedor: [
    //     null,
    //     Validators.compose([
    //       Validators.required
    //     ])
    //   ],
    //   numeroDeDocumento: [
    //     null,
    //     Validators.compose([
    //       Validators.required
    //     ])
    //   ],
    //   fecha: [
    //     null,
    //     Validators.compose([
    //       Validators.required
    //     ])
    //   ]
    // });
  }

  save() {
    this.updateEntity();
    super.save();
  }

  public subscribeToSaveResponse(result: Observable<HttpResponse<ICompra>>) {
    result.subscribe((res: HttpResponse<ICompra>) => this.onSaveSuccessCustom(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  public onSaveSuccessCustom(purchase) {
    this.isSaving = false;
  }

  public updateEntity() {
  }

  //*****************************************************PROVEEDOR**///////////////////////////////////////////

  setEntityFilterProveedor(option) {
    this.entityFilteredSelectedInputProveedor = option.razonSocial;
    this.entitySelectFlagProveedor = true;

    this.entity.proveedorId = option.id;
    this.entityFilteredSelectedProveedor = option;
  }

  clearEntitySelectedProveedor() {
    this.entitySelectFlagProveedor = false;
    this.entityFilteredSelectedProveedor = null;
    this.entityFilteredSelectedInputProveedor = undefined;

    this.entity.proveedorId = undefined;
  }

  filterEntityProveedor() {
    if (!this.entitySelectFlagProveedor && this.entityFilteredSelectedInputProveedor !== undefined) {
      if (this.entityFilteredSelectedInputProveedor.length > 2) {
        this.proveedorService.query(this.entityFilteredSelectedInputProveedor).subscribe(
          (res: HttpResponse<IProveedor[]>) => {
            this.entitiesFilteredProveedor = res.body;
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        // this.entitiesFilteredProveedor = this.proveedores.filter(x => x.razonSocial.includes(this.entityFilteredSelectedInputProveedor))
      }
    }
  }

  onBlurProveedor($event: FocusEvent) {
    console.log($event);
    if(!this.entitySelectFlagProveedor){
      this.clearEntitySelectedProveedor();
    } else {
      if (this.entity.proveedorId !== undefined) {
        let proveedor = this.entitiesFilteredProveedor.find(x => x.id === this.entity.proveedorId);
        this.setEntityFilterProveedor(proveedor);
      }
    }
  }

  //********************************************************PRODUCTOS*****************************************//


  saveChangesInProductsRow(event, indexProducto, label) {
    if (label === 'nombre') {
      this.productos[indexProducto].nombre = event.target.value;
    }

    if (label === 'cantidad') {
      this.productos[indexProducto].stock = event.target.value;
    }

    if (label === 'precioCompra') {
      this.productos[indexProducto].precioCompra = event.target.value;
    }

    this.dataSourceProductos = new MatTableDataSource<ICuentaProveedor>(this.productos);
  }



  deleteProduct(i, row) {
    this.dataSourceProductos.data.splice(i, 1);
    this.dataSourceProductos = new MatTableDataSource<IProducto>(this.dataSourceProductos.data);
    this.productos = this.dataSourceProductos.data;
  }

  agregarProducto() {
    const producto = new Producto();

    if (this.productos === undefined) {
      this.productos = [];
    }

    this.productos.push(producto);
    this.dataSourceProductos = new MatTableDataSource<ICuentaProveedor>(this.productos);
  }
  //
  // filterEntityProducto(index) {
  //   if (!this.entitySelectFlagProducto && this.entityFilteredSelectedInputProducto !== undefined) {
  //     if (this.entityFilteredSelectedInputProducto.length > 2) {
  //       this.productoService.query(this.entityFilteredSelectedInputProducto).subscribe(
  //         (res: HttpResponse<IProducto[]>) => {
  //           this.entitiesFilteredProducto = res.body;
  //         },
  //         (res: HttpErrorResponse) => this.onError(res.message)
  //       );
  //     }
  //   }
  // }
  //
  // clearEntitySelectedProducto(i) {
  //   this.entitySelectFlagProducto = false;
  //   this.entityFilteredSelectedProducto = null;
  //   this.entityFilteredSelectedInputProducto = undefined;
  //
  //   this.productos[i] = new Producto();
  // }
  //
  // onBlurProducto(index) {
  //   if(!this.entitySelectFlagProducto){
  //     this.clearEntitySelectedProducto(index);
  //   } else {
  //     if (this.entity.proveedorId !== undefined) {
  //       let proveedor = this.entitiesFilteredProducto.find(x => x.id === this.entity.proveedorId);
  //       this.setEntityFilterProducto(proveedor);
  //     }
  //   }
  // }
}
