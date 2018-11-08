import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';
import { TipoDeOperacionDeIngresoService } from './tipo-de-operacion-de-ingreso.service';

@Component({
    selector: 'jhi-tipo-de-operacion-de-ingreso-delete-dialog',
    templateUrl: './tipo-de-operacion-de-ingreso-delete-dialog.component.html'
})
export class TipoDeOperacionDeIngresoDeleteDialogComponent {
    tipoDeOperacionDeIngreso: ITipoDeOperacionDeIngreso;

    constructor(
        private tipoDeOperacionDeIngresoService: TipoDeOperacionDeIngresoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDeOperacionDeIngresoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDeOperacionDeIngresoListModification',
                content: 'Deleted an tipoDeOperacionDeIngreso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-operacion-de-ingreso-delete-popup',
    template: ''
})
export class TipoDeOperacionDeIngresoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeOperacionDeIngreso }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDeOperacionDeIngresoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoDeOperacionDeIngreso = tipoDeOperacionDeIngreso;
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
