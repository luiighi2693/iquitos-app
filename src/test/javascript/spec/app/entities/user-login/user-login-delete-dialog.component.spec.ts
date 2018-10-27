/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { UserLoginDeleteDialogComponent } from 'app/entities/user-login/user-login-delete-dialog.component';
import { UserLoginService } from 'app/entities/user-login/user-login.service';

describe('Component Tests', () => {
    describe('UserLogin Management Delete Component', () => {
        let comp: UserLoginDeleteDialogComponent;
        let fixture: ComponentFixture<UserLoginDeleteDialogComponent>;
        let service: UserLoginService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UserLoginDeleteDialogComponent]
            })
                .overrideTemplate(UserLoginDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserLoginDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserLoginService);
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
