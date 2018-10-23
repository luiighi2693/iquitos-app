import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProviderPayment } from 'app/shared/model/provider-payment.model';
import { ProviderPaymentService } from './provider-payment.service';

@Component({
    selector: 'jhi-provider-payment-delete-dialog',
    templateUrl: './provider-payment-delete-dialog.component.html'
})
export class ProviderPaymentDeleteDialogComponent {
    providerPayment: IProviderPayment;

    constructor(
        private providerPaymentService: ProviderPaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.providerPaymentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'providerPaymentListModification',
                content: 'Deleted an providerPayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-provider-payment-delete-popup',
    template: ''
})
export class ProviderPaymentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providerPayment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProviderPaymentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.providerPayment = providerPayment;
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
