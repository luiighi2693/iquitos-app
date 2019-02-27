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

export interface ProductFilterData {
  productSelected: Producto;
  valueInput: string;
  stockAdded: number;
  productsFiltered: Producto[];
  productSelectedFlag: boolean;
  index: number;
}

@Component({
  selector: 'app-purchase-update',
  templateUrl: './purchase-update.component.html',
  styleUrls: ['./purchase-update.component.scss']
})
export class PurchaseUpdateComponent extends BaseCompra implements OnInit {

  private editFlag: boolean;
  currentSearchProduct: string;
  // productos: IProducto[] = [];
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

  displayedColumnsProductos = ['producto', 'cantidad', 'precio', 'quitar'];
  dataSourceProductos = new MatTableDataSource<ProductFilterData>(null);

  productsToPurchase = new Map([]);
  productsToPurchaseIndex = 0;

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

        // this.productos = this.entity.productos;
        // this.dataSourceProductos = new MatTableDataSource<ICuentaProveedor>(this.productos);

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
        this.proveedorService.search({query:this.entityFilteredSelectedInputProveedor}).subscribe(
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
    if (label === 'valueInput') {
      // @ts-ignore
      this.productsToPurchase.get(indexProducto).valueInput = event.target.value;
    }

    if (label === 'stockAdded') {
      // @ts-ignore
      this.productsToPurchase.get(indexProducto).stockAdded = event.target.value;
    }

    // @ts-ignore
    this.dataSourceProductos = new MatTableDataSource<ProductFilterData>(Array.from(this.productsToPurchase.values()));
  }



  deleteProduct(i, row) {
    this.productsToPurchase.delete(i);
    // @ts-ignore
    this.dataSourceProductos = new MatTableDataSource<ProductFilterData>(Array.from(this.productsToPurchase.values()));
  }

  agregarProducto() {
    let productFilterData: ProductFilterData = {
      productSelected: null,
      productSelectedFlag: false,
      productsFiltered: [],
      stockAdded: 0,
      valueInput: '',
      index: this.productsToPurchaseIndex
    };

    this.productsToPurchase.set(this.productsToPurchaseIndex, productFilterData);
    this.productsToPurchaseIndex++;

    // @ts-ignore
    this.dataSourceProductos = new MatTableDataSource<ProductFilterData>(Array.from(this.productsToPurchase.values()));
  }

  filterEntityProducto(event, index, row) {
    // @ts-ignore
    if (event.target.value !== undefined) {
      // @ts-ignore
      if (event.target.value.length > 2) {
        // @ts-ignore
        this.productoService.search({query:event.target.value}).subscribe(
          (res: HttpResponse<IProducto[]>) => {
            // @ts-ignore
            this.productsToPurchase.get(index).productsFiltered = res.body;
            row.productsFiltered = res.body;

            this.removeProductSelected(res.body);
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      }
    }
  }

  clearEntitySelectedProducto(index) {
    this.productsToPurchase.set(index, {
      productSelected: null,
      productSelectedFlag: false,
      productsFiltered: [],
      stockAdded: 0,
      valueInput: '',
      index: index
    });

    // @ts-ignore
    this.dataSourceProductos = new MatTableDataSource<ProductFilterData>(Array.from(this.productsToPurchase.values()));
  }

  setEntityFilterProducto(index, option) {
    // @ts-ignore
    let productFilterData: ProductFilterData = this.productsToPurchase.get(index);

    this.productsToPurchase.set(index, {
      productSelected: option,
      productSelectedFlag: true,
      productsFiltered: productFilterData.productsFiltered,
      stockAdded: productFilterData.stockAdded,
      valueInput: option.nombre,
      index: index
    });

// @ts-ignore
    this.dataSourceProductos = new MatTableDataSource<ProductFilterData>(Array.from(this.productsToPurchase.values()));
  }

  removeProductSelected(products: Producto[]){
    this.productsToPurchase.forEach(value =>{
      // @ts-ignore
      let product: Producto = value.productSelected;
      if (product) {
        if (products.map(x=>x.id).indexOf(product.id) !== -1){
          products.splice(products.map(x=>x.id).indexOf(product.id), 1);
        }
      }
    });
  }
}
