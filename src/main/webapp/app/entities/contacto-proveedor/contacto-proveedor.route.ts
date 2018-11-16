import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactoProveedor } from 'app/shared/model/contacto-proveedor.model';
import { ContactoProveedorService } from './contacto-proveedor.service';
import { ContactoProveedorComponent } from './contacto-proveedor.component';
import { ContactoProveedorDetailComponent } from './contacto-proveedor-detail.component';
import { ContactoProveedorUpdateComponent } from './contacto-proveedor-update.component';
import { ContactoProveedorDeletePopupComponent } from './contacto-proveedor-delete-dialog.component';
import { IContactoProveedor } from 'app/shared/model/contacto-proveedor.model';

@Injectable({ providedIn: 'root' })
export class ContactoProveedorResolve implements Resolve<IContactoProveedor> {
    constructor(private service: ContactoProveedorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactoProveedor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactoProveedor>) => response.ok),
                map((contactoProveedor: HttpResponse<ContactoProveedor>) => contactoProveedor.body)
            );
        }
        return of(new ContactoProveedor());
    }
}

export const contactoProveedorRoute: Routes = [
    {
        path: 'contacto-proveedor',
        component: ContactoProveedorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactoProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contacto-proveedor/:id/view',
        component: ContactoProveedorDetailComponent,
        resolve: {
            contactoProveedor: ContactoProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactoProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contacto-proveedor/new',
        component: ContactoProveedorUpdateComponent,
        resolve: {
            contactoProveedor: ContactoProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactoProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contacto-proveedor/:id/edit',
        component: ContactoProveedorUpdateComponent,
        resolve: {
            contactoProveedor: ContactoProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactoProveedors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactoProveedorPopupRoute: Routes = [
    {
        path: 'contacto-proveedor/:id/delete',
        component: ContactoProveedorDeletePopupComponent,
        resolve: {
            contactoProveedor: ContactoProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactoProveedors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
