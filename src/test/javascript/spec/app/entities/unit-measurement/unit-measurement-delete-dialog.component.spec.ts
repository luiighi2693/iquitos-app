/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { UnitMeasurementDeleteDialogComponent } from 'app/entities/unit-measurement/unit-measurement-delete-dialog.component';
import { UnitMeasurementService } from 'app/entities/unit-measurement/unit-measurement.service';

describe('Component Tests', () => {
    describe('UnitMeasurement Management Delete Component', () => {
        let comp: UnitMeasurementDeleteDialogComponent;
        let fixture: ComponentFixture<UnitMeasurementDeleteDialogComponent>;
        let service: UnitMeasurementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UnitMeasurementDeleteDialogComponent]
            })
                .overrideTemplate(UnitMeasurementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnitMeasurementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitMeasurementService);
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
