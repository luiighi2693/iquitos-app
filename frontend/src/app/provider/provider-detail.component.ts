import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IProveedor } from '../models/proveedor.model';

@Component({
  selector: 'app-provider-detail',
  templateUrl: './provider-detail.component.html',
  styleUrls: ['./provider-detail.component.scss']
})
export class ProviderDetailComponent implements OnInit {
  proveedor: IProveedor;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
    });
  }

  previousState() {
    window.history.back();
  }

}
