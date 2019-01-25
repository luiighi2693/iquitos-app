/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ParametroSistemaUpdateComponent } from 'app/entities/parametro-sistema/parametro-sistema-update.component';
import { ParametroSistemaService } from 'app/entities/parametro-sistema/parametro-sistema.service';
import { ParametroSistema } from 'app/shared/model/parametro-sistema.model';

describe('Component Tests', () => {
    describe('ParametroSistema Management Update Component', () => {
        let comp: ParametroSistemaUpdateComponent;
        let fixture: ComponentFixture<ParametroSistemaUpdateComponent>;
        let service: ParametroSistemaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ParametroSistemaUpdateComponent]
            })
                .overrideTemplate(ParametroSistemaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParametroSistemaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametroSistemaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ParametroSistema(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.parametroSistema = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ParametroSistema();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.parametroSistema = entity;
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
