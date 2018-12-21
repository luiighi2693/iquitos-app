import {Component, ElementRef, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from "ng2-validation";
import {IProducto, UnidadDeMedida} from "../../models/producto.model";
import {ProductoService} from "./producto.service";
import { JhiDataUtils } from 'ng-jhipster';
import {CategoriaService} from "../category/categoria.service";
import {ICategoria} from "../../models/categoria.model";
import {IVariante, Variante} from "../../models/variante.model";
import {MatTableDataSource} from "@angular/material";

export interface Tab {
  label: string;
}

export interface UnidadDeMedidaDetalle {
  name: UnidadDeMedida;
  subUnidades: SubUnidadDeMedida[];
}

export interface SubUnidadDeMedida {
  name: string;
}

export interface UnidadDeMedidaLabel {
  cantidad: number;
  unidadLabel: string;
  esPrincipal: boolean
}

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.scss']
})
export class ProductUpdateComponent implements OnInit {

  entity: IProducto;
  isSaving: boolean;

  editing = {};
  rows = [];

  itemsPerPage = 500;
  page: 0;
  predicate = 'id';
  reverse = true;
  totalItems: number;

  unidadesDeMedida: UnidadDeMedida[] = [
    UnidadDeMedida.KILO,
    UnidadDeMedida.LITRO,
    UnidadDeMedida.UNIDAD
  ];

  public form: FormGroup;

  categorias: ICategoria[];

  variantes: IVariante[];

  subUnidadDeMedidaDetalle: SubUnidadDeMedida;
  subUnidades: UnidadDeMedidaDetalle[] = [
    {
      name: UnidadDeMedida.KILO,
      subUnidades: [
        {
          name: 'SACO',
        },
        {
          name: '1/2 SACO',
        }
      ]
    },
    {
      name: UnidadDeMedida.LITRO,
      subUnidades: [
      ]
    },
    {
      name: UnidadDeMedida.UNIDAD,
      subUnidades: [
        {
          name: 'BALDE',
        },
        {
          name: 'PAQUETE',
        },
        {
          name: '1/2 PAQUETE',
        },
        {
          name: 'PACK',
        },
        {
          name: 'DISPLAY',
        },
        {
          name: 'TIRA',
        },
        {
          name: '1/2 TIRA',
        },
        {
          name: 'DOCENA',
        },
        {
          name: 'CIENTO',
        },
        {
          name: '1/2 CIENTO',
        },
        {
          name: 'CAJA',
        },
        {
          name: '1/2 CAJA',
        }
      ]
    }
  ];

  primerUnidadDeMedidaLabel: UnidadDeMedidaLabel = {
    cantidad: 0,
    unidadLabel: '',
    esPrincipal: true
  };

  unidadesDeMedidaLabel: UnidadDeMedidaLabel[] = [];

  displayedColumnsVariantes = ['nombre', 'cantidad', 'precioCompra', 'quitar'];
  dataSourceVariantes = new MatTableDataSource<IVariante>(null);

  displayedColumnsUnidadesDeMedidaLabel = ['cantidad', 'unidadDeMedida'];
  dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(null);

