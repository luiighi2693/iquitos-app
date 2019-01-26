import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {TipoDePagoService} from "../../configuration/paymenttype/tipo-de-pago.service";
import {ITipoDePago, TipoDePago} from "../../models/tipo-de-pago.model";
import {ITipoDeDocumentoDeVenta, TipoDeDocumentoDeVenta} from "../../models/tipo-de-documento-de-venta.model";
import {TipoDeDocumentoDeVentaService} from "../../configuration/documenttypesell/tipo-de-documento-de-venta.service";
import Util from "../../shared/util/util";
import {Amortizacion} from "../../models/amortizacion.model";

@Component({
  selector: 'app-sell-extra-info',
  templateUrl: './sell-extra-info.component.html',
  styleUrls: ['./sell-extra-info.component.scss']
})
export class SellExtraInfoComponent {

  public paymentTypes: TipoDePago[] = [];
  public documentTypeSells: TipoDeDocumentoDeVenta[] = [];
  amortization: Amortizacion = new Amortizacion();

  constructor(
    public dialogRef: MatDialogRef<SellExtraInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public tipoDePagoService: TipoDePagoService,
    public tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaService
  ) {
    console.log(this.data);
    this.amortization.montoPagado = this.data.entity.montoTotal;
    this.amortization.monto = this.data.entity.montoTotal;

    this.tipoDePagoService.query().subscribe(
      (res: HttpResponse<ITipoDePago[]>) => {
        this.paymentTypes = res.body;
        if (this.paymentTypes.length > 0){
          this.data.entity.tipoDePagoId = this.paymentTypes[0].id;
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.tipoDeDocumentoDeVenta.query().subscribe(
      (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => {
        this.documentTypeSells = res.body;
        if (this.documentTypeSells.length > 0){
          this.data.entity.tipoDeDocumentoDeVentaId = this.documentTypeSells[0].id;
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  closeAndCheckSell(param: string) {
      if (param ==='exit') {
        this.dialogRef.close({
          entity: null,
          amortization: null,
          flag: param
        });
      }

    if (param ==='save') {
      this.dialogRef.close({
        entity: this.data.entity,
        amortization: null,
        flag: param
      });
    }

    if (param ==='pay') {
      this.dialogRef.close({
        entity: this.data.entity,
        amortization: this.amortization,
        flag: param
      });
    }
  }

  onError(errorMessage: string) {
    console.log(errorMessage);
  }

  checkNumbersDecimalOnly(event: KeyboardEvent) {
    return Util.checkNumbersDecimalOnly(event);
  }

  checkNumbersOnly(event: any): boolean {
    return Util.checkNumbersOnly(event);
  }

  parseFloatCustom(cantidad: number) {
    // @ts-ignore
    return parseFloat(Math.round(cantidad * 100) / 100).toFixed(2)
  }
}
