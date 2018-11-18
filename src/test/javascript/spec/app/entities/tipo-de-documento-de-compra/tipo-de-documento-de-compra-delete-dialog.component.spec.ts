/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeCompraDeleteDialogComponent } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra-delete-dialog.component';
import { TipoDeDocumentoDeCompraService } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra.service';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeCompra Management Delete Component', () => {
        let comp: TipoDeDocumentoDeCompraDeleteDialogComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeCompraDeleteDialogComponent>;
        let service: TipoDeDocumentoDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeCompraDeleteDialogComponent]
            })
                .overrideTemplate(TipoDeDocumentoDeCompraDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeDocumentoDeCompraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeDocumentoDeCompraService);
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
