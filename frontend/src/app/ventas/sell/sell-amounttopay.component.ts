import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {Variante} from "../../models/variante.model";

@Component({
  selector: 'app-sell-amounttopay',
  templateUrl: './sell-amounttopay.component.html',
  styleUrls: ['./sell-amounttopay.component.scss']
})
export class SellAmounttopayComponent {

  constructor(
    public dialogRef: MatDialogRef<SellAmounttopayComponent>,
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
