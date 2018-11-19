/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductosRelacionadosTagsDetailComponent } from 'app/entities/productos-relacionados-tags/productos-relacionados-tags-detail.component';
import { ProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';

describe('Component Tests', () => {
    describe('ProductosRelacionadosTags Management Detail Component', () => {
        let comp: ProductosRelacionadosTagsDetailComponent;
        let fixture: ComponentFixture<ProductosRelacionadosTagsDetailComponent>;
        const route = ({ data: of({ productosRelacionadosTags: new ProductosRelacionadosTags(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductosRelacionadosTagsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductosRelacionadosTagsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductosRelacionadosTagsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productosRelacionadosTags).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
