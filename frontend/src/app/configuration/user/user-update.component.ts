import {Component, ElementRef, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {JhiDataUtils} from 'ng-jhipster';
import {IUsuarioExterno, UserType} from "../../models/usuario-externo.model";
import {UsuarioExternoService} from "./usuario-externo.service";
import {CustomValidators} from "ng2-validation";
import {ICuentaProveedor} from "../../../../../src/main/webapp/app/shared/model/cuenta-proveedor.model";
import {ProveedorService} from "../../contact/provider/proveedor.service";
import {IProveedor} from "../../models/proveedor.model";
import {IEmpleado} from "../../models/empleado.model";
import {ICliente} from "../../models/cliente.model";
import {ClienteService} from "../../contact/client/cliente.service";
import {EmpleadoService} from "../../contact/employee/empleado.service";
import {IProducto} from "../../models/producto.model";

const password = new FormControl('', Validators.required);
const confirmPassword = new FormControl('', CustomValidators.equalTo(password));

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit {

  entity: IUsuarioExterno;
  isSaving: boolean;

  editing = {};
  rows = [];

  itemsPerPage = 500;
  page: 0;
  predicate = 'id';
  reverse = true;
  totalItems: number;
  confirmPassword: number;

  tipos: UserType[] = [
    UserType.ADMINISTRADOR,
    UserType.CLIENTE,
    UserType.EMPLEADO,
    UserType.PROVEEDOR,
  ];

  proveedor: IProveedor = undefined;
  cliente: ICliente= undefined;
  empleado: IEmpleado = undefined;

  public form: FormGroup;

  entityFilteredSelected = null;
  entityFilteredSelectedInput: string;
  entitiesFiltered = [];
  entitySelectFlag = false;

  hasError = false;
  hasErrorRequest = false;
  noError = true;
  errorMessage = '';

  dniFiltered = undefined;

  constructor(private dataUtils: JhiDataUtils,
              private service:UsuarioExternoService,
              private providerService:ProveedorService,
              private clientService:ClienteService,
              private employeeService:EmpleadoService,
              private activatedRoute: ActivatedRoute,
              private elementRef: ElementRef,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;
      if (this.entity.id !== undefined) {
        this.confirmPassword = this.entity.pin;

        this.findEntity(this.entity.userType, this.entity.idEntity);
      }

      this.form = this.fb.group({
        fdni: [
          null,
          Validators.compose([
            Validators.required,
          ])
        ],
        fentitySelected: [
          null,
          Validators.compose([
            // Validators.required,
            Validators.maxLength(150)
          ])
        ],
        password: password,
        confirmPassword: confirmPassword
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

  previousState() {
    window.history.back();
  }

  save() {
      this.updateEntity();
    this.hasError = false;
    if (this.validateEntity()) {
      console.log(this.entity.toString());
      this.isSaving = true;
      if (this.entity.id !== undefined) {
        this.subscribeToSaveResponse(this.service.update(this.entity));
      } else {
        this.subscribeToSaveResponse(this.service.create(this.entity));
      }
    } else {
      this.hasError = true;
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioExterno>>) {
    result.subscribe((res: HttpResponse<IUsuarioExterno>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(res) {
    this.saveEntity(this.entity.userType, res);
    // this.isSaving = false;
    // this.previousState();
  }

  public saveEntity(entityType, entity) {
    switch (entityType) {
      case 'PROVEEDOR': {
        this.proveedor.usuarioId = entity.body.id;
        this.subscribeToSaveProveedorResponse(this.providerService.update(this.proveedor));
        break;
      }

      case 'CLIENTE': {
        this.cliente.usuarioId = entity.body.id;
        this.subscribeToSaveClienteResponse(this.clientService.update(this.cliente));
        break;
      }

      case 'EMPLEADO': {
        this.empleado.usuarioId = entity.body.id;
        this.subscribeToSaveEmpleadoResponse(this.employeeService.update(this.empleado));
        break;
      }
      default: {
        this.onSaveSuccessSubEntity(null);
        break;
      }
    }
  }

  private subscribeToSaveProveedorResponse(result: Observable<HttpResponse<IProveedor>>) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccessSubEntity(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveClienteResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccessSubEntity(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveEmpleadoResponse(result: Observable<HttpResponse<IEmpleado>>) {
    result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccessSubEntity(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccessSubEntity(res) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private updateEntity() {
    this.entity.role = this.entity.userType;
    this.entity.idEntity = this.getEntityId(this.entity.userType)
  }

  public findEntity(entity, id) {
    switch (entity) {
      case 'PROVEEDOR': {
        this.providerService.find(id).subscribe(
          (res: HttpResponse<IProveedor>) => {
            this.setEntityFilter(res.body)
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      case 'CLIENTE': {
        this.clientService.find(id).subscribe(
          (res: HttpResponse<ICliente>) => {
            this.setEntityFilter(res.body)
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      case 'EMPLEADO': {
        this.employeeService.find(id).subscribe(
          (res: HttpResponse<IEmpleado>) => {
            this.setEntityFilter(res.body)
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      default: {
        break;
      }

    }
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
  }

  setEntityFilter(option) {
    if (option.nombre) {
      this.entityFilteredSelectedInput = option.nombre;
    } else {
      this.entityFilteredSelectedInput = option.razonSocial;
    }
    this.entitySelectFlag = true;

    this.setEntity(this.entity.userType, option);
    this.entityFilteredSelected = option;
  }

  clearEntitySelected() {
    this.entitySelectFlag = false;
    this.entityFilteredSelected = null;
    this.entityFilteredSelectedInput = undefined;

    this.proveedor = null;
    this.cliente = null;
    this.empleado = null;
  }

  filterEntity() {
    if (!this.entitySelectFlag) {
      if (this.entityFilteredSelectedInput.length > 2) {
        this.filterEntityByQuery(this.entity.userType, this.entityFilteredSelectedInput);
      }
    } else {
      this.entityFilteredSelectedInput =  this.getFilteredEntityName(this.entity.userType);
    }
  }

  public filterEntityByQuery(entity, query) {
    switch (entity) {
      case 'PROVEEDOR': {
        this.providerService.query(query).subscribe(
          (res: HttpResponse<IProveedor[]>) => {
            if (res.body.length > 0) {
              this.entitiesFiltered = res.body;
            }
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      case 'CLIENTE': {
        this.clientService.query(query).subscribe(
          (res: HttpResponse<ICliente[]>) => {
            if (res.body.length > 0) {
              this.entitiesFiltered = res.body;
            }
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      case 'EMPLEADO': {
        this.employeeService.query(query).subscribe(
          (res: HttpResponse<IEmpleado[]>) => {
            if (res.body.length > 0) {
              this.entitiesFiltered = res.body;
            }
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
        break;
      }

      default: {
        break;
      }

    }
  }

  public setEntity(entityType, entity) {
    switch (entityType) {
      case 'PROVEEDOR': {
        this.proveedor = entity;
        break;
      }

      case 'CLIENTE': {
        this.cliente = entity;
        break;
      }

      case 'EMPLEADO': {
        this.empleado = entity;
        break;
      }

      default: {
        break;
      }

    }
  }

  setUserType() {
    this.proveedor = undefined;
    this.cliente = undefined;
    this.empleado = undefined;
    this.clearEntitySelected()
    this.hasError = false;
  }

  private getFilteredEntityName(userType) {
    switch (userType) {
      case 'PROVEEDOR': {
        return this.proveedor.razonSocial;
      }

      case 'CLIENTE': {
        return this.cliente.nombre;
      }

      case 'EMPLEADO': {
        return this.empleado.nombre;
      }
    }

    return "";
  }

  private getEntityId(userType) {
    switch (userType) {
      case 'PROVEEDOR': {
        return this.proveedor === undefined || this.proveedor === null ? null : this.proveedor.id;
      }

      case 'CLIENTE': {
        return this.cliente === undefined || this.cliente === null ? null : this.cliente.id;
      }

      case 'EMPLEADO': {
        return this.empleado === undefined || this.empleado === null ? null : this.empleado.id;
      }
    }

    return null;
  }

  private getEntity(userType) {
    switch (userType) {
      case 'PROVEEDOR': {
        return this.proveedor;
      }

      case 'CLIENTE': {
        return this.cliente;
      }

      case 'EMPLEADO': {
        return this.empleado;
      }
    }

    return null;
  }

  private validateEntity(): boolean {
    this.noError = true;

    if (this.entity.userType === undefined) {
      this.errorMessage = 'Tipo de Usuario requerido';
      this.hasError = true;
      return false;
    }

    if ( (this.entity.idEntity === undefined || this.entity.idEntity === null) && this.entity.userType !== 'ADMINISTRADOR') {
      this.errorMessage = 'Busqueda de usuario requerida';
      this.hasError = true;
      return false;
    }

    if (this.dniFiltered) {
      this.errorMessage = 'Dni ingresado ya existe';
      this.hasError = true;
      return false;
    }

    if (this.entity.userType !== 'ADMINISTRADOR') {
      if (this.getEntity(this.entity.userType).usuarioId !== null) {
        if (this.entity.id !== undefined) {
          if (this.entity.id !== this.getEntity(this.entity.userType).usuarioId) {
            this.errorMessage = 'Entidad ya tiene un usuario asociado';
            return false;
          }
        } else {
          this.errorMessage = 'Entidad ya tiene un usuario asociado';
          return false;
        }
      }
    }

    return this.noError;
  }

  searchDni() {
    if (this.entity.dni.toString().length > 6) {
      this.service.findByDni(this.entity.dni).subscribe(
        (res: HttpResponse<IUsuarioExterno>) => {
            this.dniFiltered = res.body;
          this.errorMessage = 'Dni ingresado ya existe';
          this.hasError = true;
        },
        (res: HttpErrorResponse) => this.onErrorSearch(res.message)
      );
    }
  }

  private onErrorSearch(message: string) {
    this.dniFiltered = null;
    this.hasError = false;
  }
}
