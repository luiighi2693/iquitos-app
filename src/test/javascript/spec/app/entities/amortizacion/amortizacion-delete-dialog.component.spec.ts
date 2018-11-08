/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { AmortizacionDeleteDialogComponent } from 'app/entities/amortizacion/amortizacion-delete-dialog.component';
import { AmortizacionService } from 'app/entities/amortizacion/amortizacion.service';

describe('Component Tests', () => {
    describe('Amortizacion Management Delete Component', () => {
        let comp: AmortizacionDeleteDialogComponent;
        let fixture: ComponentFixture<AmortizacionDeleteDialogComponent>;
        let service: AmortizacionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [AmortizacionDeleteDialogComponent]
            })
                .overrideTemplate(AmortizacionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AmortizacionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmortizacionService);
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
