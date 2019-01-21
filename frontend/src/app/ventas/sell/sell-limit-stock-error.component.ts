import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {Variante} from "../../models/variante.model";

@Component({
  selector: 'app-sell-limit-stock-error',
  templateUrl: './sell-limit-stock-error.component.html',
  styleUrls: ['./sell-limit-stock-error.component.scss']
})
export class SellLimitStockErrorComponent {

  constructor(
    public dialogRef: MatDialogRef<SellLimitStockErrorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    console.log(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
