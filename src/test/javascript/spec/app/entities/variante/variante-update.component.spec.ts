/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { VarianteUpdateComponent } from 'app/entities/variante/variante-update.component';
import { VarianteService } from 'app/entities/variante/variante.service';
import { Variante } from 'app/shared/model/variante.model';

describe('Component Tests', () => {
    describe('Variante Management Update Component', () => {
        let comp: VarianteUpdateComponent;
        let fixture: ComponentFixture<VarianteUpdateComponent>;
        let service: VarianteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [VarianteUpdateComponent]
            })
                .overrideTemplate(VarianteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VarianteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VarianteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Variante(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.variante = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Variante();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.variante = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
