import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICredito } from 'app/shared/model/credito.model';
import { CreditoService } from './credito.service';

@Component({
    selector: 'jhi-credito-delete-dialog',
    templateUrl: './credito-delete-dialog.component.html'
})
export class CreditoDeleteDialogComponent {
    credito: ICredito;

    constructor(private creditoService: CreditoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditoListModification',
                content: 'Deleted an credito'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credito-delete-popup',
    template: ''
})
export class CreditoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ credito }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.credito = credito;
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
