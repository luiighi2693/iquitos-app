/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { CreditoUpdateComponent } from 'app/entities/credito/credito-update.component';
import { CreditoService } from 'app/entities/credito/credito.service';
import { Credito } from 'app/shared/model/credito.model';

describe('Component Tests', () => {
    describe('Credito Management Update Component', () => {
        let comp: CreditoUpdateComponent;
        let fixture: ComponentFixture<CreditoUpdateComponent>;
        let service: CreditoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [CreditoUpdateComponent]
            })
                .overrideTemplate(CreditoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Credito(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.credito = entity;
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
                    const entity = new Credito();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.credito = entity;
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
