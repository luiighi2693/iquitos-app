import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';
import { UsuarioExternoService } from './usuario-externo.service';

@Component({
    selector: 'jhi-usuario-externo-delete-dialog',
    templateUrl: './usuario-externo-delete-dialog.component.html'
})
export class UsuarioExternoDeleteDialogComponent {
    usuarioExterno: IUsuarioExterno;

    constructor(
        private usuarioExternoService: UsuarioExternoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.usuarioExternoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'usuarioExternoListModification',
                content: 'Deleted an usuarioExterno'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-usuario-externo-delete-popup',
    template: ''
})
export class UsuarioExternoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ usuarioExterno }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UsuarioExternoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.usuarioExterno = usuarioExterno;
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
