/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeOperacionDeIngresoUpdateComponent } from 'app/entities/tipo-de-operacion-de-ingreso/tipo-de-operacion-de-ingreso-update.component';
import { TipoDeOperacionDeIngresoService } from 'app/entities/tipo-de-operacion-de-ingreso/tipo-de-operacion-de-ingreso.service';
import { TipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';

describe('Component Tests', () => {
    describe('TipoDeOperacionDeIngreso Management Update Component', () => {
        let comp: TipoDeOperacionDeIngresoUpdateComponent;
        let fixture: ComponentFixture<TipoDeOperacionDeIngresoUpdateComponent>;
        let service: TipoDeOperacionDeIngresoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeOperacionDeIngresoUpdateComponent]
            })
                .overrideTemplate(TipoDeOperacionDeIngresoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeOperacionDeIngresoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeOperacionDeIngresoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeOperacionDeIngreso(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeOperacionDeIngreso = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeOperacionDeIngreso();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeOperacionDeIngreso = entity;
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
