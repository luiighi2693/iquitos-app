/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PurchaseStatusUpdateComponent } from 'app/entities/purchase-status/purchase-status-update.component';
import { PurchaseStatusService } from 'app/entities/purchase-status/purchase-status.service';
import { PurchaseStatus } from 'app/shared/model/purchase-status.model';

describe('Component Tests', () => {
    describe('PurchaseStatus Management Update Component', () => {
        let comp: PurchaseStatusUpdateComponent;
        let fixture: ComponentFixture<PurchaseStatusUpdateComponent>;
        let service: PurchaseStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PurchaseStatusUpdateComponent]
            })
                .overrideTemplate(PurchaseStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseStatusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PurchaseStatus(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseStatus = entity;
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
                    const entity = new PurchaseStatus();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseStatus = entity;
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
