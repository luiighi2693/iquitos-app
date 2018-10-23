/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { ProviderAccountDeleteDialogComponent } from 'app/entities/provider-account/provider-account-delete-dialog.component';
import { ProviderAccountService } from 'app/entities/provider-account/provider-account.service';

describe('Component Tests', () => {
    describe('ProviderAccount Management Delete Component', () => {
        let comp: ProviderAccountDeleteDialogComponent;
        let fixture: ComponentFixture<ProviderAccountDeleteDialogComponent>;
        let service: ProviderAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProviderAccountDeleteDialogComponent]
            })
                .overrideTemplate(ProviderAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProviderAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProviderAccountService);
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
