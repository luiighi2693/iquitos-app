import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from './tipo-de-pago.service';

@Component({
    selector: 'jhi-tipo-de-pago-delete-dialog',
    templateUrl: './tipo-de-pago-delete-dialog.component.html'
})
export class TipoDePagoDeleteDialogComponent {
    tipoDePago: ITipoDePago;

    constructor(private tipoDePagoService: TipoDePagoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDePagoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDePagoListModification',
                content: 'Deleted an tipoDePago'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-pago-delete-popup',
    template: ''
})
export class TipoDePagoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDePago }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDePagoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tipoDePago = tipoDePago;
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
