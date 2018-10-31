/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeVentaDetailComponent } from 'app/entities/tipo-de-documento-de-venta/tipo-de-documento-de-venta-detail.component';
import { TipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeVenta Management Detail Component', () => {
        let comp: TipoDeDocumentoDeVentaDetailComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeVentaDetailComponent>;
        const route = ({ data: of({ tipoDeDocumentoDeVenta: new TipoDeDocumentoDeVenta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeVentaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDeDocumentoDeVentaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeDocumentoDeVentaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDeDocumentoDeVenta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
