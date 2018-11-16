/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ContactoProveedorUpdateComponent } from 'app/entities/contacto-proveedor/contacto-proveedor-update.component';
import { ContactoProveedorService } from 'app/entities/contacto-proveedor/contacto-proveedor.service';
import { ContactoProveedor } from 'app/shared/model/contacto-proveedor.model';

describe('Component Tests', () => {
    describe('ContactoProveedor Management Update Component', () => {
        let comp: ContactoProveedorUpdateComponent;
        let fixture: ComponentFixture<ContactoProveedorUpdateComponent>;
        let service: ContactoProveedorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ContactoProveedorUpdateComponent]
            })
                .overrideTemplate(ContactoProveedorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactoProveedorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactoProveedorService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactoProveedor(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactoProveedor = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactoProveedor();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactoProveedor = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
