import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVariante } from 'app/shared/model/variante.model';
import { VarianteService } from './variante.service';

@Component({
    selector: 'jhi-variante-delete-dialog',
    templateUrl: './variante-delete-dialog.component.html'
})
export class VarianteDeleteDialogComponent {
    variante: IVariante;

    constructor(private varianteService: VarianteService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.varianteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'varianteListModification',
                content: 'Deleted an variante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-variante-delete-popup',
    template: ''
})
export class VarianteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ variante }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VarianteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.variante = variante;
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
