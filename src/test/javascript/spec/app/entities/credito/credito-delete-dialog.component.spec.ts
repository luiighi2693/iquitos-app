/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { CreditoDeleteDialogComponent } from 'app/entities/credito/credito-delete-dialog.component';
import { CreditoService } from 'app/entities/credito/credito.service';

describe('Component Tests', () => {
    describe('Credito Management Delete Component', () => {
        let comp: CreditoDeleteDialogComponent;
        let fixture: ComponentFixture<CreditoDeleteDialogComponent>;
        let service: CreditoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [CreditoDeleteDialogComponent]
            })
                .overrideTemplate(CreditoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditoService);
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
