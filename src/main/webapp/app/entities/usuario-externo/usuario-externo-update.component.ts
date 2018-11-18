import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';
import { UsuarioExternoService } from './usuario-externo.service';

@Component({
    selector: 'jhi-usuario-externo-update',
    templateUrl: './usuario-externo-update.component.html'
})
export class UsuarioExternoUpdateComponent implements OnInit {
    usuarioExterno: IUsuarioExterno;
    isSaving: boolean;

    constructor(private usuarioExternoService: UsuarioExternoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ usuarioExterno }) => {
            this.usuarioExterno = usuarioExterno;
        });
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
}
