import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptOperationInput } from 'app/shared/model/concept-operation-input.model';
import { ConceptOperationInputService } from './concept-operation-input.service';

@Component({
    selector: 'jhi-concept-operation-input-delete-dialog',
    templateUrl: './concept-operation-input-delete-dialog.component.html'
})
export class ConceptOperationInputDeleteDialogComponent {
    conceptOperationInput: IConceptOperationInput;

    constructor(
        private conceptOperationInputService: ConceptOperationInputService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conceptOperationInputService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'conceptOperationInputListModification',
                content: 'Deleted an conceptOperationInput'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-concept-operation-input-delete-popup',
    template: ''
})
export class ConceptOperationInputDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conceptOperationInput }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConceptOperationInputDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.conceptOperationInput = conceptOperationInput;
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
