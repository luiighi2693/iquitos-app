/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UnitMeasurementUpdateComponent } from 'app/entities/unit-measurement/unit-measurement-update.component';
import { UnitMeasurementService } from 'app/entities/unit-measurement/unit-measurement.service';
import { UnitMeasurement } from 'app/shared/model/unit-measurement.model';

describe('Component Tests', () => {
    describe('UnitMeasurement Management Update Component', () => {
        let comp: UnitMeasurementUpdateComponent;
        let fixture: ComponentFixture<UnitMeasurementUpdateComponent>;
        let service: UnitMeasurementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UnitMeasurementUpdateComponent]
            })
                .overrideTemplate(UnitMeasurementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnitMeasurementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitMeasurementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UnitMeasurement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unitMeasurement = entity;
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
                    const entity = new UnitMeasurement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unitMeasurement = entity;
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
