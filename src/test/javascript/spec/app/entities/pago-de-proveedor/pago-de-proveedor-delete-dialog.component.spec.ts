/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { PagoDeProveedorDeleteDialogComponent } from 'app/entities/pago-de-proveedor/pago-de-proveedor-delete-dialog.component';
import { PagoDeProveedorService } from 'app/entities/pago-de-proveedor/pago-de-proveedor.service';

describe('Component Tests', () => {
    describe('PagoDeProveedor Management Delete Component', () => {
        let comp: PagoDeProveedorDeleteDialogComponent;
        let fixture: ComponentFixture<PagoDeProveedorDeleteDialogComponent>;
        let service: PagoDeProveedorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PagoDeProveedorDeleteDialogComponent]
            })
                .overrideTemplate(PagoDeProveedorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PagoDeProveedorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagoDeProveedorService);
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
