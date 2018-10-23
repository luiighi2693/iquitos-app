/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProviderPaymentUpdateComponent } from 'app/entities/provider-payment/provider-payment-update.component';
import { ProviderPaymentService } from 'app/entities/provider-payment/provider-payment.service';
import { ProviderPayment } from 'app/shared/model/provider-payment.model';

describe('Component Tests', () => {
    describe('ProviderPayment Management Update Component', () => {
        let comp: ProviderPaymentUpdateComponent;
        let fixture: ComponentFixture<ProviderPaymentUpdateComponent>;
        let service: ProviderPaymentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProviderPaymentUpdateComponent]
            })
                .overrideTemplate(ProviderPaymentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProviderPaymentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProviderPaymentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProviderPayment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.providerPayment = entity;
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
                    const entity = new ProviderPayment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.providerPayment = entity;
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
