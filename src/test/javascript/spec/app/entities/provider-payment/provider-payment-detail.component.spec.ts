/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProviderPaymentDetailComponent } from 'app/entities/provider-payment/provider-payment-detail.component';
import { ProviderPayment } from 'app/shared/model/provider-payment.model';

describe('Component Tests', () => {
    describe('ProviderPayment Management Detail Component', () => {
        let comp: ProviderPaymentDetailComponent;
        let fixture: ComponentFixture<ProviderPaymentDetailComponent>;
        const route = ({ data: of({ providerPayment: new ProviderPayment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProviderPaymentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProviderPaymentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProviderPaymentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.providerPayment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
