import {Component, ElementRef, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from "ng2-validation";
import {JhiDataUtils} from 'ng-jhipster';
import {CivilStatus, ClientType, ICliente, Sex} from "../../models/cliente.model";
import {ClienteService} from "./cliente.service";
import {ITipoDeDocumento} from "../../models/tipo-de-documento.model";
import {TipoDeDocumentoService} from "../../configuration/documenttype/tipo-de-documento.service";
import Util from "../../shared/util/util";
import {IParametroSistema} from "../../models/parametro-sistema.model";

@Component({
  selector: 'app-client-update',
  templateUrl: './client-update.component.html',
  styleUrls: ['./client-update.component.scss']
})
export class ClientUpdateComponent implements OnInit {

  entity: ICliente;
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

  estatusCiviles: CivilStatus[] = [
    CivilStatus.CASADO,
    CivilStatus.SOLTERO
  ];

  tiposDeCliente: ClientType[] = [
    ClientType.JURIDICO,
    ClientType.NATURAL
  ];

  tiposDeDocumento: ITipoDeDocumento[];

  public form: FormGroup;

  clientRepeated = false;

  constructor(private dataUtils: JhiDataUtils,
              private service: ClienteService,
              private tiposDeDocumentoService: TipoDeDocumentoService,
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
        fdireccion: [
          null,
          Validators.compose([
            Validators.required
          ])
        ],
        fcodigo: [
          null,
          Validators.compose([
            Validators.required,
            Validators.maxLength(150)
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

    this.tiposDeDocumentoService.query().subscribe(
      (res: HttpResponse<ITipoDeDocumento[]>) => {
        this.tiposDeDocumento = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private onError(errorMessage: string) {
    console.log(errorMessage);
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
    if (!this.clientRepeated){
      this.updateEntity();
      console.log(this.entity.toString());
      this.isSaving = true;
      if (this.entity.id !== undefined) {
        this.subscribeToSaveResponse(this.service.update(this.entity));
      } else {
        this.subscribeToSaveResponse(this.service.create(this.entity));
      }
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
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

  validateClient() {
    console.log(this.entity.codigo);
    this.clientRepeated = false;

    if(this.entity.codigo.length > 2 && this.entity.id === undefined) {
      this.service.search({query:this.entity.codigo}).subscribe(
        (res: HttpResponse<ICliente[]>) => {
          console.log(res.body);
          res.body.forEach(client => {
            if (client.codigo === this.entity.codigo) {
              this.clientRepeated = true;
            }
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }
}
