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
import {AmortizacionService} from "../amortizacion/amortizacion.service";

@Component({
  selector: 'app-sell-extra-info',
  templateUrl: './sell-extra-info.component.html',
  styleUrls: ['./sell-extra-info.component.scss']
})
export class SellExtraInfoComponent {

  public paymentTypes: TipoDePago[] = [];
  public documentTypeSells: TipoDeDocumentoDeVenta[] = [];
  public documentTypeSellsForSeries: TipoDeDocumentoDeVenta[] = [];
  public documentTypeSellSelected: TipoDeDocumentoDeVenta = null;
  public correlatives: number[]= [];
  public correlative: number= null;
  amortization: Amortizacion = new Amortizacion();
  isCredit = false;

  @ViewChild('amountToPay') amountToPayField: ElementRef;

  constructor(
    public dialogRef: MatDialogRef<SellExtraInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public tipoDePagoService: TipoDePagoService,
    public tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaService,
    public service: AmortizacionService
  ) {
    console.log(this.data);
    this.amortization.montoPagado = this.data.entity.montoTotal;
    this.amortization.monto = this.data.entity.montoTotal;
    this.amortization.codigoDocumento = this.data.client.codigo;

    this.data.entity.diasCredito = 0;

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
          switch (this.data.client.tipoDeCliente) {
            case ClientType.JURIDICO: {
              this.filterDocumentTypeSellContent(ClientType.JURIDICO);
              break;
            }

            case ClientType.NATURAL: {
              this.filterDocumentTypeSellContent(ClientType.NATURAL);
              break;
            }
          }
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

      this.updateEntityCustom();

      this.dialogRef.close({
        entity: this.data.entity,
        amortization: this.amortization,
        serie: this.documentTypeSellSelected,
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

  validatePaymentInvalid(){
    if (this.isCredit) {
      return (this.data.entity.diasCredito.toString() === '' || this.data.entity.diasCredito.toString() === '0');
    } else {
      return (this.amortization.montoPagado.toString() === '' || this.amortization.montoPagado.toString() === '0');
    }
  }

  public updateEntityCustom() {
    this.amortization.codigo = this.documentTypeSellSelected.serie + '-00000' + Math.round(Math.random() * (10000 - 1) + 1);
  }

  validateCredit(){
    this.selectInputAmount();

    this.isCredit = false;
    if (this.paymentTypes.find(x => x.id === this.data.entity.tipoDePagoId).nombre === 'Credito' ) {
      this.isCredit = true;
      this.amortization.montoPagado = 0.0;
    }
  }

  public filterDocumentTypeSellContent(clientType: ClientType) {
    if (clientType === ClientType.NATURAL) {
      this.data.entity.tipoDeDocumentoDeVentaId = this.documentTypeSells.find(x => x.nombre === 'Boleta Electronica').id;
      this.documentTypeSells = this.documentTypeSells.filter(x => x.nombre !== 'Factura Electronica');
    } else {
      this.data.entity.tipoDeDocumentoDeVentaId = this.documentTypeSells.find(x => x.nombre === 'Factura Electronica').id;
      this.documentTypeSells = this.documentTypeSells.filter(x => x.nombre === 'Factura Electronica');
    }

    this.documentTypeSellSelected = this.documentTypeSells.find(x => x.id === this.data.entity.tipoDeDocumentoDeVentaId);
    this.documentTypeSellsForSeries = [this.documentTypeSellSelected];

    this.service.countByDocumentTypeSellId(this.documentTypeSellSelected.id).subscribe(
      (res: HttpResponse<number>) => {
        this.correlatives = [res.body];
        this.correlative = res.body
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  public filterDocumentTypeSellContentForSerie() {
    this.documentTypeSellSelected = this.documentTypeSells.find(x => x.id === this.data.entity.tipoDeDocumentoDeVentaId);
    this.documentTypeSellsForSeries = [this.documentTypeSellSelected];

    this.service.countByDocumentTypeSellId(this.documentTypeSellSelected.id).subscribe(
      (res: HttpResponse<number>) => {
        this.correlatives = [res.body + 1];
        this.correlative = res.body + 1
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngAfterViewInit() {
    this.selectInputAmount();
  }

  private selectInputAmount() {
    setTimeout(() => {
      this.amountToPayField.nativeElement.focus();
      this.amountToPayField.nativeElement.select();
    }, 300);
  }
}
