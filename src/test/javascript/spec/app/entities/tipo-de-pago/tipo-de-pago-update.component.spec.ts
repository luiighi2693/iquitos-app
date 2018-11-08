/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDePagoUpdateComponent } from 'app/entities/tipo-de-pago/tipo-de-pago-update.component';
import { TipoDePagoService } from 'app/entities/tipo-de-pago/tipo-de-pago.service';
import { TipoDePago } from 'app/shared/model/tipo-de-pago.model';

describe('Component Tests', () => {
    describe('TipoDePago Management Update Component', () => {
        let comp: TipoDePagoUpdateComponent;
        let fixture: ComponentFixture<TipoDePagoUpdateComponent>;
        let service: TipoDePagoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDePagoUpdateComponent]
            })
                .overrideTemplate(TipoDePagoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDePagoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDePagoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoDePago(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoDePago = entity;
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
                    const entity = new TipoDePago();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoDePago = entity;
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
