/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeVentaDeleteDialogComponent } from 'app/entities/tipo-de-documento-de-venta/tipo-de-documento-de-venta-delete-dialog.component';
import { TipoDeDocumentoDeVentaService } from 'app/entities/tipo-de-documento-de-venta/tipo-de-documento-de-venta.service';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeVenta Management Delete Component', () => {
        let comp: TipoDeDocumentoDeVentaDeleteDialogComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeVentaDeleteDialogComponent>;
        let service: TipoDeDocumentoDeVentaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeVentaDeleteDialogComponent]
            })
                .overrideTemplate(TipoDeDocumentoDeVentaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeDocumentoDeVentaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeDocumentoDeVentaService);
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
