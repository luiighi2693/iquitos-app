import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-sell-delete',
  templateUrl: './sell-delete.component.html',
  styleUrls: ['./sell-delete.component.scss']
})
export class SellDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<SellDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
