/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { AmortizationDetailComponent } from 'app/entities/amortization/amortization-detail.component';
import { Amortization } from 'app/shared/model/amortization.model';

describe('Component Tests', () => {
    describe('Amortization Management Detail Component', () => {
        let comp: AmortizationDetailComponent;
        let fixture: ComponentFixture<AmortizationDetailComponent>;
        const route = ({ data: of({ amortization: new Amortization(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [AmortizationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AmortizationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AmortizationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.amortization).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
