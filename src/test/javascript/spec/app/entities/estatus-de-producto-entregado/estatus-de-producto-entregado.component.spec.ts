/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { IquitosAppTestModule } from '../../../test.module';
import { EstatusDeProductoEntregadoComponent } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado.component';
import { EstatusDeProductoEntregadoService } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado.service';
import { EstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';

describe('Component Tests', () => {
    describe('EstatusDeProductoEntregado Management Component', () => {
        let comp: EstatusDeProductoEntregadoComponent;
        let fixture: ComponentFixture<EstatusDeProductoEntregadoComponent>;
        let service: EstatusDeProductoEntregadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [EstatusDeProductoEntregadoComponent],
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
                .overrideTemplate(EstatusDeProductoEntregadoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstatusDeProductoEntregadoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstatusDeProductoEntregadoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EstatusDeProductoEntregado(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.estatusDeProductoEntregados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EstatusDeProductoEntregado(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.estatusDeProductoEntregados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EstatusDeProductoEntregado(123)],
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
            expect(comp.estatusDeProductoEntregados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
