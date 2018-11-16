import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactoProveedor } from 'app/shared/model/contacto-proveedor.model';
import { ContactoProveedorService } from './contacto-proveedor.service';

@Component({
    selector: 'jhi-contacto-proveedor-delete-dialog',
    templateUrl: './contacto-proveedor-delete-dialog.component.html'
})
export class ContactoProveedorDeleteDialogComponent {
    contactoProveedor: IContactoProveedor;

    constructor(
        private contactoProveedorService: ContactoProveedorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactoProveedorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactoProveedorListModification',
                content: 'Deleted an contactoProveedor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contacto-proveedor-delete-popup',
    template: ''
})
export class ContactoProveedorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactoProveedor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactoProveedorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contactoProveedor = contactoProveedor;
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