  constructor(private dataUtils: JhiDataUtils,
              private service: ProductoService,
              private categoriaService: CategoriaService,
              private activatedRoute: ActivatedRoute,
              private elementRef: ElementRef,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;

      this.form = this.fb.group({
        fnombre: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ],
        fdescripcion: [
          null,
          Validators.compose([
            Validators.maxLength(150)
          ])
        ],
        fprecioVenta: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(10)
          ])
        ],
        fprecioCompra: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(10)
          ])
        ],
        // fdate: [
        //   null,
        //   Validators.compose([CustomValidators.date])
        // ],
      });

      if (this.entity.id) {
        this.primerUnidadDeMedidaLabel.unidadLabel = this.entity.unidadDeMedida;
        this.primerUnidadDeMedidaLabel.cantidad = this.entity.stock;
        this.unidadesDeMedidaLabel.push(this.primerUnidadDeMedidaLabel);
        this.entity.variantes.forEach(variante => {
          let unidadDeMedidaLabel: UnidadDeMedidaLabel = {
            cantidad: variante.cantidad,
            unidadLabel: variante.nombre,
            esPrincipal: false
          };

          this.unidadesDeMedidaLabel.push(unidadDeMedidaLabel);
        });

        this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
        this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.entity.variantes);

      } else {
        this.unidadesDeMedidaLabel.push(this.primerUnidadDeMedidaLabel);
        this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
      }

    });

    this.categoriaService.query().subscribe(
      (res: HttpResponse<ICategoria[]>) => {
        this.categorias = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  previousState() {
    window.history.back();
  }

  save() {
      this.updateEntity();
      console.log(this.entity.toString());
      this.isSaving = true;
      if (this.entity.id !== undefined) {
        this.subscribeToSaveResponse(this.service.update(this.entity));
      } else {
        this.subscribeToSaveResponse(this.service.create(this.entity));
      }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(res) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private updateEntity() {
    if (this.entity.codigo === undefined) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }

    if (this.entity.codigo.length === 0) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }

    this.entity.variantes = this.variantes
    this.entity.stock = this.unidadesDeMedidaLabel[0].cantidad;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.entity, this.elementRef, field, fieldContentType, idInput);
  }

  getSubUnidadesDeMedida() {
    if (this.subUnidades.find(x => x.name === this.entity.unidadDeMedida)) {
      return this.subUnidades.find(x => x.name === this.entity.unidadDeMedida).subUnidades;
    }
    return [];
  }

  addVariante() {
    const variante = new Variante();
    variante.nombre = this.subUnidadDeMedidaDetalle.name;
    variante.precioCompra = 0;
    variante.precioVenta = 0;

    if (this.variantes === undefined) {
      this.variantes = [];
    }

    if (this.variantes.find(x => x.nombre === this.subUnidadDeMedidaDetalle.name) === undefined) {
      this.variantes.push(variante);

      let unidadDeMedidaLabel: UnidadDeMedidaLabel = {
        cantidad: 0,
        unidadLabel: this.subUnidadDeMedidaDetalle.name,
        esPrincipal: false
      };
      this.unidadesDeMedidaLabel.push(unidadDeMedidaLabel);
      this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);

      this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.variantes);
    }
  }

  saveChangesInVariantRow(event, indexFromAccount, label) {
    if (label === 'nombre') {
      this.variantes[indexFromAccount].nombre = event.target.value;
    }

    if (label === 'cantidad') {
      this.variantes[indexFromAccount].cantidad = event.target.value;
      if(this.unidadesDeMedidaLabel.indexOf(this.unidadesDeMedidaLabel
        .find(x => x.unidadLabel === this.variantes[indexFromAccount].nombre)) !== -1) {
        this.unidadesDeMedidaLabel[this.unidadesDeMedidaLabel.indexOf(this.unidadesDeMedidaLabel
          .find(x => x.unidadLabel === this.variantes[indexFromAccount].nombre))].cantidad = this.variantes[indexFromAccount].cantidad;

        this.setUnidadesDeMedidaTotal();

        this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
      }
    }

    if (label === 'precioCompra') {
      this.variantes[indexFromAccount].precioCompra = event.target.value;
    }

    this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.variantes);
  }

  deleteVariant(i, row) {
    console.log(row);
    this.unidadesDeMedidaLabel.splice(this.unidadesDeMedidaLabel.indexOf(this.unidadesDeMedidaLabel
      .find(x => x.unidadLabel === row.nombre)), 1);
    this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
    this.setUnidadesDeMedidaTotal();

    this.dataSourceVariantes.data.splice(i, 1);
    this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.dataSourceVariantes.data);
    this.variantes = this.dataSourceVariantes.data;
  }

  clearUnidadesDetalle() {
    this.subUnidadDeMedidaDetalle = undefined;
    this.variantes = [];
    this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.variantes);

    this.primerUnidadDeMedidaLabel.unidadLabel = this.entity.unidadDeMedida;
    this.unidadesDeMedidaLabel = [];
    this.unidadesDeMedidaLabel.push(this.primerUnidadDeMedidaLabel);
    this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
  }

  private setUnidadesDeMedidaTotal() {
    this.unidadesDeMedidaLabel[0].cantidad = this.unidadesDeMedidaLabel
      .filter(x => !x.esPrincipal).map(y => parseInt( String(y.cantidad))).reduce((a, b) => a + b, 0);
    this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
  }


}
