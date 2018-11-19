/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductosRelacionadosTagsDeleteDialogComponent } from 'app/entities/productos-relacionados-tags/productos-relacionados-tags-delete-dialog.component';
import { ProductosRelacionadosTagsService } from 'app/entities/productos-relacionados-tags/productos-relacionados-tags.service';

describe('Component Tests', () => {
    describe('ProductosRelacionadosTags Management Delete Component', () => {
        let comp: ProductosRelacionadosTagsDeleteDialogComponent;
        let fixture: ComponentFixture<ProductosRelacionadosTagsDeleteDialogComponent>;
        let service: ProductosRelacionadosTagsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductosRelacionadosTagsDeleteDialogComponent]
            })
                .overrideTemplate(ProductosRelacionadosTagsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductosRelacionadosTagsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductosRelacionadosTagsService);
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
