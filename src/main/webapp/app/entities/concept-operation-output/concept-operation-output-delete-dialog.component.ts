import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';
import { ConceptOperationOutputService } from './concept-operation-output.service';

@Component({
    selector: 'jhi-concept-operation-output-delete-dialog',
    templateUrl: './concept-operation-output-delete-dialog.component.html'
})
export class ConceptOperationOutputDeleteDialogComponent {
    conceptOperationOutput: IConceptOperationOutput;

    constructor(
        private conceptOperationOutputService: ConceptOperationOutputService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conceptOperationOutputService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'conceptOperationOutputListModification',
                content: 'Deleted an conceptOperationOutput'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-concept-operation-output-delete-popup',
    template: ''
})
export class ConceptOperationOutputDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conceptOperationOutput }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConceptOperationOutputDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.conceptOperationOutput = conceptOperationOutput;
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
