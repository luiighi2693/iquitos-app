import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {IProducto} from "../../models/producto.model";

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit {
  entity: IProducto;

  constructor(private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;
    });
  }

  previousState() {
    window.history.back();
  }

}
