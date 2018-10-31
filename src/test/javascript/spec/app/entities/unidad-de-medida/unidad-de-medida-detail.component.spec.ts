/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UnidadDeMedidaDetailComponent } from 'app/entities/unidad-de-medida/unidad-de-medida-detail.component';
import { UnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';

describe('Component Tests', () => {
    describe('UnidadDeMedida Management Detail Component', () => {
        let comp: UnidadDeMedidaDetailComponent;
        let fixture: ComponentFixture<UnidadDeMedidaDetailComponent>;
        const route = ({ data: of({ unidadDeMedida: new UnidadDeMedida(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UnidadDeMedidaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UnidadDeMedidaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnidadDeMedidaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.unidadDeMedida).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
