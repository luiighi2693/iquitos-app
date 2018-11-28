import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-documenttypesell-delete',
  templateUrl: './documenttypesell-delete.component.html',
  styleUrls: ['./documenttypesell-delete.component.scss']
})
export class DocumenttypesellDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<DocumenttypesellDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
