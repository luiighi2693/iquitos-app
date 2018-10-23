import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISellHasProduct } from 'app/shared/model/sell-has-product.model';
import { SellHasProductService } from './sell-has-product.service';

@Component({
    selector: 'jhi-sell-has-product-delete-dialog',
    templateUrl: './sell-has-product-delete-dialog.component.html'
})
export class SellHasProductDeleteDialogComponent {
    sellHasProduct: ISellHasProduct;

    constructor(
        private sellHasProductService: SellHasProductService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sellHasProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sellHasProductListModification',
                content: 'Deleted an sellHasProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sell-has-product-delete-popup',
    template: ''
})
export class SellHasProductDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sellHasProduct }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SellHasProductDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sellHasProduct = sellHasProduct;
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
