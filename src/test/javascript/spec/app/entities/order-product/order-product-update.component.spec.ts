/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { OrderProductUpdateComponent } from 'app/entities/order-product/order-product-update.component';
import { OrderProductService } from 'app/entities/order-product/order-product.service';
import { OrderProduct } from 'app/shared/model/order-product.model';

describe('Component Tests', () => {
    describe('OrderProduct Management Update Component', () => {
        let comp: OrderProductUpdateComponent;
        let fixture: ComponentFixture<OrderProductUpdateComponent>;
        let service: OrderProductService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [OrderProductUpdateComponent]
            })
                .overrideTemplate(OrderProductUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderProductUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderProductService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderProduct(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderProduct = entity;
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
                    const entity = new OrderProduct();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderProduct = entity;
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
