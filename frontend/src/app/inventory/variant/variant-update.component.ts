import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {VarianteService} from "./variante.service";
import {IVariante} from "../../models/variante.model";

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

  constructor(private service: VarianteService,
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
            Validators.maxLength(150)
          ])
        ],
        fprecioCompra: [
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
  }
}
