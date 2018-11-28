import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-purchasestatus-delete',
  templateUrl: './deliverystatus-delete.component.html',
  styleUrls: ['./deliverystatus-delete.component.scss']
})
export class DeliverystatusDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<DeliverystatusDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
