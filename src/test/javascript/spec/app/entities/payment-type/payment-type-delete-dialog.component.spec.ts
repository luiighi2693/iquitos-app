/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { PaymentTypeDeleteDialogComponent } from 'app/entities/payment-type/payment-type-delete-dialog.component';
import { PaymentTypeService } from 'app/entities/payment-type/payment-type.service';

describe('Component Tests', () => {
    describe('PaymentType Management Delete Component', () => {
        let comp: PaymentTypeDeleteDialogComponent;
        let fixture: ComponentFixture<PaymentTypeDeleteDialogComponent>;
        let service: PaymentTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PaymentTypeDeleteDialogComponent]
            })
                .overrideTemplate(PaymentTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
