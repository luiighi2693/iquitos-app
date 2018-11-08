/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { UnidadDeMedidaDeleteDialogComponent } from 'app/entities/unidad-de-medida/unidad-de-medida-delete-dialog.component';
import { UnidadDeMedidaService } from 'app/entities/unidad-de-medida/unidad-de-medida.service';

describe('Component Tests', () => {
    describe('UnidadDeMedida Management Delete Component', () => {
        let comp: UnidadDeMedidaDeleteDialogComponent;
        let fixture: ComponentFixture<UnidadDeMedidaDeleteDialogComponent>;
        let service: UnidadDeMedidaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UnidadDeMedidaDeleteDialogComponent]
            })
                .overrideTemplate(UnidadDeMedidaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnidadDeMedidaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnidadDeMedidaService);
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
