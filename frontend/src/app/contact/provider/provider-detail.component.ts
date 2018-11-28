import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IProveedor } from '../../models/proveedor.model';
import { Observable, Observer } from 'rxjs';
import { MatTableDataSource } from '@angular/material';
import { IContactoProveedor } from '../../models/contacto-proveedor.model';
import {ICuentaProveedor} from '../../models/cuenta-proveedor.model';

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
  cuentas: ICuentaProveedor[];

  asyncTabs: Observable<ExampleTab[]>;

  displayedColumnsCuentas = ['cuenta', 'banco', 'numeroDeCuenta', 'nombreCuenta'];
  displayedColumnsContactos = ['nombre', 'cargo', 'telefono', 'producto'];
  dataSourceContactos = new MatTableDataSource<IContactoProveedor>(null);
  dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(null);

  tags = new Map([]);

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
      this.proveedor = proveedor;
      this.contactos = this.proveedor.contactoProveedors;
      this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.contactos);
      this.cuentas = this.proveedor.cuentaProveedors;
      this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);

      if (this.contactos !== undefined) {
        for (let i = 0; i < this.contactos.length; i++) {
          this.tags.set(i, this.contactos[i].producto === undefined ? [] : this.contactos[i].producto.split(','));
        }
      }
    });
  }

  previousState() {
    window.history.back();
  }

}
