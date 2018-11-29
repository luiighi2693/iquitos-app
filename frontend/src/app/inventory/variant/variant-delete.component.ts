import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-variant-delete',
  templateUrl: './variant-delete.component.html',
  styleUrls: ['./variant-delete.component.scss']
})
export class VariantDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<VariantDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
