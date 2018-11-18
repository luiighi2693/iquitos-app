/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UnidadDeMedidaUpdateComponent } from 'app/entities/unidad-de-medida/unidad-de-medida-update.component';
import { UnidadDeMedidaService } from 'app/entities/unidad-de-medida/unidad-de-medida.service';
import { UnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';

describe('Component Tests', () => {
    describe('UnidadDeMedida Management Update Component', () => {
        let comp: UnidadDeMedidaUpdateComponent;
        let fixture: ComponentFixture<UnidadDeMedidaUpdateComponent>;
        let service: UnidadDeMedidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UnidadDeMedidaUpdateComponent]
            })
                .overrideTemplate(UnidadDeMedidaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnidadDeMedidaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnidadDeMedidaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UnidadDeMedida(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.unidadDeMedida = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UnidadDeMedida();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.unidadDeMedida = entity;
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
