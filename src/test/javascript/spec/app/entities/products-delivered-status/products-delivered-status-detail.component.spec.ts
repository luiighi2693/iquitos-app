/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductsDeliveredStatusDetailComponent } from 'app/entities/products-delivered-status/products-delivered-status-detail.component';
import { ProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';

describe('Component Tests', () => {
    describe('ProductsDeliveredStatus Management Detail Component', () => {
        let comp: ProductsDeliveredStatusDetailComponent;
        let fixture: ComponentFixture<ProductsDeliveredStatusDetailComponent>;
        const route = ({ data: of({ productsDeliveredStatus: new ProductsDeliveredStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductsDeliveredStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductsDeliveredStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductsDeliveredStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productsDeliveredStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
