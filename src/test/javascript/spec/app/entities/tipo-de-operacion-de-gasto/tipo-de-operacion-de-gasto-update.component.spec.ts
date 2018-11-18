/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeOperacionDeGastoUpdateComponent } from 'app/entities/tipo-de-operacion-de-gasto/tipo-de-operacion-de-gasto-update.component';
import { TipoDeOperacionDeGastoService } from 'app/entities/tipo-de-operacion-de-gasto/tipo-de-operacion-de-gasto.service';
import { TipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';

describe('Component Tests', () => {
    describe('TipoDeOperacionDeGasto Management Update Component', () => {
        let comp: TipoDeOperacionDeGastoUpdateComponent;
        let fixture: ComponentFixture<TipoDeOperacionDeGastoUpdateComponent>;
        let service: TipoDeOperacionDeGastoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeOperacionDeGastoUpdateComponent]
            })
                .overrideTemplate(TipoDeOperacionDeGastoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeOperacionDeGastoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeOperacionDeGastoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeOperacionDeGasto(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeOperacionDeGasto = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeOperacionDeGasto();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeOperacionDeGasto = entity;
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
