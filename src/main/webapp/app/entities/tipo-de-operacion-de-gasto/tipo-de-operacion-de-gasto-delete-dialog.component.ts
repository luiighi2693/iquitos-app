import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';
import { TipoDeOperacionDeGastoService } from './tipo-de-operacion-de-gasto.service';

@Component({
    selector: 'jhi-tipo-de-operacion-de-gasto-delete-dialog',
    templateUrl: './tipo-de-operacion-de-gasto-delete-dialog.component.html'
})
export class TipoDeOperacionDeGastoDeleteDialogComponent {
    tipoDeOperacionDeGasto: ITipoDeOperacionDeGasto;

    constructor(
        private tipoDeOperacionDeGastoService: TipoDeOperacionDeGastoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDeOperacionDeGastoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDeOperacionDeGastoListModification',
                content: 'Deleted an tipoDeOperacionDeGasto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-operacion-de-gasto-delete-popup',
    template: ''
})
export class TipoDeOperacionDeGastoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeOperacionDeGasto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDeOperacionDeGastoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoDeOperacionDeGasto = tipoDeOperacionDeGasto;
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
