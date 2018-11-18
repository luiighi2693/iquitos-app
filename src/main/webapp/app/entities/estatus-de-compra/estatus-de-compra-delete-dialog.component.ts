import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';
import { EstatusDeCompraService } from './estatus-de-compra.service';

@Component({
    selector: 'jhi-estatus-de-compra-delete-dialog',
    templateUrl: './estatus-de-compra-delete-dialog.component.html'
})
export class EstatusDeCompraDeleteDialogComponent {
    estatusDeCompra: IEstatusDeCompra;

    constructor(
        private estatusDeCompraService: EstatusDeCompraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estatusDeCompraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estatusDeCompraListModification',
                content: 'Deleted an estatusDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estatus-de-compra-delete-popup',
    template: ''
})
export class EstatusDeCompraDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estatusDeCompra }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstatusDeCompraDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.estatusDeCompra = estatusDeCompra;
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
