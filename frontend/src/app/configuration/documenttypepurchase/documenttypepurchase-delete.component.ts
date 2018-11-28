import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-documenttypepurchase-delete',
  templateUrl: './documenttypepurchase-delete.component.html',
  styleUrls: ['./documenttypepurchase-delete.component.scss']
})
export class DocumenttypepurchaseDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<DocumenttypepurchaseDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
