/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { VariantUpdateComponent } from 'app/entities/variant/variant-update.component';
import { VariantService } from 'app/entities/variant/variant.service';
import { Variant } from 'app/shared/model/variant.model';

describe('Component Tests', () => {
    describe('Variant Management Update Component', () => {
        let comp: VariantUpdateComponent;
        let fixture: ComponentFixture<VariantUpdateComponent>;
        let service: VariantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [VariantUpdateComponent]
            })
                .overrideTemplate(VariantUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VariantUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VariantService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Variant(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.variant = entity;
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
                    const entity = new Variant();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.variant = entity;
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
