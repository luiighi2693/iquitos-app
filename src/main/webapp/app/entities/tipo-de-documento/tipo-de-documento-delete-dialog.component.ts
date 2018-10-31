import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';
import { TipoDeDocumentoService } from './tipo-de-documento.service';

@Component({
    selector: 'jhi-tipo-de-documento-delete-dialog',
    templateUrl: './tipo-de-documento-delete-dialog.component.html'
})
export class TipoDeDocumentoDeleteDialogComponent {
    tipoDeDocumento: ITipoDeDocumento;

    constructor(
        private tipoDeDocumentoService: TipoDeDocumentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDeDocumentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDeDocumentoListModification',
                content: 'Deleted an tipoDeDocumento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-documento-delete-popup',
    template: ''
})
export class TipoDeDocumentoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeDocumento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDeDocumentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoDeDocumento = tipoDeDocumento;
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
