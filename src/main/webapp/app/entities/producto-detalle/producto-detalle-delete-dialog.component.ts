import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoDetalle } from 'app/shared/model/producto-detalle.model';
import { ProductoDetalleService } from './producto-detalle.service';

@Component({
    selector: 'jhi-producto-detalle-delete-dialog',
    templateUrl: './producto-detalle-delete-dialog.component.html'
})
export class ProductoDetalleDeleteDialogComponent {
    productoDetalle: IProductoDetalle;

    constructor(
        private productoDetalleService: ProductoDetalleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productoDetalleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productoDetalleListModification',
                content: 'Deleted an productoDetalle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-producto-detalle-delete-popup',
    template: ''
})
export class ProductoDetalleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productoDetalle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductoDetalleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productoDetalle = productoDetalle;
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
