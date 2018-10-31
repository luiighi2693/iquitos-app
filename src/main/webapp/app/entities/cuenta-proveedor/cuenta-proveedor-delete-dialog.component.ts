import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';
import { CuentaProveedorService } from './cuenta-proveedor.service';

@Component({
    selector: 'jhi-cuenta-proveedor-delete-dialog',
    templateUrl: './cuenta-proveedor-delete-dialog.component.html'
})
export class CuentaProveedorDeleteDialogComponent {
    cuentaProveedor: ICuentaProveedor;

    constructor(
        private cuentaProveedorService: CuentaProveedorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cuentaProveedorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cuentaProveedorListModification',
                content: 'Deleted an cuentaProveedor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cuenta-proveedor-delete-popup',
    template: ''
})
export class CuentaProveedorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cuentaProveedor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CuentaProveedorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.cuentaProveedor = cuentaProveedor;
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
