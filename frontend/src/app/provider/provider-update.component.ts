import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { IProveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {ContactoProveedor, IContactoProveedor} from '../models/contacto-proveedor.model';
import {MatChipInputEvent, MatTableDataSource} from '@angular/material';
import { AccountTypeProvider, CuentaProveedor, ICuentaProveedor } from '../models/cuenta-proveedor.model';
import {CuentaProveedorService} from './cuenta-proveedor.service';
import {ContactoProveedorService} from './contacto-proveedor.service';
import {IProductosRelacionadosTags} from '../models/productos-relacionados-tags.model';
import {ProductosRelacionadosTagsService} from './productos-relacionados-tags.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {map, startWith} from 'rxjs/operators';

export interface Tab {
  label: string;
}

export interface TipoCuentaProveedor {
  value: AccountTypeProvider;
  viewValue: string;
}

@Component({
  selector: 'app-provider-update',
  templateUrl: './provider-update.component.html',
  styleUrls: ['./provider-update.component.scss']
})
export class ProviderUpdateComponent implements OnInit {

  proveedor: IProveedor;
  contactos: IContactoProveedor[];
  cuentas: ICuentaProveedor[];

  asyncTabs: Observable<Tab[]>;
  isSaving: boolean;

  displayedColumnsCuentas = ['cuenta', 'banco', 'numeroDeCuenta', 'nombreCuenta', 'quitar'];
  displayedColumnsContactos = ['nombre', 'cargo', 'telefono', 'producto', 'quitar'];
  dataSourceContactos = new MatTableDataSource<IContactoProveedor>(null);
  dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(null);

  tiposDeCuenta: TipoCuentaProveedor[] = [
    { value: AccountTypeProvider.CUENTA_CORRIENTE, viewValue: 'Cuenta Corriente'},
    { value: AccountTypeProvider.CUENTA_RECAUDADORA, viewValue: 'Cuenta Recaudadora'}
  ];

  selectedTipoDeCuenta = AccountTypeProvider.CUENTA_CORRIENTE;

  editing = {};
  rows = [];

  itemsPerPage = 500;
  page: 0;
  predicate = 'id';
  reverse = true;
  totalItems: number;
  // tags: IProductosRelacionadosTags[];
  valueInputTag = '';

  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];
  myControl = new FormControl();
  filteredOptions: Observable<IProductosRelacionadosTags[]>;
  options1: IProductosRelacionadosTags[];
  selectable = true;
  removable = true;
  addOnBlur = true;

  tags = new Map([]);

  @ViewChild('tagInput') tagInput: ElementRef;

  public form: FormGroup;

  hasError = false;
  noError = true;

  constructor(private proveedorService: ProveedorService,
              private cuentaProveedorService: CuentaProveedorService,
              private contactoProveedorService: ContactoProveedorService,
              private productosRelacionadosTagsService: ProductosRelacionadosTagsService,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder) {
    this.asyncTabs = Observable.create((observer: Observer<Tab[]>) => {
      setTimeout(() => {
        observer.next([
          { label: 'Cuentas Asociadas a Proveedor' },
          { label: 'Contactos Asociadas a Proveedor' },
        ]);
      }, 500);
    });
  }

  ngOnInit() {
        this.isSaving = false;
    this.productosRelacionadosTagsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProductosRelacionadosTags[]>) => this.paginateProductosRelacionadosTags(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
      this.contactos = this.proveedor.contactoProveedors;
      this.cuentas = this.proveedor.cuentaProveedors;
      this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.contactos);
      this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);

      if (this.contactos !== undefined) {
        for (let i = 0; i < this.contactos.length; i++) {
          this.tags.set(i, this.contactos[i].producto === undefined ? [] : this.contactos[i].producto.split(','));
        }
      }

      this.form = this.fb.group({
        frazonSocial: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ],
        fdireccion: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ]
      });
    });
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateProductosRelacionadosTags(data: IProductosRelacionadosTags[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.options1 = data;

    if (this.totalItems !== 0) {
      this.filteredOptions = this.myControl.valueChanges.pipe(
        startWith<string | IProductosRelacionadosTags>(''),
        map(value => (typeof value === 'string' ? value : value.nombre)),
        map(name => (name ? this._filter(name) : this.options1.slice()))
      );
    }
  }

  private _filter(nombre: string): IProductosRelacionadosTags[] {
    const filterValue = nombre.toLowerCase();

    return this.options1.filter(
      option => option.nombre.toLowerCase().indexOf(filterValue) === 0
    );
  }

  addValueTag(productoRelacionado: IProductosRelacionadosTags, i: number) {
    if ((productoRelacionado.nombre || '').trim()) {
      (<Array<string>>this.tags.get(i)).push(productoRelacionado.nombre.trim());
      // this.contactos[i].producto = this.contactos[i].producto === undefined || this.contactos[i].producto === '' ?
      // this.contactos[i].producto = this.contactos[i].producto === undefined || this.contactos[i].producto === '' ?
      //   productoRelacionado.nombre.trim() : this.contactos[i].producto + ',' + productoRelacionado.nombre.trim();
    }

    this.tagInput.nativeElement.value = '';

    // console.log(this.contactos[i].producto);
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.updateEntity();
    this.hasError = false;
    if (this.validateEntity()) {
      console.log(this.proveedor.toString());
      this.isSaving = true;
      if (this.proveedor.id !== undefined) {
        this.subscribeToSaveResponse(this.proveedorService.update(this.proveedor));
      } else {
        this.subscribeToSaveResponse(this.proveedorService.create(this.proveedor));
      }
    } else {
      this.hasError = true;
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponseFromRemoveAccount(result: Observable<HttpResponse<IProveedor>>, idToDelete: number) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccessFromRemoveAccount(idToDelete), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponseFromRemoveContact(result: Observable<HttpResponse<IProveedor>>, idToDelete: number) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccessFromRemoveContact(idToDelete), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(res) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveSuccessFromRemoveAccount(idToDelete) {
    this.isSaving = false;
    this.cuentaProveedorService.delete(idToDelete).subscribe(response => {
    });
  }

  private onSaveSuccessFromRemoveContact(idToDelete) {
    this.isSaving = false;
    this.contactoProveedorService.delete(idToDelete).subscribe(response => {
    });
  }

  private onSaveError() {
    this.isSaving = false;
  }

  addAccountProvider() {
    const cuenta = new CuentaProveedor();
    cuenta.tipoCuenta = this.selectedTipoDeCuenta;

    if (this.cuentas === undefined) {
      this.cuentas = [];
    }

    this.cuentas.push(cuenta);
    this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);
  }

  addContactProvider() {
    const contacto = new ContactoProveedor();

    if (this.contactos === undefined) {
      this.contactos = [];
    }

    this.contactos.push(contacto);
    this.tags.set(this.contactos.length - 1, []);
    this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.contactos);
  }

  saveChangesInAccountsRow(event, indexFromAccount, label) {
    if (label === 'banco') {
      this.cuentas[indexFromAccount].banco = event.target.value;
    }

    if (label === 'numeroDeCuenta') {
      this.cuentas[indexFromAccount].numeroDeCuenta = event.target.value;
    }

    if (label === 'nombreCuenta') {
      this.cuentas[indexFromAccount].nombreCuenta = event.target.value;
    }

    this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.cuentas);
  }

  saveChangesInContactsRow(event, indexFromAccount, label) {
    if (label === 'nombre') {
      this.contactos[indexFromAccount].nombre = event.target.value;
    }

    if (label === 'cargo') {
      this.contactos[indexFromAccount].cargo = event.target.value;
    }

    if (label === 'telefono') {
      this.contactos[indexFromAccount].telefono = event.target.value;
    }

    // if (label === 'producto') {
    //   this.contactos[indexFromAccount].producto = event.target.value;
    // }

    // if (label === 'producto') {
    //   this.contactos[indexFromAccount].producto = this.tags.map(tag => tag.nombre).join(',');
    //   console.log(this.contactos[indexFromAccount].producto);
    // }

    this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.contactos);
  }

  deleteAccount(i, row) {
    // let index = null;

    this.dataSourceCuentas.data.splice(i, 1);
    this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.dataSourceCuentas.data);
    this.cuentas = this.dataSourceCuentas.data;

    // if (row.id !== undefined) {
    //   index = this.dataSourceCuentas.data.map(x => x.id).indexOf(row.id);
    //   if (index !== -1) {
    //     this.dataSourceCuentas.data.splice(index, 1);
    //     this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.dataSourceCuentas.data);
    //
    //     this.cuentas = this.dataSourceCuentas.data;
    //
    //     this.updateEntity();
    //     this.subscribeToSaveResponseFromRemoveAccount(this.proveedorService.update(this.proveedor), row.id);
    //   }
    // } else {
    //   this.dataSourceCuentas.data.splice(index, 1);
    //   this.dataSourceCuentas = new MatTableDataSource<ICuentaProveedor>(this.dataSourceCuentas.data);
    //   this.cuentas = this.dataSourceCuentas.data;
    // }
  }

  private updateEntity() {
    for (let i = 0; i < this.contactos.length; i++) {
      this.contactos[i].producto = (<Array<string>>this.tags.get(i)).join(',');
      if (this.contactos[i].producto === '') {
        this.contactos[i].producto = null;
      }
    }
    this.proveedor.contactoProveedors = this.contactos;
    this.proveedor.cuentaProveedors = this.cuentas;
  }

  deleteContact(i, row) {
    // let index = null;
    this.dataSourceContactos.data.splice(i, 1);
    this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.dataSourceContactos.data);
    this.contactos = this.dataSourceContactos.data;

    // if (row.id !== undefined) {
    //   index = this.dataSourceContactos.data.map(x => x.id).indexOf(row.id);
    //   if (index !== -1) {
    //     this.dataSourceContactos.data.splice(index, 1);
    //     this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.dataSourceContactos.data);
    //
    //     this.contactos = this.dataSourceContactos.data;
    //
    //     this.updateEntity();
    //     this.subscribeToSaveResponseFromRemoveContact(this.proveedorService.update(this.proveedor), row.id);
    //   }
    // } else {
    //   this.dataSourceContactos.data.splice(index, 1);
    //   this.dataSourceContactos = new MatTableDataSource<IContactoProveedor>(this.dataSourceContactos.data);
    //   this.contactos = this.dataSourceContactos.data;
    // }
  }

  displayFn(productoRelacionado?: IProductosRelacionadosTags): string | undefined {
    return productoRelacionado ? productoRelacionado.nombre : undefined;
  }

  addTag(event: MatChipInputEvent): void {
    const input = event.input;
    if (input) {
      input.value = '';
    }
  }

  removeTag(productoRelacionado: string, i: number): void {
    const index = (<Array<string>>this.tags.get(i)).indexOf(productoRelacionado);
    if (index >= 0) {
      (<Array<string>>this.tags.get(i)).splice(index, 1);
    }
  }

  private validateEntity(): boolean {
    this.noError = true;

    for (let i = 0; i < this.proveedor.contactoProveedors.length; i++) {
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.contactoProveedors[i].nombre);
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.contactoProveedors[i].cargo);
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.contactoProveedors[i].telefono);
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.contactoProveedors[i].producto);
    }

    for (let i = 0; i < this.proveedor.cuentaProveedors.length; i++) {
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.cuentaProveedors[i].nombreCuenta);
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.cuentaProveedors[i].banco);
      this.noError = !this.noError ? this.noError : this.validateField(this.proveedor.cuentaProveedors[i].numeroDeCuenta);
    }

    return this.noError;
  }

  validateField(field: any) {
    if (field === null || field === undefined) {
      return false;
    } else {
      return field.toString().length !== 0;
    }
  }
}
