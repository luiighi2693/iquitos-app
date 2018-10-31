/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDePagoDetailComponent } from 'app/entities/tipo-de-pago/tipo-de-pago-detail.component';
import { TipoDePago } from 'app/shared/model/tipo-de-pago.model';

describe('Component Tests', () => {
    describe('TipoDePago Management Detail Component', () => {
        let comp: TipoDePagoDetailComponent;
        let fixture: ComponentFixture<TipoDePagoDetailComponent>;
        const route = ({ data: of({ tipoDePago: new TipoDePago(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDePagoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDePagoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDePagoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDePago).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
