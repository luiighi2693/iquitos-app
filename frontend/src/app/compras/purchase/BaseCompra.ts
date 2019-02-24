import {Base} from "../../base/Base";
import {FullService} from "../../layouts/full/full.service";
import {MatDialog, MatTableDataSource} from "@angular/material";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
// import {SellDeleteComponent} from "./sell-delete.component";
import {JhiDataUtils} from "ng-jhipster";
import {ElementRef} from "@angular/core";
import {Compra, ICompra} from "../../models/compra.model";
import {CompraService} from "./compra.service";

declare var require: any;

export class BaseCompra extends Base<ICompra,Compra> {
  constructor(public service:CompraService,
              public fullService: FullService,
              public displayedColumns: string[],
              public dialog: MatDialog,
              public dataUtils: JhiDataUtils,
              public elementRef: ElementRef,
              public menu: any,
              public menuDisplay: string) {
    super(fullService, menu,
      menuDisplay, displayedColumns, dialog, dataUtils, elementRef);
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
          (res: HttpResponse<ICompra[]>) => this.paginate(res.body, res.headers),
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
        (res: HttpResponse<ICompra[]>) => this.paginate(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  save() {
    super.save();
    if (this.entity.id !== undefined) {
      this.subscribeToSaveResponse(this.service.update(this.entity));
    } else {
      this.subscribeToSaveResponse(this.service.create(this.entity));
    }
  }

  deleteEntity(row) {
    super.deleteEntity(row);

    this.service.delete(row.id).subscribe(response => {
    });

    const index = this.dataSource.data.map(x => x.id).indexOf(row.id);
    if (index !== -1) {
      this.dataSource.data.splice(index, 1);
      this.dataSource = new MatTableDataSource<ICompra>(this.dataSource.data);
    }
  }

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
