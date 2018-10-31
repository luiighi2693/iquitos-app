import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';
import { TipoDeDocumentoDeCompraService } from './tipo-de-documento-de-compra.service';

@Component({
    selector: 'jhi-tipo-de-documento-de-compra-delete-dialog',
    templateUrl: './tipo-de-documento-de-compra-delete-dialog.component.html'
})
export class TipoDeDocumentoDeCompraDeleteDialogComponent {
    tipoDeDocumentoDeCompra: ITipoDeDocumentoDeCompra;

    constructor(
        private tipoDeDocumentoDeCompraService: TipoDeDocumentoDeCompraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDeDocumentoDeCompraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDeDocumentoDeCompraListModification',
                content: 'Deleted an tipoDeDocumentoDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-documento-de-compra-delete-popup',
    template: ''
})
export class TipoDeDocumentoDeCompraDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeDocumentoDeCompra }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDeDocumentoDeCompraDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
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
