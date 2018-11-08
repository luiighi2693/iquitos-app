import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizacion } from 'app/shared/model/amortizacion.model';
import { AmortizacionService } from './amortizacion.service';

@Component({
    selector: 'jhi-amortizacion-delete-dialog',
    templateUrl: './amortizacion-delete-dialog.component.html'
})
export class AmortizacionDeleteDialogComponent {
    amortizacion: IAmortizacion;

    constructor(
        private amortizacionService: AmortizacionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.amortizacionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'amortizacionListModification',
                content: 'Deleted an amortizacion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-amortizacion-delete-popup',
    template: ''
})
export class AmortizacionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ amortizacion }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AmortizacionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.amortizacion = amortizacion;
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
