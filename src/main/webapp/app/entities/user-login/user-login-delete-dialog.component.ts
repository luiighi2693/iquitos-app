import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserLogin } from 'app/shared/model/user-login.model';
import { UserLoginService } from './user-login.service';

@Component({
    selector: 'jhi-user-login-delete-dialog',
    templateUrl: './user-login-delete-dialog.component.html'
})
export class UserLoginDeleteDialogComponent {
    userLogin: IUserLogin;

    constructor(private userLoginService: UserLoginService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userLoginService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userLoginListModification',
                content: 'Deleted an userLogin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-login-delete-popup',
    template: ''
})
export class UserLoginDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userLogin }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserLoginDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.userLogin = userLogin;
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
