import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-provider-delete',
  templateUrl: './provider-delete.component.html',
  styleUrls: ['./provider-delete.component.scss']
})
export class ProviderDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<ProviderDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
