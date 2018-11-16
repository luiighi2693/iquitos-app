/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { ContactoProveedorDeleteDialogComponent } from 'app/entities/contacto-proveedor/contacto-proveedor-delete-dialog.component';
import { ContactoProveedorService } from 'app/entities/contacto-proveedor/contacto-proveedor.service';

describe('Component Tests', () => {
    describe('ContactoProveedor Management Delete Component', () => {
        let comp: ContactoProveedorDeleteDialogComponent;
        let fixture: ComponentFixture<ContactoProveedorDeleteDialogComponent>;
        let service: ContactoProveedorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ContactoProveedorDeleteDialogComponent]
            })
                .overrideTemplate(ContactoProveedorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactoProveedorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactoProveedorService);
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
