import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';

@Component({
    selector: 'jhi-usuario-externo-detail',
    templateUrl: './usuario-externo-detail.component.html'
})
export class UsuarioExternoDetailComponent implements OnInit {
    usuarioExterno: IUsuarioExterno;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ usuarioExterno }) => {
            this.usuarioExterno = usuarioExterno;
        });
    }

    previousState() {
        window.history.back();
    }
}
