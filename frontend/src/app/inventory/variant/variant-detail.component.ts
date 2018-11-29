import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {IVariante} from "../../models/variante.model";

@Component({
  selector: 'app-variant-detail',
  templateUrl: './variant-detail.component.html',
  styleUrls: ['./variant-detail.component.scss']
})
export class VariantDetailComponent implements OnInit {
  entity: IVariante;

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
