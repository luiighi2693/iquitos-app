/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDePagoDeleteDialogComponent } from 'app/entities/tipo-de-pago/tipo-de-pago-delete-dialog.component';
import { TipoDePagoService } from 'app/entities/tipo-de-pago/tipo-de-pago.service';

describe('Component Tests', () => {
    describe('TipoDePago Management Delete Component', () => {
        let comp: TipoDePagoDeleteDialogComponent;
        let fixture: ComponentFixture<TipoDePagoDeleteDialogComponent>;
        let service: TipoDePagoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDePagoDeleteDialogComponent]
            })
                .overrideTemplate(TipoDePagoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDePagoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDePagoService);
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
