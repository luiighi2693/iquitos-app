import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParametroSistema } from 'app/shared/model/parametro-sistema.model';
import { ParametroSistemaService } from './parametro-sistema.service';

@Component({
    selector: 'jhi-parametro-sistema-delete-dialog',
    templateUrl: './parametro-sistema-delete-dialog.component.html'
})
export class ParametroSistemaDeleteDialogComponent {
    parametroSistema: IParametroSistema;

    constructor(
        private parametroSistemaService: ParametroSistemaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametroSistemaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'parametroSistemaListModification',
                content: 'Deleted an parametroSistema'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametro-sistema-delete-popup',
    template: ''
})
export class ParametroSistemaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parametroSistema }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParametroSistemaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.parametroSistema = parametroSistema;
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
