import {MatDialog, MatTableDataSource} from "@angular/material";
import {FullService} from "../layouts/full/full.service";
import {SelectionModel} from "@angular/cdk/collections";
import {Observable} from "rxjs";
import {HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {FormGroup} from "@angular/forms";
import {JhiDataUtils} from "ng-jhipster";
import {ElementRef} from "@angular/core";
import Util from "../shared/util/util";

export class Base<I,C> {
  public data: I[];
  public entity: C;

  itemsPerPage: number;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  dataSource = new MatTableDataSource<I>(null);
  selection = new SelectionModel<I>(true, []);

  isSaving: boolean;

  public form: FormGroup;

  constructor (public fullService: FullService,
               public menu: any, public item: string,
               public displayedColumns: string[],
               public dialog: MatDialog,
               public dataUtils: JhiDataUtils,
               public elementRef: ElementRef) {
    this.data = [];
    this.itemsPerPage = 500;
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;

    if (this.fullService) {
      this.fullService.changeMenu(menu);
      this.fullService.changeMenuSelected(item);
    }
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

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  loadAll() {
  }

  save() {
  }

  subscribeToSaveResponse(result: Observable<HttpResponse<I>>) {
    result.subscribe(
      (res: HttpResponse<I>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  onSaveSuccess() {
    this.loadAll();
  }

  onSaveError() {
    this.isSaving = false;
  }

  reset() {
    this.page = 0;
    this.data = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.data = [];
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
    this.data = [];
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  paginate(data: I[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.data = data;
    this.dataSource = new MatTableDataSource<I>(this.data);
  }

  onError(errorMessage: string) {
    console.log(errorMessage);
  }

  openDialog(entity): void {
  }

  deleteEntity(row) {
  }

  previousState() {
    window.history.back();
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.entity, this.elementRef, field, fieldContentType, idInput);
  }

  checkNumbersOnly(event: any): boolean {
    return Util.checkNumbersOnly(event);
  }

  checkCharactersOnly(event: any): boolean {
    return Util.checkCharactersOnly(event);
  }

  checkCharactersAndNumbersOnly(event: any): boolean {
    return Util.checkCharactersAndNumbersOnly(event);
  }

  checkNumbersDecimalOnly(event: any): boolean {
    return Util.checkNumbersDecimalOnly(event);
  }

  parseFloatCustom(cantidad: number) {
    // @ts-ignore
    return parseFloat(Math.round(cantidad * 100) / 100).toFixed(2)
  }
}
