/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ConceptOperationInputUpdateComponent } from 'app/entities/concept-operation-input/concept-operation-input-update.component';
import { ConceptOperationInputService } from 'app/entities/concept-operation-input/concept-operation-input.service';
import { ConceptOperationInput } from 'app/shared/model/concept-operation-input.model';

describe('Component Tests', () => {
    describe('ConceptOperationInput Management Update Component', () => {
        let comp: ConceptOperationInputUpdateComponent;
        let fixture: ComponentFixture<ConceptOperationInputUpdateComponent>;
        let service: ConceptOperationInputService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ConceptOperationInputUpdateComponent]
            })
                .overrideTemplate(ConceptOperationInputUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConceptOperationInputUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConceptOperationInputService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ConceptOperationInput(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.conceptOperationInput = entity;
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
                    const entity = new ConceptOperationInput();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.conceptOperationInput = entity;
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
