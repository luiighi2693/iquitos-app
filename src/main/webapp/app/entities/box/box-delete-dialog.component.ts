import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBox } from 'app/shared/model/box.model';
import { BoxService } from './box.service';

@Component({
    selector: 'jhi-box-delete-dialog',
    templateUrl: './box-delete-dialog.component.html'
})
export class BoxDeleteDialogComponent {
    box: IBox;

    constructor(private boxService: BoxService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.boxService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'boxListModification',
                content: 'Deleted an box'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-box-delete-popup',
    template: ''
})
export class BoxDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ box }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BoxDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.box = box;
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
