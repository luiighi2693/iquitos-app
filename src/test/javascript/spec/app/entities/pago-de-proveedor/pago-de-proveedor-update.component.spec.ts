/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PagoDeProveedorUpdateComponent } from 'app/entities/pago-de-proveedor/pago-de-proveedor-update.component';
import { PagoDeProveedorService } from 'app/entities/pago-de-proveedor/pago-de-proveedor.service';
import { PagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';

describe('Component Tests', () => {
    describe('PagoDeProveedor Management Update Component', () => {
        let comp: PagoDeProveedorUpdateComponent;
        let fixture: ComponentFixture<PagoDeProveedorUpdateComponent>;
        let service: PagoDeProveedorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PagoDeProveedorUpdateComponent]
            })
                .overrideTemplate(PagoDeProveedorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PagoDeProveedorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagoDeProveedorService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PagoDeProveedor(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pagoDeProveedor = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PagoDeProveedor();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pagoDeProveedor = entity;
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
