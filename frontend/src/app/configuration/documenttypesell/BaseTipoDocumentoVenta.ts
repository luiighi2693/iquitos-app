import {Base} from "../../base/Base";
import {FullService} from "../../layouts/full/full.service";
import {MatDialog, MatTableDataSource} from "@angular/material";
import {HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {JhiDataUtils} from "ng-jhipster";
import {ElementRef} from "@angular/core";
import {ITipoDeDocumentoDeVenta, TipoDeDocumentoDeVenta} from "../../models/tipo-de-documento-de-venta.model";
import {TipoDeDocumentoDeVentaService} from "./tipo-de-documento-de-venta.service";

declare var require: any;

export class BaseTipoDocumentoVenta extends Base<ITipoDeDocumentoDeVenta, TipoDeDocumentoDeVenta> {
  constructor(public service: TipoDeDocumentoDeVentaService,
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
          (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => this.paginate(res.body, res.headers),
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
        (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => this.paginate(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  paginate(data: TipoDeDocumentoDeVenta[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.data = data;

    this.dataSource = new MatTableDataSource<TipoDeDocumentoDeVenta>(this.data);
  }

}
