/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { DocumentTypePurchaseDeleteDialogComponent } from 'app/entities/document-type-purchase/document-type-purchase-delete-dialog.component';
import { DocumentTypePurchaseService } from 'app/entities/document-type-purchase/document-type-purchase.service';

describe('Component Tests', () => {
    describe('DocumentTypePurchase Management Delete Component', () => {
        let comp: DocumentTypePurchaseDeleteDialogComponent;
        let fixture: ComponentFixture<DocumentTypePurchaseDeleteDialogComponent>;
        let service: DocumentTypePurchaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [DocumentTypePurchaseDeleteDialogComponent]
            })
                .overrideTemplate(DocumentTypePurchaseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTypePurchaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTypePurchaseService);
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
