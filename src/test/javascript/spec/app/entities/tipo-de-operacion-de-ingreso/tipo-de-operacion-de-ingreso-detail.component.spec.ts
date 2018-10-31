/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeOperacionDeIngresoDetailComponent } from 'app/entities/tipo-de-operacion-de-ingreso/tipo-de-operacion-de-ingreso-detail.component';
import { TipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';

describe('Component Tests', () => {
    describe('TipoDeOperacionDeIngreso Management Detail Component', () => {
        let comp: TipoDeOperacionDeIngresoDetailComponent;
        let fixture: ComponentFixture<TipoDeOperacionDeIngresoDetailComponent>;
        const route = ({ data: of({ tipoDeOperacionDeIngreso: new TipoDeOperacionDeIngreso(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeOperacionDeIngresoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDeOperacionDeIngresoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeOperacionDeIngresoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDeOperacionDeIngreso).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
