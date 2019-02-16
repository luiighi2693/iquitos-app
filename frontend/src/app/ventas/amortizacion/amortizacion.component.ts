import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {FullService} from '../../layouts/full/full.service';
import {BaseAmortizacion} from "./BaseAmortizacion";
import {VentaService} from "../sell/venta.service";
import {IVenta, SellStatus, Venta} from "../../models/venta.model";
import {SelectionModel} from "@angular/cdk/collections";
import {MatCheckboxChange} from "@angular/material/typings/checkbox";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AmortizacionExtraInfoComponent} from "./amortizacion-extra-info.component";
import {Cliente, ICliente} from "../../models/cliente.model";
import {ClienteService} from "../../contact/client/cliente.service";

declare var require: any;
import * as moment from 'moment';
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {Amortizacion} from "../../models/amortizacion.model";
import {AmortizacionService} from "./amortizacion.service";

@Component({
  selector: 'app-amortizacion',
  templateUrl: './amortizacion.component.html',
  styleUrls: ['./amortizacion.component.scss']
})
export class AmortizacionComponent extends BaseAmortizacion implements OnInit, OnDestroy {

  clientes: ICliente[];
  public serie: any;

  constructor(public service: VentaService, public fullService: FullService,
              public dialog: MatDialog, public clienteService: ClienteService,
              private router: Router,private amortizacionService: AmortizacionService) {
    super(
      service,
      fullService,
      ['select', 'codigo', 'fecha', 'clienteNombre', 'total', 'amortizado', 'restante'],
      // ['select', 'codigo', 'fecha', 'clienteNombre', 'total', 'amortizado', 'restante', 'documento', 'glosa'],
      dialog, null, null, require('../menu.json'), 'COBROS');

    this.selection = new SelectionModel<Venta>(false, []);
    this.entity = undefined;
  }

  ngOnInit() {

    this.clienteService.query().subscribe(
      (res: HttpResponse<ICliente[]>) => {
        this.clientes = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.loadAll();
  }

  ngOnDestroy() {
  }

  save() {
    super.save();
  }

  public subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
    result.subscribe((res: HttpResponse<IVenta>) => this.onSaveSuccessCustom(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  public onSaveSuccessCustom(sell) {
    this.isSaving = false;
    this.amortizacionService.countByDocumentTypeSellId(sell.amortizacions[sell.amortizacions.length-1].tipoDeDocumentoDeVentaId).subscribe(
      (res: HttpResponse<number>) => {
        sell.amortizacions[sell.amortizacions.length-1].codigo = this.serie.serie+'-00000'+res.body.toString();
        this.amortizacionService.update(sell.amortizacions[sell.amortizacions.length-1]).subscribe(
          (res: HttpResponse<Amortizacion>) => {
            console.log(res.body);
            this.router.navigate(['/print/invoice2/' + sell.id + '/' + (sell.amortizacions.length - 1)]);
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getMontoAmortizado(sell: Venta): number {
    return sell.amortizacions.map(x => x.montoPagado).reduce((a, b) => a + b, 0)
  }

  getDocumentListFromSell(sell: Venta): string {
    let documents : string = '';
    sell.amortizacions.forEach(amortization => {
      documents += amortization.tipoDeDocumentoDeVentaNombre +' '+ amortization.codigo + ', ';
    });
    documents = documents.substring(0, documents.length - 2);
    return documents;
  }

  changeToggle($event: MatCheckboxChange, row: any) {
    if ($event.checked) {
      this.entity = row;
    } else {
      this.entity = undefined;
    }
    return $event ? this.selection.toggle(row) : null;
  }

  addExtraInfoSell() {
    const dialogRef = this.dialog.open(AmortizacionExtraInfoComponent, {
      width: '80%',
      data: {
        entity: this.entity,
        client: this.clientes.find(x => x.id === this.entity.clienteId)
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result !== undefined) {
        if (result.flag === 'pay'){
          this.serie = result.serie;
          this.entity.amortizacions.push(result.amortization);
          this.entity.estatus = this.getMontoAmortizado(this.entity) >= parseFloat(result.amortization.monto) ? SellStatus.COMPLETADO : SellStatus.PENDIENTE;

          this.entity.amortizacions[this.entity.amortizacions.length - 1].fecha = moment();
          this.save();
        }
      }
    });
  }
}
