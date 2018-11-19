import { Component, OnInit } from '@angular/core';
import { IProveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IContactoProveedor } from '../models/contacto-proveedor.model';
import { MatTableDataSource } from '@angular/material';
import { AccountTypeProvider, CuentaProveedor, ICuentaProveedor } from '../models/cuenta-proveedor.model';
import {CuentaProveedorService} from './cuenta-proveedor.service';

export interface ExampleTab {
  label: string;
}

export interface TipoCuentaProveedor {
  value: AccountTypeProvider;
  viewValue: string;
}

@Component({
  selector: 'app-provider-update',
  templateUrl: './provider-update.component.html',
  styleUrls: ['./provider-update.component.scss']
})
export class ProviderUpdateComponent implements OnInit {

  proveedor: IProveedor;
  contactos: IContactoProveedor[];
  cuentas: ICuentaProveedor[];

  asyncTabs: Observable<ExampleTab[]>;
  isSaving: boolean;

  displayedColumnsCuentas = ['cuenta', 'banco', 'numeroDeCuenta', 'nombreCuenta', 'quitar'];
  displayedColumnsContactos = ['nombre', 'cargo', 'telefono', 'producto'];
  dataSourceContactos = new MatTableDataSource<IContactoProveedor>(null);
  dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(null);

  tiposDeCuenta: TipoCuentaProveedor[] = [
    { value: AccountTypeProvider.CUENTA_CORRIENTE, viewValue: 'Cuenta Corriente'},
    { value: AccountTypeProvider.CUENTA_RECAUDADORA, viewValue: 'Cuenta Recaudadora'}
  ];

  selectedTipoDeCuenta = AccountTypeProvider.CUENTA_CORRIENTE;

  editing = {};
  rows = [];

  constructor(private proveedorService: ProveedorService,
              private cuentaProveedorService: CuentaProveedorService,
              private activatedRoute: ActivatedRoute) {
    this.asyncTabs = Observable.create((observer: Observer<ExampleTab[]>) => {
      setTimeout(() => {
        observer.next([
          { label: 'Cuentas Asociadas a Proveedor' },
          { label: 'Contactos Asociadas a Proveedor' },
        ]);
      }, 1000);
    });
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
      this.contactos = this.proveedor.contactoProveedors;
      this.cuentas = this.proveedor.cuentaProveedors;
      this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.contactos);
      this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.updateEntity();
    console.log(this.proveedor.toString());
    this.isSaving = true;
    if (this.proveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.proveedorService.update(this.proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(this.proveedor));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponseFromUpdateChild(result: Observable<HttpResponse<IProveedor>>, idToDelete: number) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccessFromUpdateChild(idToDelete), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(res) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveSuccessFromUpdateChild(idToDelete) {
    this.isSaving = false;
    this.cuentaProveedorService.delete(idToDelete).subscribe(response => {
    });
  }

  private onSaveError() {
    this.isSaving = false;
  }

  addAccountProvider() {
    const cuenta = new CuentaProveedor();
    cuenta.tipoCuenta = this.selectedTipoDeCuenta;

    if (this.cuentas === undefined) {
      this.cuentas = [];
    }

    this.cuentas.push(cuenta);
    this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);
  }

  saveChangesInAccountsRow(event, indexFromAccount, label) {
    if (label === 'banco') {
      this.cuentas[indexFromAccount].banco = event.target.value;
    }

    if (label === 'numeroDeCuenta') {
      this.cuentas[indexFromAccount].numeroDeCuenta = event.target.value;
    }

    if (label === 'nombreCuenta') {
      this.cuentas[indexFromAccount].nombreCuenta = event.target.value;
    }

    this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);
  }

  deleteAccount(i, row) {
    let index = null;

    if (row.id !== undefined) {
      index = this.dataSourceCuentas.data.map(x => x.id).indexOf(row.id);
      if (index !== -1) {
        this.dataSourceCuentas.data.splice(index, 1);
        this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.dataSourceCuentas.data);

        this.cuentas = this.dataSourceCuentas.data;

        this.updateEntity();
        this.subscribeToSaveResponseFromUpdateChild(this.proveedorService.update(this.proveedor), row.id);
      }
    } else {
      this.dataSourceCuentas.data.splice(index, 1);
      this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.dataSourceCuentas.data);
      this.cuentas = this.dataSourceCuentas.data;
    }
  }

  private updateEntity() {
    this.proveedor.contactoProveedors = this.contactos;
    this.proveedor.cuentaProveedors = this.cuentas;
  }
}
