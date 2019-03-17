import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VentaService} from "../../ventas/sell/venta.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {IEmpleado} from "../../models/empleado.model";
import {IVenta} from "../../models/venta.model";
import {ClienteService} from "../../contact/client/cliente.service";
import {ICliente} from "../../models/cliente.model";

import * as moment from 'moment';
import {IAmortizacion} from "../../models/amortizacion.model";

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice2.component.html',
  styleUrls: ['./invoice2.component.scss']
})
export class Invoice2Component implements OnInit{
  invoiceItems: any = [
    {
      title: 'Ample Admin',
      subtitle: 'The ultimate admin template',
      price: 499.0,
      quantity: 1
    },
    {
      title: 'Material Pro Admin',
      subtitle: 'Material Based admin template',
      price: 399.0,
      quantity: 1
    },
    {
      title: 'Wrapkit',
      subtitle: 'Bootstrap 4 UI kit',
      price: 599.0,
      quantity: 1
    },
    {
      title: 'Admin Wrap',
      subtitle: 'Free admin template with UI kit',
      price: 0.0,
      quantity: 1
    }
  ];

  invoiceTotals: any = [
    {
      subtotal: this.getSubTotal(),
      tax: this.getCalculatedTax(),
      discount: 0.0,
      total: 0
    }
  ];
  public venta: IVenta;
  public cliente: ICliente;
  public amortizationSelected: IAmortizacion;

  getSubTotal() {
    let total = 0.0;
    for (let i = 1; i < this.invoiceItems.length; i++) {
      total += this.invoiceItems[i].price * this.invoiceItems[i].quantity;
    }
    return total;
  }

  getCalculatedTax() {
    return (2 * this.getSubTotal()) / 100;
  }

  getTotal() {
    return this.getSubTotal() + this.getCalculatedTax();
  }

  constructor(route: ActivatedRoute,
              private router: Router,
              ventaService: VentaService,
              clienteService: ClienteService) {
    console.log(route.snapshot.params['id']);

    ventaService.find(route.snapshot.params['id']).subscribe(
      (res: HttpResponse<IVenta>) => {
        this.venta = res.body;
        this.amortizationSelected = this.venta.amortizacions[route.snapshot.params['index']];
        console.log(this.venta);

        if (this.venta.clienteId !== null) {
          clienteService.find(this.venta.clienteId).subscribe(
            (res: HttpResponse<ICliente>) => {
              this.cliente = res.body;
              this.printInvoiceAndReturn();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
          );
        } else {
          this.printInvoiceAndReturn();
        }
        
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit(): void {
    // this.print();
  }

  onError(errorMessage: string) {
    console.log(errorMessage);
  }

  print(){
    window.print();
  }

  getFormatedDate(): string {
    return moment(this.venta.fecha).format("DD-MM-YYYY");
  }

  parseFloatCustom(cantidad: number) {
    // @ts-ignore
    return parseFloat(Math.round(cantidad * 100) / 100).toFixed(2)
  }

  private printInvoiceAndReturn() {
    setTimeout(() => {
      window.print();
      this.router.navigate(['/ventas/sell/list']);
    });
  }
}
