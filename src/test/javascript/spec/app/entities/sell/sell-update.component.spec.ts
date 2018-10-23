/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { SellUpdateComponent } from 'app/entities/sell/sell-update.component';
import { SellService } from 'app/entities/sell/sell.service';
import { Sell } from 'app/shared/model/sell.model';

describe('Component Tests', () => {
    describe('Sell Management Update Component', () => {
        let comp: SellUpdateComponent;
        let fixture: ComponentFixture<SellUpdateComponent>;
        let service: SellService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [SellUpdateComponent]
            })
                .overrideTemplate(SellUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SellUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SellService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sell(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sell = entity;
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
                    const entity = new Sell();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sell = entity;
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
