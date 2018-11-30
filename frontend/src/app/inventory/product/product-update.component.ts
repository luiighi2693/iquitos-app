import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from "ng2-validation";
import {IProducto} from "../../models/producto.model";
import {ProductoService} from "./producto.service";

export interface Tab {
  label: string;
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

  public form: FormGroup;

  constructor(private service: ProductoService,
              private activatedRoute: ActivatedRoute,
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
  }
}
