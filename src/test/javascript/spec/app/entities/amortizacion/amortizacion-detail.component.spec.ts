/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { AmortizacionDetailComponent } from 'app/entities/amortizacion/amortizacion-detail.component';
import { Amortizacion } from 'app/shared/model/amortizacion.model';

describe('Component Tests', () => {
    describe('Amortizacion Management Detail Component', () => {
        let comp: AmortizacionDetailComponent;
        let fixture: ComponentFixture<AmortizacionDetailComponent>;
        const route = ({ data: of({ amortizacion: new Amortizacion(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [AmortizacionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AmortizacionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AmortizacionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.amortizacion).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
