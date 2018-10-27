import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Provider } from 'app/shared/model/provider.model';
import { ProviderService } from './provider.service';
import { ProviderComponent } from './provider.component';
import { ProviderDetailComponent } from './provider-detail.component';
import { ProviderUpdateComponent } from './provider-update.component';
import { ProviderDeletePopupComponent } from './provider-delete-dialog.component';
import { IProvider } from 'app/shared/model/provider.model';

@Injectable({ providedIn: 'root' })
export class ProviderResolve implements Resolve<IProvider> {
    constructor(private service: ProviderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((provider: HttpResponse<Provider>) => provider.body));
        }
        return of(new Provider());
    }
}

export const providerRoute: Routes = [
    {
        path: 'provider',
        component: ProviderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Providers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider/:id/view',
        component: ProviderDetailComponent,
        resolve: {
            provider: ProviderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Providers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider/new',
        component: ProviderUpdateComponent,
        resolve: {
            provider: ProviderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Providers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider/:id/edit',
        component: ProviderUpdateComponent,
        resolve: {
            provider: ProviderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Providers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const providerPopupRoute: Routes = [
    {
        path: 'provider/:id/delete',
        component: ProviderDeletePopupComponent,
        resolve: {
            provider: ProviderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Providers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
