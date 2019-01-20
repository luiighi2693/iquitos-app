/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductoDetalleDeleteDialogComponent } from 'app/entities/producto-detalle/producto-detalle-delete-dialog.component';
import { ProductoDetalleService } from 'app/entities/producto-detalle/producto-detalle.service';

describe('Component Tests', () => {
    describe('ProductoDetalle Management Delete Component', () => {
        let comp: ProductoDetalleDeleteDialogComponent;
        let fixture: ComponentFixture<ProductoDetalleDeleteDialogComponent>;
        let service: ProductoDetalleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductoDetalleDeleteDialogComponent]
            })
                .overrideTemplate(ProductoDetalleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductoDetalleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoDetalleService);
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
