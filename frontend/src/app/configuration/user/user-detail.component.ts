import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {IUsuarioExterno} from "../../models/usuario-externo.model";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {
  entity: IUsuarioExterno;

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
