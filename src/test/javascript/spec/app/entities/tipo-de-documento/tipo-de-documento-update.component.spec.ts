/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoUpdateComponent } from 'app/entities/tipo-de-documento/tipo-de-documento-update.component';
import { TipoDeDocumentoService } from 'app/entities/tipo-de-documento/tipo-de-documento.service';
import { TipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';

describe('Component Tests', () => {
    describe('TipoDeDocumento Management Update Component', () => {
        let comp: TipoDeDocumentoUpdateComponent;
        let fixture: ComponentFixture<TipoDeDocumentoUpdateComponent>;
        let service: TipoDeDocumentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoUpdateComponent]
            })
                .overrideTemplate(TipoDeDocumentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeDocumentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeDocumentoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeDocumento(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeDocumento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeDocumento();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeDocumento = entity;
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
