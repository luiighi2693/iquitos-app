import {Component, OnInit, OnDestroy} from '@angular/core';
import {FullService} from '../../layouts/full/full.service';
import {TipoDeDocumentoService} from "./tipo-de-documento.service";
import {BaseTipoDocumento} from "./BaseTipoDocumento";

declare var require: any;

@Component({
  selector: 'app-documenttype',
  templateUrl: './documenttype.component.html',
  styleUrls: ['./documenttype.component.scss']
})
export class DocumenttypeComponent extends BaseTipoDocumento implements OnInit, OnDestroy {
  constructor(public service: TipoDeDocumentoService, public fullService: FullService) {
    super(
      service,
      fullService,
      ['nombre', 'cantidadDigitos'],
      null, null, null, require('../menu.json'), 'DOCUMENTOS');
  }

  ngOnInit() {

    this.loadAll();
  }

  ngOnDestroy() {
  }
}
