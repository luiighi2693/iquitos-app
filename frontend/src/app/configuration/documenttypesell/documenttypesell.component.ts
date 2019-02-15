import {Component, OnInit, OnDestroy} from '@angular/core';
import {FullService} from '../../layouts/full/full.service';
import {TipoDeDocumentoDeVentaService} from "./tipo-de-documento-de-venta.service";
import {BaseTipoDocumentoVenta} from "./BaseTipoDocumentoVenta";

declare var require: any;

@Component({
  selector: 'app-documenttypesell',
  templateUrl: './documenttypesell.component.html',
  styleUrls: ['./documenttypesell.component.scss']
})
export class DocumenttypesellComponent extends BaseTipoDocumentoVenta implements OnInit, OnDestroy {

  constructor(public service: TipoDeDocumentoDeVentaService, public fullService: FullService) {
    super(
      service,
      fullService,
      ['nombre', 'serie', 'formato'],
      null, null, null, require('../menu.json'), 'D. DE VENTA');
  }

  ngOnInit() {

    this.loadAll();
  }

  ngOnDestroy() {
  }

}
