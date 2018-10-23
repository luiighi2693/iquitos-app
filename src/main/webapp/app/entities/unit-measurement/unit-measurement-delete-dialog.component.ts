import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitMeasurement } from 'app/shared/model/unit-measurement.model';
import { UnitMeasurementService } from './unit-measurement.service';

@Component({
    selector: 'jhi-unit-measurement-delete-dialog',
    templateUrl: './unit-measurement-delete-dialog.component.html'
})
export class UnitMeasurementDeleteDialogComponent {
    unitMeasurement: IUnitMeasurement;

    constructor(
        private unitMeasurementService: UnitMeasurementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.unitMeasurementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'unitMeasurementListModification',
                content: 'Deleted an unitMeasurement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-unit-measurement-delete-popup',
    template: ''
})
export class UnitMeasurementDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unitMeasurement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UnitMeasurementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.unitMeasurement = unitMeasurement;
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
