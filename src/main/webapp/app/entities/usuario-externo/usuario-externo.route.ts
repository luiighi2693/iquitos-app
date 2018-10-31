import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UsuarioExterno } from 'app/shared/model/usuario-externo.model';
import { UsuarioExternoService } from './usuario-externo.service';
import { UsuarioExternoComponent } from './usuario-externo.component';
import { UsuarioExternoDetailComponent } from './usuario-externo-detail.component';
import { UsuarioExternoUpdateComponent } from './usuario-externo-update.component';
import { UsuarioExternoDeletePopupComponent } from './usuario-externo-delete-dialog.component';
import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';

@Injectable({ providedIn: 'root' })
export class UsuarioExternoResolve implements Resolve<IUsuarioExterno> {
    constructor(private service: UsuarioExternoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((usuarioExterno: HttpResponse<UsuarioExterno>) => usuarioExterno.body));
        }
        return of(new UsuarioExterno());
    }
}

export const usuarioExternoRoute: Routes = [
    {
        path: 'usuario-externo',
        component: UsuarioExternoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UsuarioExternos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'usuario-externo/:id/view',
        component: UsuarioExternoDetailComponent,
        resolve: {
            usuarioExterno: UsuarioExternoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UsuarioExternos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'usuario-externo/new',
        component: UsuarioExternoUpdateComponent,
        resolve: {
            usuarioExterno: UsuarioExternoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UsuarioExternos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'usuario-externo/:id/edit',
        component: UsuarioExternoUpdateComponent,
        resolve: {
            usuarioExterno: UsuarioExternoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UsuarioExternos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usuarioExternoPopupRoute: Routes = [
    {
        path: 'usuario-externo/:id/delete',
        component: UsuarioExternoDeletePopupComponent,
        resolve: {
            usuarioExterno: UsuarioExternoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UsuarioExternos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
