import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {BaseVenta} from "./BaseVenta";

declare var require: any;

@Component({
  selector: 'app-sell-detail',
  templateUrl: './sell-detail.component.html',
  styleUrls: ['./sell-detail.component.scss']
})
export class SellDetailComponent extends BaseVenta implements OnInit {
  constructor(private activatedRoute: ActivatedRoute) {
    super(null,null,null,null, null, null, require('../menu.json'), 'VENTAS');
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;
    });
  }

}
