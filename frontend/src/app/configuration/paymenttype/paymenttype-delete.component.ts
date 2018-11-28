import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-paymenttype-delete',
  templateUrl: './paymenttype-delete.component.html',
  styleUrls: ['./paymenttype-delete.component.scss']
})
export class PaymenttypeDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<PaymenttypeDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
