import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import {FullService} from '../../layouts/full/full.service';
import {UserDeleteComponent} from "./user-delete.component";
import {IUsuarioExterno} from "../../models/usuario-externo.model";
import {UsuarioExternoService} from "./usuario-externo.service";
import {ProveedorService} from "../../contact/provider/proveedor.service";
import {ClienteService} from "../../contact/client/cliente.service";
import {EmpleadoService} from "../../contact/employee/empleado.service";
import {ICliente} from "../../models/cliente.model";
import {IProveedor} from "../../models/proveedor.model";
import {IEmpleado} from "../../models/empleado.model";

declare var require: any;
const menu: any = require('../menu.json');

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {
  entities: IUsuarioExterno[];
  itemsPerPage: number;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;
  entity: IUsuarioExterno;

  constructor(private service: UsuarioExternoService, public dialog: MatDialog, public fullService: FullService,
              private providerService:ProveedorService,
              private clientService:ClienteService,
              private employeeService:EmpleadoService,) {
    this.entities = [];
    this.itemsPerPage = 500;
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.fullService.changeMenu(menu);
    this.fullService.changeMenuSelected('USUARIOS');
  }

  displayedColumns = ['select', 'dni', 'userType', 'star'];
  dataSource = new MatTableDataSource<IUsuarioExterno>(null);
  selection = new SelectionModel<IUsuarioExterno>(true, []);

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
          (res: HttpResponse<IUsuarioExterno[]>) => this.paginate(res.body, res.headers),
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
        (res: HttpResponse<IUsuarioExterno[]>) => this.paginate(res.body, res.headers),
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
      this.dataSource = new MatTableDataSource<IUsuarioExterno>(this.dataSource.data);
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  save() {
    if (this.entity.id !== undefined) {
      this.subscribeToSaveResponse(this.service.update(this.entity));
    } else {
      this.subscribeToSaveResponse(this.service.create(this.entity));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioExterno>>) {
    result.subscribe((res: HttpResponse<IUsuarioExterno>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.loadAll();
  }

  private onSaveError() {
    // this.isSaving = false;
  }

  reset() {
    this.page = 0;
    this.entities = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.entities = [];
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
    this.entities = [];
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

  private paginate(data: IUsuarioExterno[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.entities = data;
    this.dataSource = new MatTableDataSource<IUsuarioExterno>(this.entities);
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  openDialog(entity): void {
    const dialogRef = this.dialog.open(UserDeleteComponent, {
      width: '300px',
      data: { entity: entity }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.updateSubEntity(result.userType, result.idEntity, result);
        // this.deleteEntity(result);
      }
    });
  }

  public updateSubEntity(entityType, entityId, row) {
    switch (entityType) {
      case 'PROVEEDOR': {
        this.providerService.find(entityId).subscribe(
          (res: HttpResponse<IProveedor>) => {
            let subEntity = res.body;
            subEntity.usuarioDni = null;
            subEntity.usuarioId = null;

            this.providerService.update(subEntity).subscribe(
              (res: HttpResponse<IProveedor>) => {
                // this.cliente = res.body;
                this.deleteEntity(row);
              },
              (res: HttpErrorResponse) => this.onError(res.message)
            );
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      case 'CLIENTE': {
        this.clientService.find(entityId).subscribe(
          (res: HttpResponse<ICliente>) => {
            let subEntity = res.body;
            subEntity.usuarioDni = null;
            subEntity.usuarioId = null;

            this.clientService.update(subEntity).subscribe(
              (res: HttpResponse<ICliente>) => {
                // this.cliente = res.body;
                this.deleteEntity(row);
              },
              (res: HttpErrorResponse) => this.onError(res.message)
            );
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      case 'EMPLEADO': {
        this.employeeService.find(entityId).subscribe(
          (res: HttpResponse<IEmpleado>) => {
            let subEntity = res.body;
            subEntity.usuarioDni = null;
            subEntity.usuarioId = null;

            this.employeeService.update(subEntity).subscribe(
              (res: HttpResponse<IEmpleado>) => {
                // this.cliente = res.body;
                this.deleteEntity(row);
              },
              (res: HttpErrorResponse) => this.onError(res.message)
            );
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }
      default: {
        break;
      }
    }
  }
}
