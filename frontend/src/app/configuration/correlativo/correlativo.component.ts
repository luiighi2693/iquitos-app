import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import {FullService} from '../../layouts/full/full.service';
import {UnidadDeMedida} from "../../models/producto.model";
import {SubUnidadDeMedida} from "../../inventory/product/product-update.component";
import {ITipoDeDocumentoDeVenta} from "../../models/tipo-de-documento-de-venta.model";
import {TipoDeDocumentoDeVentaService} from "../documenttypesell/tipo-de-documento-de-venta.service";

declare var require: any;
const menu: any = require('../menu.json');

export interface Correlativo {
  label: string;
  serie: string;
  valor: string;
}

@Component({
  selector: 'app-user',
  templateUrl: './correlativo.component.html',
  styleUrls: ['./correlativo.component.scss']
})
export class CorrelativoComponent implements OnInit, OnDestroy {

  public correlativos: Correlativo[] = [];
  displayedColumns = ['label', 'serie', 'correlativo'];
  dataSource = new MatTableDataSource<Correlativo>(null);
  selection = new SelectionModel<Correlativo>(true, []);

  constructor(public fullService: FullService, public tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService) {
    this.fullService.changeMenu(menu);
    this.fullService.changeMenuSelected('CORRELATIVOS');

    this.tipoDeDocumentoDeVentaService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => {
        res.body.forEach(item => {
          let correlativo: Correlativo = {
            label: item.nombre,
            serie: sessionStorage.getItem('correlativo.'+item.nombre),
            valor: sessionStorage.getItem('correlativo.contador.'+item.nombre)
          };

          this.correlativos.push(correlativo);
        });

        this.dataSource = new MatTableDataSource<Correlativo>(this.correlativos);
      },
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

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  ngOnInit() {

  }

  ngOnDestroy() {
  }


  private onError(errorMessage: string) {
    console.log(errorMessage);
  }
}
