/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { EstatusDeProductoEntregadoDeleteDialogComponent } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado-delete-dialog.component';
import { EstatusDeProductoEntregadoService } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado.service';

describe('Component Tests', () => {
    describe('EstatusDeProductoEntregado Management Delete Component', () => {
        let comp: EstatusDeProductoEntregadoDeleteDialogComponent;
        let fixture: ComponentFixture<EstatusDeProductoEntregadoDeleteDialogComponent>;
        let service: EstatusDeProductoEntregadoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [EstatusDeProductoEntregadoDeleteDialogComponent]
            })
                .overrideTemplate(EstatusDeProductoEntregadoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstatusDeProductoEntregadoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstatusDeProductoEntregadoService);
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
