import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';
import { ProductosRelacionadosTagsService } from './productos-relacionados-tags.service';
import { ProductosRelacionadosTagsComponent } from './productos-relacionados-tags.component';
import { ProductosRelacionadosTagsDetailComponent } from './productos-relacionados-tags-detail.component';
import { ProductosRelacionadosTagsUpdateComponent } from './productos-relacionados-tags-update.component';
import { ProductosRelacionadosTagsDeletePopupComponent } from './productos-relacionados-tags-delete-dialog.component';
import { IProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';

@Injectable({ providedIn: 'root' })
export class ProductosRelacionadosTagsResolve implements Resolve<IProductosRelacionadosTags> {
    constructor(private service: ProductosRelacionadosTagsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProductosRelacionadosTags> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductosRelacionadosTags>) => response.ok),
                map((productosRelacionadosTags: HttpResponse<ProductosRelacionadosTags>) => productosRelacionadosTags.body)
            );
        }
        return of(new ProductosRelacionadosTags());
    }
}

export const productosRelacionadosTagsRoute: Routes = [
    {
        path: 'productos-relacionados-tags',
        component: ProductosRelacionadosTagsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductosRelacionadosTags'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'productos-relacionados-tags/:id/view',
        component: ProductosRelacionadosTagsDetailComponent,
        resolve: {
            productosRelacionadosTags: ProductosRelacionadosTagsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductosRelacionadosTags'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'productos-relacionados-tags/new',
        component: ProductosRelacionadosTagsUpdateComponent,
        resolve: {
            productosRelacionadosTags: ProductosRelacionadosTagsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductosRelacionadosTags'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'productos-relacionados-tags/:id/edit',
        component: ProductosRelacionadosTagsUpdateComponent,
        resolve: {
            productosRelacionadosTags: ProductosRelacionadosTagsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductosRelacionadosTags'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productosRelacionadosTagsPopupRoute: Routes = [
    {
        path: 'productos-relacionados-tags/:id/delete',
        component: ProductosRelacionadosTagsDeletePopupComponent,
        resolve: {
            productosRelacionadosTags: ProductosRelacionadosTagsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductosRelacionadosTags'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
