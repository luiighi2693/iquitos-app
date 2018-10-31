import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';
import { TipoDeDocumentoDeVentaService } from './tipo-de-documento-de-venta.service';

@Component({
    selector: 'jhi-tipo-de-documento-de-venta-delete-dialog',
    templateUrl: './tipo-de-documento-de-venta-delete-dialog.component.html'
})
export class TipoDeDocumentoDeVentaDeleteDialogComponent {
    tipoDeDocumentoDeVenta: ITipoDeDocumentoDeVenta;

    constructor(
        private tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDeDocumentoDeVentaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDeDocumentoDeVentaListModification',
                content: 'Deleted an tipoDeDocumentoDeVenta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-documento-de-venta-delete-popup',
    template: ''
})
export class TipoDeDocumentoDeVentaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeDocumentoDeVenta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDeDocumentoDeVentaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
