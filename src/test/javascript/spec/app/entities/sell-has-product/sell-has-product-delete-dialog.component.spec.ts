/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IquitosAppTestModule } from '../../../test.module';
import { SellHasProductDeleteDialogComponent } from 'app/entities/sell-has-product/sell-has-product-delete-dialog.component';
import { SellHasProductService } from 'app/entities/sell-has-product/sell-has-product.service';

describe('Component Tests', () => {
    describe('SellHasProduct Management Delete Component', () => {
        let comp: SellHasProductDeleteDialogComponent;
        let fixture: ComponentFixture<SellHasProductDeleteDialogComponent>;
        let service: SellHasProductService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [SellHasProductDeleteDialogComponent]
            })
                .overrideTemplate(SellHasProductDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SellHasProductDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SellHasProductService);
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
