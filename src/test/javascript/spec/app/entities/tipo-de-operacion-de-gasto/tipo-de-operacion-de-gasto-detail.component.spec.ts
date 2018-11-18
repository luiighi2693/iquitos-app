/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeOperacionDeGastoDetailComponent } from 'app/entities/tipo-de-operacion-de-gasto/tipo-de-operacion-de-gasto-detail.component';
import { TipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';

describe('Component Tests', () => {
    describe('TipoDeOperacionDeGasto Management Detail Component', () => {
        let comp: TipoDeOperacionDeGastoDetailComponent;
        let fixture: ComponentFixture<TipoDeOperacionDeGastoDetailComponent>;
        const route = ({ data: of({ tipoDeOperacionDeGasto: new TipoDeOperacionDeGasto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeOperacionDeGastoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDeOperacionDeGastoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeOperacionDeGastoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDeOperacionDeGasto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
