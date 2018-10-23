/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { PurchaseStatusDeleteDialogComponent } from 'app/entities/purchase-status/purchase-status-delete-dialog.component';
import { PurchaseStatusService } from 'app/entities/purchase-status/purchase-status.service';

describe('Component Tests', () => {
    describe('PurchaseStatus Management Delete Component', () => {
        let comp: PurchaseStatusDeleteDialogComponent;
        let fixture: ComponentFixture<PurchaseStatusDeleteDialogComponent>;
        let service: PurchaseStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PurchaseStatusDeleteDialogComponent]
            })
                .overrideTemplate(PurchaseStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseStatusService);
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
