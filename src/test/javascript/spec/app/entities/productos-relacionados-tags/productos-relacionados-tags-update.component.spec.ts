/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProductosRelacionadosTagsUpdateComponent } from 'app/entities/productos-relacionados-tags/productos-relacionados-tags-update.component';
import { ProductosRelacionadosTagsService } from 'app/entities/productos-relacionados-tags/productos-relacionados-tags.service';
import { ProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';

describe('Component Tests', () => {
    describe('ProductosRelacionadosTags Management Update Component', () => {
        let comp: ProductosRelacionadosTagsUpdateComponent;
        let fixture: ComponentFixture<ProductosRelacionadosTagsUpdateComponent>;
        let service: ProductosRelacionadosTagsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProductosRelacionadosTagsUpdateComponent]
            })
                .overrideTemplate(ProductosRelacionadosTagsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductosRelacionadosTagsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductosRelacionadosTagsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductosRelacionadosTags(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productosRelacionadosTags = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductosRelacionadosTags();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productosRelacionadosTags = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
