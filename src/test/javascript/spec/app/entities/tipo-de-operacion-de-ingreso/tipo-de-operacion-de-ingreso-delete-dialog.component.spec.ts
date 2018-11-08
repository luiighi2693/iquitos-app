/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeOperacionDeIngresoDeleteDialogComponent } from 'app/entities/tipo-de-operacion-de-ingreso/tipo-de-operacion-de-ingreso-delete-dialog.component';
import { TipoDeOperacionDeIngresoService } from 'app/entities/tipo-de-operacion-de-ingreso/tipo-de-operacion-de-ingreso.service';

describe('Component Tests', () => {
    describe('TipoDeOperacionDeIngreso Management Delete Component', () => {
        let comp: TipoDeOperacionDeIngresoDeleteDialogComponent;
        let fixture: ComponentFixture<TipoDeOperacionDeIngresoDeleteDialogComponent>;
        let service: TipoDeOperacionDeIngresoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeOperacionDeIngresoDeleteDialogComponent]
            })
                .overrideTemplate(TipoDeOperacionDeIngresoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeOperacionDeIngresoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeOperacionDeIngresoService);
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
