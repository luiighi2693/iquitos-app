import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {IEmpleado} from "../../models/empleado.model";

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.scss']
})
export class EmployeeDetailComponent implements OnInit {
  entity: IEmpleado;

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
