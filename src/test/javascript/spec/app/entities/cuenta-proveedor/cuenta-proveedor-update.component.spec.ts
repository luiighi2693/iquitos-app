/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { CuentaProveedorUpdateComponent } from 'app/entities/cuenta-proveedor/cuenta-proveedor-update.component';
import { CuentaProveedorService } from 'app/entities/cuenta-proveedor/cuenta-proveedor.service';
import { CuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';

describe('Component Tests', () => {
    describe('CuentaProveedor Management Update Component', () => {
        let comp: CuentaProveedorUpdateComponent;
        let fixture: ComponentFixture<CuentaProveedorUpdateComponent>;
        let service: CuentaProveedorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [CuentaProveedorUpdateComponent]
            })
                .overrideTemplate(CuentaProveedorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CuentaProveedorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CuentaProveedorService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CuentaProveedor(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cuentaProveedor = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CuentaProveedor();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cuentaProveedor = entity;
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
