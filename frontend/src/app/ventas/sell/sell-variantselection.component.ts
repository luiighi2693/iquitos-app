import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {Variante} from "../../models/variante.model";

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
}
