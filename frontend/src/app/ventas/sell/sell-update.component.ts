import {Component, ElementRef, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder, Validators} from '@angular/forms';
import {IVenta, SellStatus} from "../../models/venta.model";
import {VentaService} from "./venta.service";
import {JhiDataUtils} from 'ng-jhipster';
import {MatDialog, MatTableDataSource} from "@angular/material";
import {BaseVenta} from "./BaseVenta";
import {IProducto, Producto} from "../../models/producto.model";
import {ProductoService} from "../../inventory/product/producto.service";
import {CivilStatus, Cliente, ClientType, ICliente, Sex} from "../../models/cliente.model";
import {ClienteService} from "../../contact/client/cliente.service";
import {ITipoDeDocumento} from "../../models/tipo-de-documento.model";
import {TipoDeDocumentoService} from "../../configuration/documenttype/tipo-de-documento.service";
import {SellVariantselectionComponent} from "./sell-variantselection.component";
import {ProductoDetalle} from "../../models/producto-detalle.model";
import {SellLimitStockErrorComponent} from "./sell-limit-stock-error.component";
import {SellExtraInfoComponent} from "./sell-extra-info.component";
import {FullService} from "../../layouts/full/full.service";
import {EmpleadoService} from "../../contact/employee/empleado.service";
import {Empleado, IEmpleado} from "../../models/empleado.model";
import {ParametroSistemaService} from "../../configuration/systemparam/parametro-sistema.service";
import {IParametroSistema} from "../../models/parametro-sistema.model";

import * as moment from 'moment';
import {AmortizacionService} from "../amortizacion/amortizacion.service";
import {CustomValidators} from "ng2-validation";
import {Amortizacion} from "../../models/amortizacion.model";

declare var require: any;

@Component({
  selector: 'app-sell-update',
  templateUrl: './sell-update.component.html',
  styleUrls: ['./sell-update.component.scss']
})
export class SellUpdateComponent extends BaseVenta implements OnInit {

  private editFlag: boolean;
  currentSearchProduct: string;
  currentSearchClient: string;
  productos: IProducto[] = [];
  clientes: ICliente[];
  displayedColumnsProductosDetalles = ['cantidad', 'producto', 'precioVenta', 'precioTotal', 'quitar'];
  displayedColumnsClientes = ['name', 'action'];

  dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(null);
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
  tiposDeDocumentoFiltered: ITipoDeDocumento[];

  empleados: Empleado[] = [];

  clientRepeated = false;
  public serie: any;

  constructor(public dataUtils: JhiDataUtils,
              public service: VentaService,
              public fullService: FullService,
              public productoService: ProductoService,
              public clienteService: ClienteService,
              public empleadoService: EmpleadoService,
              private tiposDeDocumentoService: TipoDeDocumentoService,
              private amortizacionService: AmortizacionService,
              public parametrosSistemaService: ParametroSistemaService,
              public activatedRoute: ActivatedRoute,
              public elementRef: ElementRef,
              public fb: FormBuilder,
              public dialog: MatDialog,
              private router: Router) {
    super(service, fullService,null,dialog, dataUtils, elementRef, require('../menu.json'), 'NUEVA VENTA');
  }

