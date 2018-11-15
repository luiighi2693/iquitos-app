import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
// import { BreakpointObserver } from '@angular/cdk/layout';
import {SelectionModel} from '@angular/cdk/collections';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { IProveedor, Proveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { Observable, Subscription } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.scss']
})
export class ProviderComponent implements OnInit, OnDestroy {
  options: FormGroup;
  action: string;
  proveedors: IProveedor[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  proveedor: IProveedor;

  constructor(private proveedorService: ProveedorService,
              fb: FormBuilder) {
    this.proveedors = [];
    this.itemsPerPage = 500;
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;

    this.proveedor = {
      'id': undefined,
      'codigo': '',
      'razonSocial': '',
      'correo': '',
      'direccion': '',
      'telefono': ''
    };

    this.options = fb.group({
      hideRequired: false,
      floatLabel: 'auto'
    });

    this.action = 'list';
  }

  displayedColumns = ['select', 'codigo', 'razonSocial', 'correo', 'telefono', 'star'];
  dataSource = new MatTableDataSource<IProveedor>(null);
  selection = new SelectionModel<IProveedor>(true, []);

  loadAll() {
    if (this.currentSearch) {
      this.proveedorService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IProveedor[]>) => this.paginateProveedors(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.proveedorService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProveedor[]>) => this.paginateProveedors(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  editRow(row) {
    this.proveedor = row;
    this.changeAction('edit');
  }

  deleteRow(row) {
    this.proveedorService.delete(row.id).subscribe(response => {
    });

    const index = this.dataSource.data.map(x => x.id).indexOf(row.id);
    if (index !== -1) {
      this.dataSource.data.splice(index, 1);
      this.dataSource = new MatTableDataSource<IProveedor>(this.dataSource.data);
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  changeAction(action) {
    this.action = action;
  }

  save() {
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
    this.changeAction('list');
    this.loadAll();
  }

  private onSaveError() {
    // this.isSaving = false;
  }

  cancel() {
    this.dataSource.filter = '';
    this.changeAction('list');
  }

  getNameForm() {
    if ( this.action === 'edit') {
      return 'Edición';
    }

    if ( this.action === 'new') {
      return 'Nuevo';
    }

    return '';
  }

  reset() {
    this.page = 0;
    this.proveedors = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.proveedors = [];
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch = '';
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.proveedors = [];
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateProveedors(data: IProveedor[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.proveedors = data;
    this.dataSource = new MatTableDataSource<IProveedor>(this.proveedors);
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  new() {
    this.proveedor = {
      'id': undefined,
      'codigo': '',
      'razonSocial': '',
      'correo': '',
      'direccion': '',
      'telefono': ''
    };

    this.changeAction('new');
  }
}
