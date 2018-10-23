import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProviderAccount } from 'app/shared/model/provider-account.model';
import { ProviderAccountService } from './provider-account.service';

@Component({
    selector: 'jhi-provider-account-delete-dialog',
    templateUrl: './provider-account-delete-dialog.component.html'
})
export class ProviderAccountDeleteDialogComponent {
    providerAccount: IProviderAccount;

    constructor(
        private providerAccountService: ProviderAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.providerAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'providerAccountListModification',
                content: 'Deleted an providerAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-provider-account-delete-popup',
    template: ''
})
export class ProviderAccountDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providerAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProviderAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.providerAccount = providerAccount;
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
