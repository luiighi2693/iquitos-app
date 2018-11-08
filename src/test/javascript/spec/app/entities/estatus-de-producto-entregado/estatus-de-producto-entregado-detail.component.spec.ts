/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { EstatusDeProductoEntregadoDetailComponent } from 'app/entities/estatus-de-producto-entregado/estatus-de-producto-entregado-detail.component';
import { EstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';

describe('Component Tests', () => {
    describe('EstatusDeProductoEntregado Management Detail Component', () => {
        let comp: EstatusDeProductoEntregadoDetailComponent;
        let fixture: ComponentFixture<EstatusDeProductoEntregadoDetailComponent>;
        const route = ({ data: of({ estatusDeProductoEntregado: new EstatusDeProductoEntregado(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [EstatusDeProductoEntregadoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstatusDeProductoEntregadoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstatusDeProductoEntregadoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estatusDeProductoEntregado).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
