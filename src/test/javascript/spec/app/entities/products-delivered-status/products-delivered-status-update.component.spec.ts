/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductsDeliveredStatusUpdateComponent } from 'app/entities/products-delivered-status/products-delivered-status-update.component';
import { ProductsDeliveredStatusService } from 'app/entities/products-delivered-status/products-delivered-status.service';
import { ProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';

describe('Component Tests', () => {
    describe('ProductsDeliveredStatus Management Update Component', () => {
        let comp: ProductsDeliveredStatusUpdateComponent;
        let fixture: ComponentFixture<ProductsDeliveredStatusUpdateComponent>;
        let service: ProductsDeliveredStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductsDeliveredStatusUpdateComponent]
            })
                .overrideTemplate(ProductsDeliveredStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductsDeliveredStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductsDeliveredStatusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductsDeliveredStatus(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productsDeliveredStatus = entity;
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
                    const entity = new ProductsDeliveredStatus();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productsDeliveredStatus = entity;
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
