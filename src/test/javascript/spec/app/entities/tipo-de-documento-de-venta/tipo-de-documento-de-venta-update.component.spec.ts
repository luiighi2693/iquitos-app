/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeVentaUpdateComponent } from 'app/entities/tipo-de-documento-de-venta/tipo-de-documento-de-venta-update.component';
import { TipoDeDocumentoDeVentaService } from 'app/entities/tipo-de-documento-de-venta/tipo-de-documento-de-venta.service';
import { TipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeVenta Management Update Component', () => {
        let comp: TipoDeDocumentoDeVentaUpdateComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeVentaUpdateComponent>;
        let service: TipoDeDocumentoDeVentaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeVentaUpdateComponent]
            })
                .overrideTemplate(TipoDeDocumentoDeVentaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeDocumentoDeVentaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeDocumentoDeVentaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeDocumentoDeVenta(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeDocumentoDeVenta = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeDocumentoDeVenta();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeDocumentoDeVenta = entity;
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
