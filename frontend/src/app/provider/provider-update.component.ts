import { Component, OnInit } from '@angular/core';
import { IProveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-provider-update',
  templateUrl: './provider-update.component.html',
  styleUrls: ['./provider-update.component.scss']
})
export class ProviderUpdateComponent implements OnInit {

  proveedor: IProveedor;
  isSaving: boolean;

  constructor(private proveedorService: ProveedorService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.proveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.proveedorService.update(this.proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(this.proveedor));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

}
