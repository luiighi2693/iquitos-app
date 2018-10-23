import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';
import { ProductsDeliveredStatusService } from './products-delivered-status.service';

@Component({
    selector: 'jhi-products-delivered-status-delete-dialog',
    templateUrl: './products-delivered-status-delete-dialog.component.html'
})
export class ProductsDeliveredStatusDeleteDialogComponent {
    productsDeliveredStatus: IProductsDeliveredStatus;

    constructor(
        private productsDeliveredStatusService: ProductsDeliveredStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productsDeliveredStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productsDeliveredStatusListModification',
                content: 'Deleted an productsDeliveredStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-products-delivered-status-delete-popup',
    template: ''
})
export class ProductsDeliveredStatusDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productsDeliveredStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductsDeliveredStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productsDeliveredStatus = productsDeliveredStatus;
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
