import {Component, ElementRef, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder, Validators} from '@angular/forms';
import {IProducto, UnidadDeMedida} from "../../models/producto.model";
import {ProductoService} from "./producto.service";
import { JhiDataUtils } from 'ng-jhipster';
import {CategoriaService} from "../category/categoria.service";
import {ICategoria} from "../../models/categoria.model";
import {IVariante, Variante} from "../../models/variante.model";
import {MatTableDataSource} from "@angular/material";
import {BaseProducto} from "./BaseProducto";

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
export class ProductUpdateComponent extends BaseProducto implements OnInit {

  unidadesDeMedida: UnidadDeMedida[] = [
    UnidadDeMedida.KILO,
    UnidadDeMedida.LITRO,
    UnidadDeMedida.UNIDAD
  ];

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
  private editFlag: boolean;

  constructor(public dataUtils: JhiDataUtils,
              public service: ProductoService,
              public categoriaService: CategoriaService,
              public activatedRoute: ActivatedRoute,
              public elementRef: ElementRef,
              public fb: FormBuilder) {
    super(service, null,null,null, dataUtils, elementRef);
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
          ])
        ],
        fprecioVenta: [
          null,
          Validators.compose([
            Validators.required,
            // Validators.maxLength(10)
          ])
        ],
        fprecioCompra: [
          null,
          Validators.compose([
            // Validators.maxLength(10)
          ])
        ],
        funidadDeMedida: [
          null,
          Validators.compose([
            Validators.required,
          ])
        ],
      });

      if (this.entity.id) {
        console.log(this.entity);
        this.editFlag = true;
        this.primerUnidadDeMedidaLabel.unidadLabel = this.entity.unidadDeMedida;
        this.primerUnidadDeMedidaLabel.cantidad = this.entity.stock;
        this.unidadesDeMedidaLabel.push(this.primerUnidadDeMedidaLabel);

        this.variantes = this.entity.variantes;
        this.variantes.forEach(variante => {
          let unidadDeMedidaLabel: UnidadDeMedidaLabel = {
            cantidad: this.primerUnidadDeMedidaLabel.cantidad / variante.cantidad,
            unidadLabel: variante.nombre,
            esPrincipal: false
          };

          this.unidadesDeMedidaLabel.push(unidadDeMedidaLabel);
        });

        this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
        this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.variantes);

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

  save() {
    this.updateEntity();
    super.save();
  }

  public subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  public onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  public updateEntity() {
    if (this.entity.codigo === undefined) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }

    if (this.entity.precioCompra === undefined) {
      this.entity.precioCompra = 0;
    }

    if (this.entity.stock === undefined) {
      this.entity.stock = 0;
    }

    if (this.entity.codigo.length === 0) {
      this.entity.codigo = 'COD00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }

    this.entity.variantes = this.variantes;
    this.entity.stock = this.unidadesDeMedidaLabel[0].cantidad;
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

        this.setCalculoDeUnidadesDeMedida();

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

    this.dataSourceVariantes.data.splice(i, 1);
    this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.dataSourceVariantes.data);
    this.variantes = this.dataSourceVariantes.data;
  }

  clearUnidadesDetalle() {
    if (this.editFlag) {
      this.editFlag = !this.editFlag;
    } else {
      this.subUnidadDeMedidaDetalle = undefined;
      this.variantes = [];
      this.dataSourceVariantes = new MatTableDataSource<IVariante>(this.variantes);

      this.primerUnidadDeMedidaLabel.unidadLabel = this.entity.unidadDeMedida;
      this.unidadesDeMedidaLabel = [];
      this.unidadesDeMedidaLabel.push(this.primerUnidadDeMedidaLabel);
      this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
    }

  }

  public setCalculoDeUnidadesDeMedida() {
    for (let i = 1; i < this.unidadesDeMedidaLabel.length; i++) {
      this.unidadesDeMedidaLabel[i].cantidad = this.unidadesDeMedidaLabel[0].cantidad / this.variantes[i - 1].cantidad;
    }

    this.dataSourceUnidadesDeMedidaLabel = new MatTableDataSource<UnidadDeMedidaLabel>(this.unidadesDeMedidaLabel);
  }

  updateStock($event: any) {
    this.unidadesDeMedidaLabel[0].cantidad = $event.target.value;
    this.setCalculoDeUnidadesDeMedida();
  }
}
