/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UnitMeasurementDetailComponent } from 'app/entities/unit-measurement/unit-measurement-detail.component';
import { UnitMeasurement } from 'app/shared/model/unit-measurement.model';

describe('Component Tests', () => {
    describe('UnitMeasurement Management Detail Component', () => {
        let comp: UnitMeasurementDetailComponent;
        let fixture: ComponentFixture<UnitMeasurementDetailComponent>;
        const route = ({ data: of({ unitMeasurement: new UnitMeasurement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UnitMeasurementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UnitMeasurementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnitMeasurementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.unitMeasurement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