  ngOnInit() {
    console.log(this.client);
    this.isSaving = false;

    this.client.tipoDeCliente = ClientType.NATURAL;
    this.client.sexo = Sex.MASCULINO;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;

      //this.form = this.fb.group({
      //});

      if (this.entity.id) {
        console.log(this.entity);
        this.editFlag = true;
        this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.entity.productoDetalles);

      } else {
        this.entity.subTotal = 0;
        this.entity.montoTotal = 0;
        this.entity.productoDetalles = [];
        this.entity.metaData = '{}';
      }
    });

    this.tiposDeDocumentoService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumento[]>) => {
        this.tiposDeDocumento = res.body;
        if (this.tiposDeDocumento.length > 0){
          this.client.tipoDeCliente = ClientType.NATURAL;
          this.filterDocumentTypeContent(ClientType.NATURAL);
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.productoService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumento[]>) => {
        this.productos = res.body;
        if (this.productos.length > 15){
          this.productos = this.productos.slice(0,14);
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.empleadoService.query().subscribe(
      (res: HttpResponse<IEmpleado[]>) => {
        this.empleados = res.body;
        if (this.empleados.length > 0){
          if (!this.entity.id){
            this.entity.empleadoId = this.empleados[0].id;
          }
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.clienteService.query().subscribe(
      (res: HttpResponse<ICliente[]>) => {
        this.clientes = res.body;
        this.dataSourceClientes = new MatTableDataSource<Cliente>(this.clientes);
        if (this.entity.id){
          this.client = this.clientes.find(x => x.id === this.entity.clienteId);
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.parametrosSistemaService.search({query:'Impuesto'}).subscribe(
      (res: HttpResponse<IParametroSistema[]>) => {
       this.entity.impuesto = res.body.length  > 0 ? parseInt(res.body[0].nombre.split('=')[1].slice(0,res.body[0].nombre.split('=')[1].length -1)) : 0;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.form = this.fb.group({
      fnombre: [
        null,
        Validators.compose([
          Validators.required
        ])
      ],
      fdireccion: [
        null,
        Validators.compose([
          Validators.required
        ])
      ],
      fcodigo: [
        null,
        Validators.compose([
          Validators.required
        ])
      ],
      ftelefono: [
        null,
        Validators.compose([
          Validators.required
        ])
      ],
      femail: [
        null,
        Validators.compose([
          Validators.required,
          CustomValidators.email])
      ],
    });
  }

  save() {
    this.updateEntity();
    super.save();
  }

  public subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
    result.subscribe((res: HttpResponse<IVenta>) => this.onSaveSuccessCustom(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  public onSaveSuccessCustom(sell) {
    this.isSaving = false;
    if (sell.amortizacions.length === 0) {
      this.router.navigate(['/ventas/sell/list']);
    } else {
      this.amortizacionService.countByDocumentTypeSellId(sell.amortizacions[0].tipoDeDocumentoDeVentaId).subscribe(
        (res: HttpResponse<number>) => {
          sell.amortizacions[0].codigo = this.serie.serie+'-00000'+res.body.toString();
          this.amortizacionService.update(sell.amortizacions[0]).subscribe(
            (res: HttpResponse<Amortizacion>) => {
              console.log(res.body);
              this.router.navigate(['/print/invoice2/' + sell.id + '/' + 0]);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
          );
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    }
  }

  public updateEntity() {
    if (this.entity.codigo === undefined) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    } else if (this.entity.codigo.length === 0) {
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


  saveChangesInProductosDetallesRow($event: FocusEvent, i: any, label: string) {
    // @ts-ignore
    console.log($event.target.value, i, label);

    if (label === 'cantidad') {
      // @ts-ignore
      const newQuantity = parseInt($event.target.value);
      // @ts-ignore
      const oldQuantity = this.entity.productoDetalles[i].cantidad;

      // @ts-ignore
      this.entity.productoDetalles[i].cantidad = newQuantity;

      const newStockConsum = this.getStockConsumFromProduct(this.entity.productoDetalles[i].productos[0]);

      if (newStockConsum > this.entity.productoDetalles[i].productos[0].stock) {
        console.log(newQuantity, oldQuantity);
        this.entity.productoDetalles[i].cantidad = oldQuantity;

        this.openDialogErrorStock(this.entity.productoDetalles[i].productoLabel);
      }
    }

    if (label === 'precioVenta') {
      // @ts-ignore
      this.entity.productoDetalles[i].precioVenta = parseFloat($event.target.value);
    }

    this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.entity.productoDetalles);
    this.setAmmount();
  }

  deleteProductosDetalles(i: any, row: any) {
    this.entity.productoDetalles.splice(i,1);

    this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.entity.productoDetalles);
    this.setAmmount();
  }

  goStep(step: string, view: string, row: Cliente) {
    switch (step) {
      case 'sell': {
        this.stateStep = step;
        break;
      }

      case 'clientList': {
        this.stateStep = step;
        this.client = new Cliente();
        this.searchClient('');
        break;
      }

      case 'clientUpdate': {
        this.stateStep = step;
        if (view === 'new') {
          this.client = new Cliente();
          this.client.tipoDeCliente = ClientType.NATURAL;
          this.client.tipoDeDocumentoId = this.tiposDeDocumento.find(x => x.nombre === 'DNI').id;
        } else {
          this.client = row;
        }
        break;
      }
    }
  }

  setClient(row: Cliente) {
    this.client = row;
    this.entity.clienteId = this.client.id;
    this.goStep('sell', null, null);
  }

  saveClient() {
    console.log(this.form.valid);
    if (!this.clientRepeated){
      console.log(this.client.toString());
      this.isSaving = true;
      if (this.client.id !== undefined) {
        this.clienteService.update(this.client).subscribe(
          (res: HttpResponse<Cliente>) => {
            console.log(res.body);
            this.client = res.body;
            this.entity.clienteId = this.client.id;
            this.entity.clienteNombre = this.client.nombre;
            this.goStep('sell', null, null);
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      } else {
        this.clienteService.create(this.client).subscribe(
          (res: HttpResponse<Cliente>) => {
            console.log(res.body);
            this.client = res.body;
            this.entity.clienteId = this.client.id;
            this.entity.clienteNombre = this.client.nombre;
            this.goStep('sell', null, null);
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      }
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

  openDialogVariantSelection(entity): void {
    if (entity.variantes.length === 0) {
      const result = {
        isVariant:
          false, variant: null
      };

      this.processVariantSelection(entity, result);
    } else {
      const dialogRef = this.dialog.open(SellVariantselectionComponent, {
        width: '50%',
        data: {
          entity: entity,
          entityByStock: this.entity
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        this.processVariantSelection(entity, result)
      });
    }
  }

  processVariantSelection(entity, result){
    if (result !== undefined) {
      console.log(result);

      let productoLabelExtra = null;

      if (result.isVariant) {
        productoLabelExtra = result.variant.nombre;
      } else {
        productoLabelExtra = entity.unidadDeMedida;
      }

      const productoDetalle = <ProductoDetalle>{
        cantidad: 1,
        precioVenta: result.isVariant ? result.variant.precioCompra : entity.precioVenta,
        productos: [entity],
        productoLabel: entity.nombre + '('+productoLabelExtra+')',
        variantes: result.isVariant ? [result.variant] : []
      };

      const index = this.entity.productoDetalles.map(x => x.productoLabel).indexOf(productoDetalle.productoLabel);

      if (index !== -1) {
        this.entity.productoDetalles[index].cantidad += 1;
        //this.entity.productoDetalles[index].precioVenta += productoDetalle.precioVenta;
      } else {
        this.entity.productoDetalles.push(productoDetalle);
      }

      if (this.getStockConsumFromProduct(productoDetalle.productos[0]) >
        this.entity.productoDetalles.find(x => x.productos[0] === productoDetalle.productos[0]).productos[0].stock) {

        if (index !== -1) {
          this.entity.productoDetalles[index].cantidad -= 1;
          // this.entity.productoDetalles[index].precioVenta -= productoDetalle.precioVenta;
        } else {
          this.entity.productoDetalles.splice(this.entity.productoDetalles.length-1, 1);
        }

        this.openDialogErrorStock(productoDetalle.productoLabel);
      } else {
        console.log(this.entity.productoDetalles);

        this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.entity.productoDetalles);
        this.setAmmount();
      }
    }
  }

  openDialogErrorStock(productLabel): void {
    const dialogRef = this.dialog.open(SellLimitStockErrorComponent, {
      width: '50%',
      data: { productLabel: productLabel }
    });

    dialogRef.afterClosed().subscribe(result => {
     this.refreshProductDetails();
    });
  }

  setAmmount(){
    if (this.entity.productoDetalles.length === 0) {
      this.entity.subTotal= this.entity.montoTotal = 0;
    } else {
      this.entity.subTotal= this.entity.montoTotal = this.entity.productoDetalles.map(x => x.precioVenta * x.cantidad).reduce((a, b) => a + b, 0);
      this.entity.montoTotal = this.parseFloatCustom(this.entity.subTotal + ((this.entity.subTotal * this.entity.impuesto) / 100))
    }
  }

  public getStockConsumFromProduct(product: IProducto) {
    let sum = 0;
    this.entity.productoDetalles.filter(x => x.productos[0].id === product.id ).forEach(productDetalle => {
      if (productDetalle.variantes.length === 0) {
        //unidad
        sum += productDetalle.cantidad;
      } else {
        sum += productDetalle.variantes[0].cantidad * productDetalle.cantidad;
      }
    });

    return sum;
  }

  private refreshProductDetails() {
    setTimeout(() => {
      this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(null);
      setTimeout(() => {
        this.dataSourceProductosDetalles = new MatTableDataSource<ProductoDetalle>(this.entity.productoDetalles);
      }, 500);
    }, 500);
  }

  addExtraInfoSell() {
    //save the sell first ?
    const dialogRef = this.dialog.open(SellExtraInfoComponent, {
      width: '80%',
      data: {
        entity: this.entity,
        client: this.client,
        serie: this.serie
      },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result !== undefined) {
        if (result.flag === 'exit'){
          if (this.entity.id) {
            this.service.delete(this.entity.id).subscribe(
              (res: HttpResponse<Producto>) => {
                this.router.navigate(['/ventas/sell']);
              },
              (res: HttpErrorResponse) => this.onError(res.message)
            );
          } else {
            this.entity.productoDetalles = [];
            this.entity.subTotal = 0;
            this.entity.montoTotal = 0;
            this.refreshProductDetails();
            this.removeCliente();
          }
        }

        if (result.flag === 'save'){
          this.entity.fecha = moment();
          this.entity.estatus = SellStatus.PENDIENTE;
          this.save();
        }

        if (result.flag === 'pay'){
          this.serie = result.serie;

          console.log('estatus');
          this.entity.estatus = parseFloat(result.amortization.montoPagado) >= parseFloat(result.amortization.monto) ? SellStatus.COMPLETADO : SellStatus.PENDIENTE;
          this.entity.amortizacions = [result.amortization];

          this.entity.productoDetalles.forEach(productoDetalle => {
            productoDetalle.productos[0].stock = productoDetalle.productos[0].stock - this.getStockConsumFromProduct(productoDetalle.productos[0]);
            console.log('producto ' + productoDetalle.productos[0]);
            this.productoService.update(productoDetalle.productos[0]).subscribe(
              (res: HttpResponse<Producto>) => {

              },
              (res: HttpErrorResponse) => this.onError(res.message)
            );
          });

          this.entity.fecha = moment();
          this.entity.amortizacions[0].fecha = moment();
          this.save();
        }
      } else {
        // this.entity.estatus = SellStatus.PENDIENTE;
        // this.save();
      }
    });
  }

  removeCliente(){
    this.client = new Cliente();
    this.client.tipoDeCliente = ClientType.NATURAL;
    this.client.tipoDeDocumentoId = this.tiposDeDocumento.find(x => x.nombre === 'DNI').id;
  }

  refreshSell() {
    this.entity.productoDetalles = [];
    this.entity.subTotal = 0;
    this.entity.montoTotal = 0;
    this.refreshProductDetails();
    this.removeCliente();
  }

  validateClient() {
    console.log(this.client.codigo);
    this.clientRepeated = false;

    if(this.client.codigo.length > 2 && this.client.id === undefined) {
      this.clienteService.search({query:this.client.codigo}).subscribe(
        (res: HttpResponse<ICliente[]>) => {
          console.log(res.body);
          res.body.forEach(client => {
            if (client.codigo === this.client.codigo) {
              this.clientRepeated = true;
            }
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  private filterDocumentTypeContent(clientType: ClientType) {
    if (clientType === ClientType.NATURAL) {
      this.client.tipoDeDocumentoId = this.tiposDeDocumento.find(x => x.nombre === 'DNI').id;
      this.tiposDeDocumentoFiltered = this.tiposDeDocumento.filter(x => x.nombre !== 'RUC');
    } else {
      this.client.tipoDeDocumentoId = this.tiposDeDocumento.find(x => x.nombre === 'RUC').id;
      this.tiposDeDocumentoFiltered = this.tiposDeDocumento.filter(x => x.nombre === 'RUC');
    }
  }
}
