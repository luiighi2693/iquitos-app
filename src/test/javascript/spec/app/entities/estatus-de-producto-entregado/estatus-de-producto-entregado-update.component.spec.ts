/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { EstatusDeProductoEntregadoUpdateComponent } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado-update.component';
import { EstatusDeProductoEntregadoService } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado.service';
import { EstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';

describe('Component Tests', () => {
    describe('EstatusDeProductoEntregado Management Update Component', () => {
        let comp: EstatusDeProductoEntregadoUpdateComponent;
        let fixture: ComponentFixture<EstatusDeProductoEntregadoUpdateComponent>;
        let service: EstatusDeProductoEntregadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [EstatusDeProductoEntregadoUpdateComponent]
            })
                .overrideTemplate(EstatusDeProductoEntregadoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstatusDeProductoEntregadoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstatusDeProductoEntregadoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EstatusDeProductoEntregado(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.estatusDeProductoEntregado = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EstatusDeProductoEntregado();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.estatusDeProductoEntregado = entity;
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
