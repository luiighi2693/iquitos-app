/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { EstatusDeCompraDetailComponent } from 'app/entities/estatus-de-compra/estatus-de-compra-detail.component';
import { EstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';

describe('Component Tests', () => {
    describe('EstatusDeCompra Management Detail Component', () => {
        let comp: EstatusDeCompraDetailComponent;
        let fixture: ComponentFixture<EstatusDeCompraDetailComponent>;
        const route = ({ data: of({ estatusDeCompra: new EstatusDeCompra(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [EstatusDeCompraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstatusDeCompraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstatusDeCompraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estatusDeCompra).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
