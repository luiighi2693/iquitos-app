/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { SellHasProductUpdateComponent } from 'app/entities/sell-has-product/sell-has-product-update.component';
import { SellHasProductService } from 'app/entities/sell-has-product/sell-has-product.service';
import { SellHasProduct } from 'app/shared/model/sell-has-product.model';

describe('Component Tests', () => {
    describe('SellHasProduct Management Update Component', () => {
        let comp: SellHasProductUpdateComponent;
        let fixture: ComponentFixture<SellHasProductUpdateComponent>;
        let service: SellHasProductService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [SellHasProductUpdateComponent]
            })
                .overrideTemplate(SellHasProductUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SellHasProductUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SellHasProductService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SellHasProduct(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sellHasProduct = entity;
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
                    const entity = new SellHasProduct();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sellHasProduct = entity;
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
