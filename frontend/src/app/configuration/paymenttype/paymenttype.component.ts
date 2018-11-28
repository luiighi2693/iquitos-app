import {Component, OnInit, OnDestroy, ViewChild, ElementRef} from '@angular/core';
import { MatDialog } from '@angular/material';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import {FullService} from '../../layouts/full/full.service';
import {PaymenttypeDeleteComponent} from "./paymenttype-delete.component";
import {TipoDePagoService} from "./tipo-de-pago.service";
import {ITipoDePago} from "../../models/tipo-de-pago.model";

declare var require: any;
const menu: any = require('../menu.json');

@Component({
  selector: 'app-paymenttype',
  templateUrl: './paymenttype.component.html',
  styleUrls: ['./paymenttype.component.scss']
})
export class PaymenttypeComponent implements OnInit, OnDestroy {
  itemsPerPage: number;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  editing = {};
  rows = [];

  rowSelected = null;
  loadingIndicator = true;
  reorderable = true;

  columns = [{ prop: 'nombre' }];

  @ViewChild('valueInput') valueInput: ElementRef;

  @ViewChild(PaymenttypeComponent) table: PaymenttypeComponent;
  constructor(private service: TipoDePagoService, public dialog: MatDialog, public fullService: FullService) {
    this.itemsPerPage = 500;
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.fullService.changeMenu(menu);
    this.fullService.changeMenuSelected('TIPOS DE PAGO');
  }

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
          (res: HttpResponse<ITipoDePago[]>) => this.paginate(res.body, res.headers),
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
        (res: HttpResponse<ITipoDePago[]>) => this.paginate(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  deleteEntity(row) {
    this.service.delete(row.id).subscribe(response => {
    });

    this.rows.splice(this.rowSelected, 1);
  }

  save() {
    if (this.rows[this.rowSelected].id !== undefined) {
      this.subscribeToSaveResponse(this.service.update(this.rows[this.rowSelected]));
    } else {
      this.subscribeToSaveResponse(this.service.create(this.rows[this.rowSelected]));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDePago>>) {
    result.subscribe((res: HttpResponse<ITipoDePago>) =>
      this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.loadAll();
  }

  private onSaveError() {
    // this.isSaving = false;
  }

  clear() {
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

  private paginate(data: ITipoDePago[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.rows = data;
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  openDialog(index): void {
    this.rowSelected = index;
    const dialogRef = this.dialog.open(PaymenttypeDeleteComponent, {
      width: '300px',
      data: { entity: this.rows[index] }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.deleteEntity(result);
      }
    });
  }

  updateValue(event, cell, rowIndex) {
    if (this.valueInput.nativeElement.value.length !== 0) {
      this.editing[rowIndex + '-' + cell] = false;
      this.rows[rowIndex][cell] = this.valueInput.nativeElement.value;
      this.rowSelected = rowIndex;
      this.save();
    } else {
      this.valueInput.nativeElement.value = '';
    }
  }

  cancel(event, cell, rowIndex) {
    if (this.rows[rowIndex].id === undefined) {
      this.rows.splice(rowIndex, 1);
    } else {
      this.editing[rowIndex + '-' + cell] = false;
    }
  }

  addEntity() {
    if(this.rows.length !== 0) {
      if (this.rows[this.rows.length -1 ].id !== undefined) {
        this.pushEntity();
      }
    } else {
      this.pushEntity();
    }
  }

  pushEntity (){
    this.rows.push({
      nombre: ''
    });
    this.editing[this.rows.length - 1 + '-nombre'] = true;
  }
}
