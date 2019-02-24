import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import {FullService} from '../../layouts/full/full.service';
import {BaseCompra} from "./BaseCompra";
import {CompraService} from "./compra.service";

declare var require: any;

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.scss']
})
export class PurchaseComponent extends BaseCompra implements OnInit, OnDestroy {

  /**mapeo
   * proveedor: proveedorRazonSocial (implementar)
   * comprobante: tipoDeDocumentoDeCompraNombre numeroDeDocumento
   * correlativo: fecha(MESABREBIADO) + fecha(AÑO) + -000 + id (EJ: FEB2019-00060)
   * fecha: fecha
   * total: S./ montoTotal
   * transacción: tipoDeTransaccion
   * estado: estatus
   * **/

  constructor(public service: CompraService, public fullService: FullService, public dialog: MatDialog) {
    super(
      service,
      fullService,
      ['select', 'proveedor', 'comprobante', 'correlativo', 'fecha', 'total', 'transacción', 'estado', 'star'],
      dialog, null, null, require('../menu.json'), 'COMPRAS');
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
  }
}
