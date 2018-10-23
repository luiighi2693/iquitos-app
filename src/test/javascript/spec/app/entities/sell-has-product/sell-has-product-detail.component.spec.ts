/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { SellHasProductDetailComponent } from 'app/entities/sell-has-product/sell-has-product-detail.component';
import { SellHasProduct } from 'app/shared/model/sell-has-product.model';

describe('Component Tests', () => {
    describe('SellHasProduct Management Detail Component', () => {
        let comp: SellHasProductDetailComponent;
        let fixture: ComponentFixture<SellHasProductDetailComponent>;
        const route = ({ data: of({ sellHasProduct: new SellHasProduct(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [SellHasProductDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SellHasProductDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SellHasProductDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sellHasProduct).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
