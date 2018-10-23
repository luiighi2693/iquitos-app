/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { DocumentTypePurchaseUpdateComponent } from 'app/entities/document-type-purchase/document-type-purchase-update.component';
import { DocumentTypePurchaseService } from 'app/entities/document-type-purchase/document-type-purchase.service';
import { DocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';

describe('Component Tests', () => {
    describe('DocumentTypePurchase Management Update Component', () => {
        let comp: DocumentTypePurchaseUpdateComponent;
        let fixture: ComponentFixture<DocumentTypePurchaseUpdateComponent>;
        let service: DocumentTypePurchaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [DocumentTypePurchaseUpdateComponent]
            })
                .overrideTemplate(DocumentTypePurchaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentTypePurchaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTypePurchaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentTypePurchase(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTypePurchase = entity;
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
                    const entity = new DocumentTypePurchase();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTypePurchase = entity;
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
