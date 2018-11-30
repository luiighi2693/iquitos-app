import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {VarianteService} from "./variante.service";
import {IVariante} from "../../models/variante.model";
import {ProductoService} from "../product/producto.service";
import {IProducto} from "../../models/producto.model";
import {CustomValidators} from "ng2-validation";

export interface Tab {
  label: string;
}

@Component({
  selector: 'app-variant-update',
  templateUrl: './variant-update.component.html',
  styleUrls: ['./variant-update.component.scss']
})
export class VariantUpdateComponent implements OnInit {

  entity: IVariante;
  isSaving: boolean;

  editing = {};
  rows = [];

  itemsPerPage = 500;
  page: 0;
  predicate = 'id';
  reverse = true;
  totalItems: number;

  public form: FormGroup;
  entityFilteredSelected: IProducto = null;
  entityFilteredSelectedInput: string;
  entitiesFiltered: IProducto[] = [];
  entitySelectFlag = false;

  constructor(private service: VarianteService,
              private filterService: ProductoService,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;

      if (this.entity.id !== undefined) {
        this.entitySelectFlag = true;
        this.entityFilteredSelected = this.entity.productos[0];
        this.entityFilteredSelectedInput = this.entityFilteredSelected.codigo;
      }

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
            Validators.maxLength(150),
            CustomValidators.range([1, 10000000])
          ])
        ],
        fprecioCompra: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150),
            CustomValidators.range([1, 10000000])
          ])
        ],
        fcantidad: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(8)
          ])
        ],
        fproductSelected: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ]
      });
    });
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
    if (this.entityFilteredSelected) {
      this.updateEntity();
      console.log(this.entity.toString());
      this.isSaving = true;
      if (this.entity.id !== undefined) {
        this.subscribeToSaveResponse(this.service.update(this.entity));
      } else {
        this.subscribeToSaveResponse(this.service.create(this.entity));
      }
    } else {
      this.clearEntitySelected();
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IVariante>>) {
    result.subscribe((res: HttpResponse<IVariante>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(res) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private updateEntity() {
    this.entity.productos = [];
    this.entity.productos.push(this.entityFilteredSelected);
  }

  filterEntity() {
    if (!this.entitySelectFlag) {
      if (this.entityFilteredSelectedInput.length > 2) {
        this.filterEntityByQuery(this.entityFilteredSelectedInput);
      }
    } else {
      this.entityFilteredSelectedInput = this.entityFilteredSelected.codigo;
    }
  }

  filterEntityByQuery(query) {
    this.filterService
      .search({
        query: query,
        page: 0,
        size: 10,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.paginate(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  private paginate(data: IProducto[], headers: HttpHeaders) {
    if (parseInt(headers.get('X-Total-Count'), 10) > 0) {
      this.entitiesFiltered = data;
    }
}

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  setEntityFilter(option) {
    this.entityFilteredSelected = option;
    this.entityFilteredSelectedInput = option.codigo;
    this.entitySelectFlag = true;
  }

  clearEntitySelected() {
    this.entitySelectFlag = false;
    this.entityFilteredSelected = null;
    this.entityFilteredSelectedInput = undefined;
  }
}
