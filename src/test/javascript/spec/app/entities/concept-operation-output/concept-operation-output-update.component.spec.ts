/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ConceptOperationOutputUpdateComponent } from 'app/entities/concept-operation-output/concept-operation-output-update.component';
import { ConceptOperationOutputService } from 'app/entities/concept-operation-output/concept-operation-output.service';
import { ConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';

describe('Component Tests', () => {
    describe('ConceptOperationOutput Management Update Component', () => {
        let comp: ConceptOperationOutputUpdateComponent;
        let fixture: ComponentFixture<ConceptOperationOutputUpdateComponent>;
        let service: ConceptOperationOutputService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ConceptOperationOutputUpdateComponent]
            })
                .overrideTemplate(ConceptOperationOutputUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConceptOperationOutputUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConceptOperationOutputService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ConceptOperationOutput(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.conceptOperationOutput = entity;
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
                    const entity = new ConceptOperationOutput();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.conceptOperationOutput = entity;
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
