/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PaymentTypeDetailComponent } from 'app/entities/payment-type/payment-type-detail.component';
import { PaymentType } from 'app/shared/model/payment-type.model';

describe('Component Tests', () => {
    describe('PaymentType Management Detail Component', () => {
        let comp: PaymentTypeDetailComponent;
        let fixture: ComponentFixture<PaymentTypeDetailComponent>;
        const route = ({ data: of({ paymentType: new PaymentType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PaymentTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PaymentTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.paymentType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
