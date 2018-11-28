import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
// import { BreakpointObserver } from '@angular/cdk/layout';
import {SelectionModel} from '@angular/cdk/collections';
// import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { IProveedor, Proveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { Observable, Subscription } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ProviderDeleteComponent } from './provider-delete.component';
import {FullService} from '../layouts/full/full.service';

export interface ListContent {
  value: string;
  uri: string;
}

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.scss']
})
export class ProviderComponent implements OnInit, OnDestroy {
  proveedors: IProveedor[];
  itemsPerPage: number;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;
  proveedor: IProveedor;

  tags = new Map([]);

  constructor(private proveedorService: ProveedorService, public dialog: MatDialog, public fullService: FullService) {
    this.proveedors = [];
    this.itemsPerPage = 500;
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.fullService.changeMenu([
      { value: 'PROVEEDORES', uri: '/provider'}
    ]);
    this.fullService.changeMenuSelected('PROVEEDORES');
  }

  // displayedColumns = ['select', 'codigo', 'razonSocial', 'correo', 'telefono', 'star'];
  displayedColumns = ['select', 'razonSocial', 'telefono', 'productosRelacionados', 'contactos', 'star'];
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

  deleteEntity(row) {
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
    this.loadAll();
  }

  private onSaveError() {
    // this.isSaving = false;
  }

  // cancel() {
  //   this.dataSourceContactos.filter = '';
  // }

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

    this.tags.clear();

    let index = 0;
    this.proveedors.forEach(proveedor => {
      let t = [];
      if (proveedor.contactoProveedors !== undefined) {
        for (let i = 0; i < proveedor.contactoProveedors.length; i++) {
          if (proveedor.contactoProveedors[i].producto !== undefined) {
            proveedor.contactoProveedors[i].producto.split(',').forEach(tag => {
              t.push(tag);
            });
          }
        }
      }
      t = t.filter(function (item, pos) {
        return t.indexOf(item) === pos;
      });
      this.tags.set(index, t);
      index = index + 1;
    });

    console.log(this.tags);

    // this.proveedors = dataIn;

    this.dataSource = new MatTableDataSource<IProveedor>(this.proveedors);
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  openDialog(provider): void {
    const dialogRef = this.dialog.open(ProviderDeleteComponent, {
      width: '300px',
      data: { provider: provider }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.deleteEntity(result);
      }
    });
  }
}
