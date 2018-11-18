import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';
import { PagoDeProveedorService } from './pago-de-proveedor.service';

@Component({
    selector: 'jhi-pago-de-proveedor-delete-dialog',
    templateUrl: './pago-de-proveedor-delete-dialog.component.html'
})
export class PagoDeProveedorDeleteDialogComponent {
    pagoDeProveedor: IPagoDeProveedor;

    constructor(
        private pagoDeProveedorService: PagoDeProveedorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pagoDeProveedorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pagoDeProveedorListModification',
                content: 'Deleted an pagoDeProveedor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pago-de-proveedor-delete-popup',
    template: ''
})
export class PagoDeProveedorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pagoDeProveedor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PagoDeProveedorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pagoDeProveedor = pagoDeProveedor;
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
