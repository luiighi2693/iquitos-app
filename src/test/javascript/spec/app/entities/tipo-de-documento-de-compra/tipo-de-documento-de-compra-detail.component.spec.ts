/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDeCompraDetailComponent } from 'app/entities/tipo-de-documento-de-compra/tipo-de-documento-de-compra-detail.component';
import { TipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';

describe('Component Tests', () => {
    describe('TipoDeDocumentoDeCompra Management Detail Component', () => {
        let comp: TipoDeDocumentoDeCompraDetailComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDeCompraDetailComponent>;
        const route = ({ data: of({ tipoDeDocumentoDeCompra: new TipoDeDocumentoDeCompra(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDeCompraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDeDocumentoDeCompraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeDocumentoDeCompraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDeDocumentoDeCompra).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
