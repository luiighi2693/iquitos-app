import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';
import { UnidadDeMedidaService } from './unidad-de-medida.service';

@Component({
    selector: 'jhi-unidad-de-medida-delete-dialog',
    templateUrl: './unidad-de-medida-delete-dialog.component.html'
})
export class UnidadDeMedidaDeleteDialogComponent {
    unidadDeMedida: IUnidadDeMedida;

    constructor(
        private unidadDeMedidaService: UnidadDeMedidaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.unidadDeMedidaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'unidadDeMedidaListModification',
                content: 'Deleted an unidadDeMedida'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-unidad-de-medida-delete-popup',
    template: ''
})
export class UnidadDeMedidaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unidadDeMedida }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UnidadDeMedidaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.unidadDeMedida = unidadDeMedida;
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
