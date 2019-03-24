import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-sell-show-image',
  templateUrl: './sell-show-image.component.html',
  styleUrls: ['./sell-show-image.component.scss']
})
export class SellShowImageComponent {

  constructor(
    public dialogRef: MatDialogRef<SellShowImageComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
