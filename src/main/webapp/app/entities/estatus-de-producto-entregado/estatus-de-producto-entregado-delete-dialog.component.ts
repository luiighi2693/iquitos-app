import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';
import { EstatusDeProductoEntregadoService } from './estatus-de-producto-entregado.service';

@Component({
    selector: 'jhi-estatus-de-producto-entregado-delete-dialog',
    templateUrl: './estatus-de-producto-entregado-delete-dialog.component.html'
})
export class EstatusDeProductoEntregadoDeleteDialogComponent {
    estatusDeProductoEntregado: IEstatusDeProductoEntregado;

    constructor(
        private estatusDeProductoEntregadoService: EstatusDeProductoEntregadoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estatusDeProductoEntregadoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estatusDeProductoEntregadoListModification',
                content: 'Deleted an estatusDeProductoEntregado'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estatus-de-producto-entregado-delete-popup',
    template: ''
})
export class EstatusDeProductoEntregadoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estatusDeProductoEntregado }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstatusDeProductoEntregadoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.estatusDeProductoEntregado = estatusDeProductoEntregado;
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
