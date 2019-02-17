import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {Variante} from "../../models/variante.model";

@Component({
  selector: 'app-sell-notification-error',
  templateUrl: './sell-notification-error.component.html',
  styleUrls: ['./sell-notification-error.component.scss']
})
export class SellNotificationErrorComponent {

  constructor(
    public dialogRef: MatDialogRef<SellNotificationErrorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    console.log(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
