/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductoDetalleDetailComponent } from 'app/entities/producto-detalle/producto-detalle-detail.component';
import { ProductoDetalle } from 'app/shared/model/producto-detalle.model';

describe('Component Tests', () => {
    describe('ProductoDetalle Management Detail Component', () => {
        let comp: ProductoDetalleDetailComponent;
        let fixture: ComponentFixture<ProductoDetalleDetailComponent>;
        const route = ({ data: of({ productoDetalle: new ProductoDetalle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductoDetalleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductoDetalleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductoDetalleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productoDetalle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
