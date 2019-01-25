import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-systemparam-delete',
  templateUrl: './systemparam-delete.component.html',
  styleUrls: ['./systemparam-delete.component.scss']
})
export class SystemparamDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<SystemparamDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
