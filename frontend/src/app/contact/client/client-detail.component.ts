import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {ICliente} from "../../models/cliente.model";

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.scss']
})
export class ClientDetailComponent implements OnInit {
  entity: ICliente;

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
