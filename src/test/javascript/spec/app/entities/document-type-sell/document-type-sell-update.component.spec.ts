/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { DocumentTypeSellUpdateComponent } from 'app/entities/document-type-sell/document-type-sell-update.component';
import { DocumentTypeSellService } from 'app/entities/document-type-sell/document-type-sell.service';
import { DocumentTypeSell } from 'app/shared/model/document-type-sell.model';

describe('Component Tests', () => {
    describe('DocumentTypeSell Management Update Component', () => {
        let comp: DocumentTypeSellUpdateComponent;
        let fixture: ComponentFixture<DocumentTypeSellUpdateComponent>;
        let service: DocumentTypeSellService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [DocumentTypeSellUpdateComponent]
            })
                .overrideTemplate(DocumentTypeSellUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentTypeSellUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTypeSellService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentTypeSell(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTypeSell = entity;
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
                    const entity = new DocumentTypeSell();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTypeSell = entity;
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
