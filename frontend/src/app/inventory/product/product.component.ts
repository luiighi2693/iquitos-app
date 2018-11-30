import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import {FullService} from '../../layouts/full/full.service';
import {IProducto} from "../../models/producto.model";
import {ProductoService} from "./producto.service";
import {ProductDeleteComponent} from "./product-delete.component";

declare var require: any;
const menu: any = require('../menu.json');

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit, OnDestroy {
  products: IProducto[];
  itemsPerPage: number;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;
  product: IProducto;

  constructor(private service: ProductoService, public dialog: MatDialog, public fullService: FullService) {
    this.products = [];
    this.itemsPerPage = 500;
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.fullService.changeMenu(menu);
    this.fullService.changeMenuSelected('PRODUCTOS');
  }

  displayedColumns = ['select', 'codigo', 'descripcion', 'stock', 'star'];
  dataSource = new MatTableDataSource<IProducto>(null);
  selection = new SelectionModel<IProducto>(true, []);

  loadAll() {
    if (this.currentSearch) {
      this.service
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IProducto[]>) => this.paginate(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.service
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.paginate(res.body, res.headers),
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
    this.service.delete(row.id).subscribe(response => {
    });

    const index = this.dataSource.data.map(x => x.id).indexOf(row.id);
    if (index !== -1) {
      this.dataSource.data.splice(index, 1);
      this.dataSource = new MatTableDataSource<IProducto>(this.dataSource.data);
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  save() {
    if (this.product.id !== undefined) {
      this.subscribeToSaveResponse(this.service.update(this.product));
    } else {
      this.subscribeToSaveResponse(this.service.create(this.product));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.loadAll();
  }

  private onSaveError() {
    // this.isSaving = false;
  }

  reset() {
    this.page = 0;
    this.products = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.products = [];
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
    this.products = [];
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

  private paginate(data: IProducto[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.products = data;
    this.dataSource = new MatTableDataSource<IProducto>(this.products);
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  openDialog(entity): void {
    const dialogRef = this.dialog.open(ProductDeleteComponent, {
      width: '300px',
      data: { entity: entity }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.deleteEntity(result);
      }
    });
  }
}
