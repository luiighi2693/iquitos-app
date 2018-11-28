import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-documenttype-delete',
  templateUrl: './documenttype-delete.component.html',
  styleUrls: ['./documenttype-delete.component.scss']
})
export class DocumenttypeDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<DocumenttypeDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
