/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PurchaseHasProductDetailComponent } from 'app/entities/purchase-has-product/purchase-has-product-detail.component';
import { PurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';

describe('Component Tests', () => {
    describe('PurchaseHasProduct Management Detail Component', () => {
        let comp: PurchaseHasProductDetailComponent;
        let fixture: ComponentFixture<PurchaseHasProductDetailComponent>;
        const route = ({ data: of({ purchaseHasProduct: new PurchaseHasProduct(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PurchaseHasProductDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchaseHasProductDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseHasProductDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchaseHasProduct).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
