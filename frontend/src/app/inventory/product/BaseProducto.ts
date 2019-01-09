import {Base} from "../../base/Base";
import {IProducto, Producto} from "../../models/producto.model";
import {ProductoService} from "./producto.service";
import {FullService} from "../../layouts/full/full.service";
import {MatDialog, MatTableDataSource} from "@angular/material";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {ProductDeleteComponent} from "./product-delete.component";
import {JhiDataUtils} from "ng-jhipster";
import {ElementRef} from "@angular/core";

declare var require: any;

export class BaseProducto extends Base<IProducto, Producto> {
  constructor(public service: ProductoService,
              public fullService: FullService,
              public displayedColumns: string[],
              public dialog: MatDialog,
              public dataUtils: JhiDataUtils,
              public elementRef: ElementRef) {
    super(fullService, require('../menu.json'),
      'PRODUCTOS', displayedColumns, dialog, dataUtils, elementRef);
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
      this.dataSource = new MatTableDataSource<IProducto>(this.dataSource.data);
    }
  }

  openDialog(entity): void {
    super.openDialog(entity);
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
