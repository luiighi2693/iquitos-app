import {Base} from "../../base/Base";
import {IVenta, SellStatus, Venta} from "../../models/venta.model";
import {FullService} from "../../layouts/full/full.service";
import {MatDialog, MatTableDataSource} from "@angular/material";
import {HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {JhiDataUtils} from "ng-jhipster";
import {ElementRef} from "@angular/core";
import {VentaService} from "../sell/venta.service";

declare var require: any;

export class BaseAmortizacion extends Base<IVenta, Venta> {
  constructor(public service: VentaService,
              public fullService: FullService,
              public displayedColumns: string[],
              public dialog: MatDialog,
              public dataUtils: JhiDataUtils,
              public elementRef: ElementRef,
              public menu: any,
              public menuDisplay: string) {
    super(fullService, menu,
      menuDisplay, displayedColumns, dialog, dataUtils, elementRef);
    this.itemsPerPage = 20;
  }

  loadAll() {
    super.loadAll();
    if (this.currentSearch) {
      this.service
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IVenta[]>) => this.paginate(res.body, res.headers),
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
        (res: HttpResponse<IVenta[]>) => this.paginate(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  paginate(data: Venta[], headers: HttpHeaders) {
    this.isSaving = false;
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    if (data.length > 0) {
      data = this.filterByPendingSell(data);
    }
    this.data = data;

    this.dataSource = new MatTableDataSource<Venta>(this.data);
  }

  filterByPendingSell(data: Venta[]): Venta[] {
    let dataReturn: Venta[] = [];

    data.forEach(sell => {
      if (sell.amortizacions.length > 0 && sell.estatus === SellStatus.PENDIENTE) {
        dataReturn.push(sell);
      }
    });

    return dataReturn;
  }

  save() {
    super.save();
    if (this.entity.id !== undefined) {
      this.subscribeToSaveResponse(this.service.update(this.entity));
    } else {
      this.subscribeToSaveResponse(this.service.create(this.entity));
    }
  }

  // deleteEntity(row) {
  //   super.deleteEntity(row);
  //
  //   this.service.delete(row.id).subscribe(response => {
  //   });
  //
  //   const index = this.dataSource.data.map(x => x.id).indexOf(row.id);
  //   if (index !== -1) {
  //     this.dataSource.data.splice(index, 1);
  //     this.dataSource = new MatTableDataSource<IVenta>(this.dataSource.data);
  //   }
  // }

  // openDialog(entity): void {
  //   super.openDialog(entity);
  //   const dialogRef = this.dialog.open(SellDeleteComponent, {
  //     width: '300px',
  //     data: { entity: entity }
  //   });
  //
  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result !== undefined) {
  //       this.deleteEntity(result);
  //     }
  //   });
  // }
}
