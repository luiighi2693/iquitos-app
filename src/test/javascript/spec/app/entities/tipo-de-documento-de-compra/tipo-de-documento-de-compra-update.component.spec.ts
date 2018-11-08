/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeCompraUpdateComponent } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra-update.component';
import { TipoDeDocumentoDeCompraService } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra.service';
import { TipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeCompra Management Update Component', () => {
        let comp: TipoDeDocumentoDeCompraUpdateComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeCompraUpdateComponent>;
        let service: TipoDeDocumentoDeCompraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeCompraUpdateComponent]
            })
                .overrideTemplate(TipoDeDocumentoDeCompraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeDocumentoDeCompraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeDocumentoDeCompraService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoDeDocumentoDeCompra(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoDeDocumentoDeCompra = entity;
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
                    const entity = new TipoDeDocumentoDeCompra();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoDeDocumentoDeCompra = entity;
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
