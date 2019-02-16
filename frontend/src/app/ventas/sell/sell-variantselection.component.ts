import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {Variante} from "../../models/variante.model";
import {IProducto} from "../../models/producto.model";

@Component({
  selector: 'app-sell-variantselection',
  templateUrl: './sell-variantselection.component.html',
  styleUrls: ['./sell-variantselection.component.scss']
})
export class SellVariantselectionComponent {

  constructor(
    public dialogRef: MatDialogRef<SellVariantselectionComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    console.log(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResult(isVariant: boolean, variant: Variante) {
    this.dialogRef.close({
      isVariant: isVariant,
      variant: variant
    });
  }

  public getStockConsumFromProduct(product: IProducto) {
    let sum = 0;
    this.data.entityByStock.productoDetalles.filter(x => x.productos[0].id === product.id ).forEach(productDetalle => {
      if (productDetalle.variantes.length === 0) {
        //unidad
        sum += productDetalle.cantidad;
      } else {
        sum += productDetalle.variantes[0].cantidad * productDetalle.cantidad;
      }
    });

    return sum;
  }
}
