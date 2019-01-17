import {Component, ElementRef, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {IVenta} from "../../models/venta.model";
import {VentaService} from "./venta.service";
import {JhiDataUtils} from 'ng-jhipster';
import {MatTableDataSource} from "@angular/material";
import {BaseVenta} from "./BaseVenta";
import {IProducto} from "../../models/producto.model";
import {ProductoService} from "../../inventory/product/producto.service";
import {CivilStatus, Cliente, ClientType, ICliente, Sex} from "../../models/cliente.model";
import {ClienteService} from "../../contact/client/cliente.service";
import {ITipoDeDocumento} from "../../models/tipo-de-documento.model";
import {TipoDeDocumentoService} from "../../configuration/documenttype/tipo-de-documento.service";

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
  currentSearchProduct: string;
  currentSearchClient: string;
  productos: IProducto[];
  clientes: ICliente[];
  displayedColumnsProductosDetalles = ['cantidad', 'producto', 'precioVenta', 'quitar'];
  displayedColumnsClientes = ['name', 'action'];

  productosDetalles: ProductoDetalle[]  = [
    {
      cantidad: 2,
      producto: 'harina',
      precioVenta: 4
    }
  ];
  dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.productosDetalles);
  dataSourceClientes = new MatTableDataSource<Cliente>(this.clientes);
  stateStep: string = 'sell';

  client: Cliente = new Cliente();

  sexos: Sex[] = [
    Sex.FEMENINO,
    Sex.MASCULINO
  ];

  estatusCiviles: CivilStatus[] = [
    CivilStatus.CASADO,
    CivilStatus.SOLTERO
  ];

  tiposDeCliente: ClientType[] = [
    ClientType.JURIDICO,
    ClientType.NATURAL
  ];

  tiposDeDocumento: ITipoDeDocumento[];

  constructor(public dataUtils: JhiDataUtils,
              public service: VentaService,
              public productoService: ProductoService,
              public clienteService: ClienteService,
              private tiposDeDocumentoService: TipoDeDocumentoService,
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

    this.tiposDeDocumentoService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumento[]>) => {
        this.tiposDeDocumento = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.productoService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumento[]>) => {
        this.productos = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
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
      // return this.clear();
      this.productoService.query().subscribe(
        (res: HttpResponse<ITipoDeDocumento[]>) => {
          this.productos = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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

  searchClient(query) {
    if (!query) {
      // return this.clear();
      this.clienteService.query().subscribe(
        (res: HttpResponse<ITipoDeDocumento[]>) => {
          this.clientes = res.body;
          this.dataSourceClientes = new MatTableDataSource<Cliente>(this.clientes);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    } else {
      this.clienteService.search({
        query: query,
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      }).subscribe(
        (res: HttpResponse<IProducto[]>) => {
          this.clientes = res.body;
          this.dataSourceClientes = new MatTableDataSource<Cliente>(this.clientes);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }


  saveChangesInProductosDetallesRow($event: FocusEvent, i: any, producto: string) {
    
  }

  deleteProductosDetalles(i: any, row: any) {
    
  }

  editarCliente(row: Cliente) {
    console.log(row);
  }

  goStep(step: string, view: string, row: Cliente) {
    switch (step) {
      case 'sell': {
        this.stateStep = step;
        break;
      }

      case 'clientList': {
        this.stateStep = step;
        this.searchClient('');
        break;
      }

      case 'clientUpdate': {
        this.stateStep = step;
        if (view === 'new') {
          this.client = new Cliente();
        } else {
          this.client = row;
        }
        break;
      }
    }
  }

  setClient(row: Cliente) {
    this.client = row;
    this.goStep('sell', null, null);
  }

  saveClient() {
    console.log(this.client.toString());
    this.isSaving = true;
    if (this.client.id !== undefined) {
      this.clienteService.update(this.client).subscribe(
        (res: HttpResponse<Cliente>) => {
          console.log(res.body);
          this.client = res.body;
          this.goStep('sell', null, null);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    } else {
      this.clienteService.create(this.client).subscribe(
        (res: HttpResponse<Cliente>) => {
          console.log(res.body);
          this.client = res.body;
          this.goStep('sell', null, null);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  validateSearchProduct() {
    if (this.currentSearchProduct.length > 2 || this.currentSearchProduct.length === 0) {
     this.searchProduct(this.currentSearchProduct)
    }
  }

  validateSearchClient() {
    if (this.currentSearchClient.length > 2 || this.currentSearchClient.length === 0) {
      this.searchClient(this.currentSearchClient)
    }
  }
}
