import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-relatedproductstag-delete',
  templateUrl: './relatedproductstag-delete.component.html',
  styleUrls: ['./relatedproductstag-delete.component.scss']
})
export class RelatedproductstagDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<RelatedproductstagDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
