import {Component, ElementRef, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from "ng2-validation";
import {JhiDataUtils} from 'ng-jhipster';
import {EmployeeRole, IEmpleado, Sex} from "../../models/empleado.model";
import {EmpleadoService} from "./empleado.service";
import Util from "../../shared/util/util";

@Component({
  selector: 'app-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.component.scss']
})
export class EmployeeUpdateComponent implements OnInit {

  entity: IEmpleado;
  isSaving: boolean;

  editing = {};
  rows = [];

  itemsPerPage = 500;
  page: 0;
  predicate = 'id';
  reverse = true;
  totalItems: number;

  sexos: Sex[] = [
    Sex.FEMENINO,
    Sex.MASCULINO
  ];

  roles: EmployeeRole[] = [
    EmployeeRole.VENDEDOR
  ];

  public form: FormGroup;

  constructor(private dataUtils: JhiDataUtils,
              private service: EmpleadoService,
              private activatedRoute: ActivatedRoute,
              private elementRef: ElementRef,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ entity }) => {
      this.entity = entity;

      this.form = this.fb.group({
        fnombre: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ],
        fapellido: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ],
        fdni: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(8)
          ])
        ],
        ftelefono: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
          ])
        ],
        ffechaNacimiento: [
          null,
          Validators.compose([CustomValidators.date])
        ],
        femail: [
          null,
          Validators.compose([CustomValidators.email])
        ],
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
      console.log(this.entity.toString());
      this.isSaving = true;
      if (this.entity.id !== undefined) {
        this.subscribeToSaveResponse(this.service.update(this.entity));
      } else {
        this.subscribeToSaveResponse(this.service.create(this.entity));
      }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>) {
    result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(res) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private updateEntity() {
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.entity, this.elementRef, field, fieldContentType, idInput);
  }

  checkNumbersOnly(event: any): boolean {
    return Util.checkNumbersOnly(event);
  }

  checkCharactersOnly(event: any): boolean {
    return Util.checkCharactersOnly(event);
  }

  checkCharactersAndNumbersOnly(event: any): boolean {
    return Util.checkCharactersAndNumbersOnly(event);
  }

  checkNumbersDecimalOnly(event: any): boolean {
    return Util.checkNumbersDecimalOnly(event);
  }
}
