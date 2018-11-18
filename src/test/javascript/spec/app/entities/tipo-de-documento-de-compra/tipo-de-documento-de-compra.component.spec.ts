/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeCompraComponent } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra.component';
import { TipoDeDocumentoDeCompraService } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra.service';
import { TipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeCompra Management Component', () => {
        let comp: TipoDeDocumentoDeCompraComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeCompraComponent>;
        let service: TipoDeDocumentoDeCompraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeCompraComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            data: {
                                subscribe: (fn: (value: Data) => void) =>
                                    fn({
                                        pagingParams: {
                                            predicate: 'id',
                                            reverse: false,
                                            page: 0
                                        }
                                    })
                            }
                        }
                    }
                ]
            })
                .overrideTemplate(TipoDeDocumentoDeCompraComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeDocumentoDeCompraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeDocumentoDeCompraService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoDeDocumentoDeCompra(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoDeDocumentoDeCompras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoDeDocumentoDeCompra(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoDeDocumentoDeCompras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoDeDocumentoDeCompra(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);
            comp.reset();

            // THEN
            expect(comp.page).toEqual(0);
            expect(service.query).toHaveBeenCalledTimes(2);
            expect(comp.tipoDeDocumentoDeCompras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should calculate the sort attribute for an id', () => {
            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['id,asc']);
        });

        it('should calculate the sort attribute for a non-id attribute', () => {
            // GIVEN
            comp.predicate = 'name';

            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['name,asc', 'id']);
        });
    });
});
