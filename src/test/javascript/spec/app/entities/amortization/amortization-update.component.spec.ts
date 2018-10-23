/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { AmortizationUpdateComponent } from 'app/entities/amortization/amortization-update.component';
import { AmortizationService } from 'app/entities/amortization/amortization.service';
import { Amortization } from 'app/shared/model/amortization.model';

describe('Component Tests', () => {
    describe('Amortization Management Update Component', () => {
        let comp: AmortizationUpdateComponent;
        let fixture: ComponentFixture<AmortizationUpdateComponent>;
        let service: AmortizationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [AmortizationUpdateComponent]
            })
                .overrideTemplate(AmortizationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AmortizationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmortizationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Amortization(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.amortization = entity;
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
                    const entity = new Amortization();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.amortization = entity;
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
