/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { ConceptOperationOutputDeleteDialogComponent } from 'app/entities/concept-operation-output/concept-operation-output-delete-dialog.component';
import { ConceptOperationOutputService } from 'app/entities/concept-operation-output/concept-operation-output.service';

describe('Component Tests', () => {
    describe('ConceptOperationOutput Management Delete Component', () => {
        let comp: ConceptOperationOutputDeleteDialogComponent;
        let fixture: ComponentFixture<ConceptOperationOutputDeleteDialogComponent>;
        let service: ConceptOperationOutputService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ConceptOperationOutputDeleteDialogComponent]
            })
                .overrideTemplate(ConceptOperationOutputDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConceptOperationOutputDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConceptOperationOutputService);
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
