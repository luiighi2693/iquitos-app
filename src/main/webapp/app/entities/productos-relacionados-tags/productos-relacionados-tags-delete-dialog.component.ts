import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';
import { ProductosRelacionadosTagsService } from './productos-relacionados-tags.service';

@Component({
    selector: 'jhi-productos-relacionados-tags-delete-dialog',
    templateUrl: './productos-relacionados-tags-delete-dialog.component.html'
})
export class ProductosRelacionadosTagsDeleteDialogComponent {
    productosRelacionadosTags: IProductosRelacionadosTags;

    constructor(
        private productosRelacionadosTagsService: ProductosRelacionadosTagsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productosRelacionadosTagsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productosRelacionadosTagsListModification',
                content: 'Deleted an productosRelacionadosTags'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-productos-relacionados-tags-delete-popup',
    template: ''
})
export class ProductosRelacionadosTagsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productosRelacionadosTags }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductosRelacionadosTagsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productosRelacionadosTags = productosRelacionadosTags;
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
