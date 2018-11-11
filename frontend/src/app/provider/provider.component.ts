import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { BreakpointObserver } from '@angular/cdk/layout';
import {SelectionModel} from '@angular/cdk/collections';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { IProveedor, Proveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { Observable, Subscription } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

// export interface Element {
//   id: number;
//   codigo: string;
//   razonSocial: string;
//   correo: string;
//   telefono: string;
// }
//
// const ELEMENT_DATA: Element[] = [
//   {
//     id: 1,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Nirav joshi',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   },
//   {
//     id: 2,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Sunil joshi',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   },
//   {
//     id: 3,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Vishal Bhatt',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   },
//   {
//     id: 4,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Beryllium Lon',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   },
//   {
//     id: 5,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Boron son',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   },
//   {
//     id: 6,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Carbon hryt',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   },
//   {
//     id: 7,
//     codigo: 'dflgdlfgmndlfng',
//     razonSocial: 'Nitro oxur',
//     correo: 'algo@hotmail',
//     telefono: '13456'
//   }
// ];

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.scss']
})
export class ProviderComponent implements OnInit, OnDestroy {
  options: FormGroup;
  action: string;

  proveedors: IProveedor[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  queryCount: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  proveedor: IProveedor;

  // This is for the table responsive
  constructor(private proveedorService: ProveedorService,
              breakpointObserver: BreakpointObserver,
              fb: FormBuilder,
              private jhiAlertService: JhiAlertService,
              private eventManager: JhiEventManager,
              private parseLinks: JhiParseLinks) {
    this.proveedors = [];
    this.itemsPerPage = 20000;
    this.page = 0;
    this.links = {
      last: 0
    };
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

    breakpointObserver.observe(['(max-width: 600px)']).subscribe(result => {
      this.displayedColumns = result.matches ?
          ['select', 'codigo', 'razonSocial', 'correo', 'telefono', 'star'] :
          ['select', 'codigo', 'razonSocial', 'correo', 'telefono', 'star'];
    });

    this.options = fb.group({
      hideRequired: false,
      floatLabel: 'auto'
    });

    this.action = 'list';
  }

  // tslint:disable-next-line:member-ordering
  displayedColumns = ['select', 'codigo', 'razonSocial', 'correo', 'telefono', 'star'];
  // tslint:disable-next-line:member-ordering
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

  // For form validator
  // email = new FormControl('', [Validators.required, Validators.email]);

  // Sufix and prefix
  // hide = true;

  // getErrorMessage() {
  //   return this.email.hasError('required')
  //     ? 'You must enter a value'
  //     : this.email.hasError('email')
  //       ? 'Not a valid email'
  //       : '';
  // }

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
    this.links = {
      last: 0
    };
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
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    // this.principal.identity().then(account => {
    //   this.currentAccount = account;
    // });
    this.registerChangeInProveedors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProveedor) {
    return item.id;
  }

  registerChangeInProveedors() {
    this.eventSubscriber = this.eventManager.subscribe('proveedorListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateProveedors(data: IProveedor[], headers: HttpHeaders) {
    this.proveedors = [];

    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.proveedors.push(data[i]);
    }
    this.dataSource = new MatTableDataSource<IProveedor>(this.proveedors);
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
    // this.jhiAlertService.error(errorMessage, null, null);
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
