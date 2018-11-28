import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-purchasestatus-delete',
  templateUrl: './purchasestatus-delete.component.html',
  styleUrls: ['./purchasestatus-delete.component.scss']
})
export class PurchasestatusDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<PurchasestatusDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
