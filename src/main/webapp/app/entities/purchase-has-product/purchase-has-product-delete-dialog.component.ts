import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';
import { PurchaseHasProductService } from './purchase-has-product.service';

@Component({
    selector: 'jhi-purchase-has-product-delete-dialog',
    templateUrl: './purchase-has-product-delete-dialog.component.html'
})
export class PurchaseHasProductDeleteDialogComponent {
    purchaseHasProduct: IPurchaseHasProduct;

    constructor(
        private purchaseHasProductService: PurchaseHasProductService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseHasProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseHasProductListModification',
                content: 'Deleted an purchaseHasProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-has-product-delete-popup',
    template: ''
})
export class PurchaseHasProductDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseHasProduct }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseHasProductDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseHasProduct = purchaseHasProduct;
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
