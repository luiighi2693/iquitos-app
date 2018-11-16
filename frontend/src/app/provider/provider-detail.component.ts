import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IProveedor } from '../models/proveedor.model';
import { Observable, Observer } from 'rxjs';
import { MatTableDataSource } from '@angular/material';
import { IContactoProveedor } from '../models/contactoproveedor.model';

declare var require: any;

const dataIn: any = require('./data.json');


export interface ExampleTab {
  label: string;
}

@Component({
  selector: 'app-provider-detail',
  templateUrl: './provider-detail.component.html',
  styleUrls: ['./provider-detail.component.scss']
})
export class ProviderDetailComponent implements OnInit {
  proveedor: IProveedor;
  contactos: IContactoProveedor[];

  asyncTabs: Observable<ExampleTab[]>;

  displayedColumnsContactos = ['nombre', 'cargo', 'telefono', 'producto'];
  dataSource = new MatTableDataSource<IContactoProveedor>(null);

  constructor(private activatedRoute: ActivatedRoute) {
    this.asyncTabs = Observable.create((observer: Observer<ExampleTab[]>) => {
      setTimeout(() => {
        observer.next([
          { label: 'Contactos' },
          { label: 'Cuentas' }
        ]);
      }, 1000);
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      // this.proveedor = proveedor;

    });

    this.proveedor = dataIn[0];
    this.contactos = this.proveedor.contactos;
    console.log(this.contactos);
    this.dataSource = new MatTableDataSource<IContactoProveedor>(this.contactos);
  }

  previousState() {
    window.history.back();
  }

}
