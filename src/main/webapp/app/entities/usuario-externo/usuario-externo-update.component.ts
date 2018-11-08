import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';
import { UsuarioExternoService } from './usuario-externo.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-usuario-externo-update',
    templateUrl: './usuario-externo-update.component.html'
})
export class UsuarioExternoUpdateComponent implements OnInit {
    usuarioExterno: IUsuarioExterno;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private usuarioExternoService: UsuarioExternoService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ usuarioExterno }) => {
            this.usuarioExterno = usuarioExterno;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.usuarioExterno.id !== undefined) {
            this.subscribeToSaveResponse(this.usuarioExternoService.update(this.usuarioExterno));
        } else {
            this.subscribeToSaveResponse(this.usuarioExternoService.create(this.usuarioExterno));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioExterno>>) {
        result.subscribe((res: HttpResponse<IUsuarioExterno>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
