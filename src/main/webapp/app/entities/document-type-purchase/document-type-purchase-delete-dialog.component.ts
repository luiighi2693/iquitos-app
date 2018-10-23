import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';
import { DocumentTypePurchaseService } from './document-type-purchase.service';

@Component({
    selector: 'jhi-document-type-purchase-delete-dialog',
    templateUrl: './document-type-purchase-delete-dialog.component.html'
})
export class DocumentTypePurchaseDeleteDialogComponent {
    documentTypePurchase: IDocumentTypePurchase;

    constructor(
        private documentTypePurchaseService: DocumentTypePurchaseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentTypePurchaseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentTypePurchaseListModification',
                content: 'Deleted an documentTypePurchase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-type-purchase-delete-popup',
    template: ''
})
export class DocumentTypePurchaseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTypePurchase }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DocumentTypePurchaseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.documentTypePurchase = documentTypePurchase;
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
