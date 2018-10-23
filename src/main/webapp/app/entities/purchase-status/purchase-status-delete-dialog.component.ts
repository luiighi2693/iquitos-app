import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseStatus } from 'app/shared/model/purchase-status.model';
import { PurchaseStatusService } from './purchase-status.service';

@Component({
    selector: 'jhi-purchase-status-delete-dialog',
    templateUrl: './purchase-status-delete-dialog.component.html'
})
export class PurchaseStatusDeleteDialogComponent {
    purchaseStatus: IPurchaseStatus;

    constructor(
        private purchaseStatusService: PurchaseStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseStatusListModification',
                content: 'Deleted an purchaseStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-status-delete-popup',
    template: ''
})
export class PurchaseStatusDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseStatus = purchaseStatus;
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
