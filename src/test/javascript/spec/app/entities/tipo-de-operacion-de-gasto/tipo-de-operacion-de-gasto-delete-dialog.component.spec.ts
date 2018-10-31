/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeOperacionDeGastoDeleteDialogComponent } from 'app/entities/tipo-de-operacion-de-gasto/tipo-de-operacion-de-gasto-delete-dialog.component';
import { TipoDeOperacionDeGastoService } from 'app/entities/tipo-de-operacion-de-gasto/tipo-de-operacion-de-gasto.service';

describe('Component Tests', () => {
    describe('TipoDeOperacionDeGasto Management Delete Component', () => {
        let comp: TipoDeOperacionDeGastoDeleteDialogComponent;
        let fixture: ComponentFixture<TipoDeOperacionDeGastoDeleteDialogComponent>;
        let service: TipoDeOperacionDeGastoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeOperacionDeGastoDeleteDialogComponent]
            })
                .overrideTemplate(TipoDeOperacionDeGastoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeOperacionDeGastoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeOperacionDeGastoService);
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
