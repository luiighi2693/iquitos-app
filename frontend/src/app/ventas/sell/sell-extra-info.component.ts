import {Component, ElementRef, Inject, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {TipoDePagoService} from "../../configuration/paymenttype/tipo-de-pago.service";
import {ITipoDePago, TipoDePago} from "../../models/tipo-de-pago.model";
import {ITipoDeDocumentoDeVenta, TipoDeDocumentoDeVenta} from "../../models/tipo-de-documento-de-venta.model";
import {TipoDeDocumentoDeVentaService} from "../../configuration/documenttypesell/tipo-de-documento-de-venta.service";
import Util from "../../shared/util/util";
import {Amortizacion} from "../../models/amortizacion.model";
import {ClientType} from "../../models/cliente.model";

@Component({
  selector: 'app-sell-extra-info',
  templateUrl: './sell-extra-info.component.html',
  styleUrls: ['./sell-extra-info.component.scss']
})
export class SellExtraInfoComponent {

  public paymentTypes: TipoDePago[] = [];
  public documentTypeSells: TipoDeDocumentoDeVenta[] = [];
  amortization: Amortizacion = new Amortizacion();
  isCredit = false;

  // @ViewChild('inputAmount') inputAmount: ElementRef;

  constructor(
    public dialogRef: MatDialogRef<SellExtraInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public tipoDePagoService: TipoDePagoService,
    public tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaService
  ) {
    console.log(this.data);
    this.amortization.montoPagado = this.data.entity.montoTotal;
    this.amortization.monto = this.data.entity.montoTotal;
    this.amortization.codigoDocumento = this.data.client.codigo;

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
          this.data.entity.tipoDeDocumentoDeVentaId = this.data.client.tipoDeCliente === ClientType.JURIDICO ?
            this.documentTypeSells.find(x => x.nombre === 'Factura Electronica').id :
            this.documentTypeSells.find(x => x.nombre === 'Boleta Electronica').id
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
      this.amortization.tipoDeDocumentoDeVentaId = this.data.entity.tipoDeDocumentoDeVentaId;
      this.amortization.tipoDePagoId = this.data.entity.tipoDePagoId;
      this.amortization.glosa = this.data.entity.glosa;

      let tipoDocumentoVenta = this.documentTypeSells.find(x => x.id === this.amortization.tipoDeDocumentoDeVentaId);
      const countTipoDocumentoVenta = sessionStorage.getItem('correlativo.contador.' + tipoDocumentoVenta.nombre);
      sessionStorage.setItem('correlativo.contador.' + tipoDocumentoVenta.nombre, (parseInt(countTipoDocumentoVenta) + 1).toString());

      this.updateEntityCustom();

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

  validateDocumentCodeInvalid(){
    if (this.amortization.codigoDocumento === undefined) {
      return true;
    } else {
      return this.amortization.codigoDocumento.length === 0;
    }
  }

  public updateEntityCustom() {
    let flag = true;

    if (this.documentTypeSells.find(x => x.nombre === 'Boleta Electronica').id === this.amortization.tipoDeDocumentoDeVentaId) {
      this.amortization.codigo = 'B001-00000' + Math.round(Math.random() * (10000 - 1) + 1);
      flag = false;
    }

    if (this.documentTypeSells.find(x => x.nombre === 'Factura Electronica').id === this.amortization.tipoDeDocumentoDeVentaId) {
      this.amortization.codigo = 'F001-00000' + Math.round(Math.random() * (10000 - 1) + 1);
      flag = false;
    }

    if (flag) {
      this.amortization.codigo = 'M001-00000' + Math.round(Math.random() * (10000 - 1) + 1);
    }
  }

  validateCredit() {
    // this.selectInputAmount();

    this.isCredit = false;
    if (this.paymentTypes.find(x => x.id === this.data.entity.tipoDePagoId).nombre === 'Credito' ) {
      this.isCredit = true;
      this.amortization.montoPagado = 0.0;
    }
  }

  // selectInputAmount() {
  //   this.inputAmount.nativeElement.focus();
  // }
}
