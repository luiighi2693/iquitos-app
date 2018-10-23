/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PurchaseHasProductUpdateComponent } from 'app/entities/purchase-has-product/purchase-has-product-update.component';
import { PurchaseHasProductService } from 'app/entities/purchase-has-product/purchase-has-product.service';
import { PurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';

describe('Component Tests', () => {
    describe('PurchaseHasProduct Management Update Component', () => {
        let comp: PurchaseHasProductUpdateComponent;
        let fixture: ComponentFixture<PurchaseHasProductUpdateComponent>;
        let service: PurchaseHasProductService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PurchaseHasProductUpdateComponent]
            })
                .overrideTemplate(PurchaseHasProductUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseHasProductUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseHasProductService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PurchaseHasProduct(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseHasProduct = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PurchaseHasProduct();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseHasProduct = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
