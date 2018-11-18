/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { EstatusDeCompraUpdateComponent } from 'app/entities/estatus-de-compra/estatus-de-compra-update.component';
import { EstatusDeCompraService } from 'app/entities/estatus-de-compra/estatus-de-compra.service';
import { EstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';

describe('Component Tests', () => {
    describe('EstatusDeCompra Management Update Component', () => {
        let comp: EstatusDeCompraUpdateComponent;
        let fixture: ComponentFixture<EstatusDeCompraUpdateComponent>;
        let service: EstatusDeCompraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [EstatusDeCompraUpdateComponent]
            })
                .overrideTemplate(EstatusDeCompraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstatusDeCompraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstatusDeCompraService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EstatusDeCompra(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.estatusDeCompra = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EstatusDeCompra();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.estatusDeCompra = entity;
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
