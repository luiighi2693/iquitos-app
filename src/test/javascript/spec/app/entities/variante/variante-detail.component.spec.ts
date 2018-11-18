/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { VarianteDetailComponent } from 'app/entities/variante/variante-detail.component';
import { Variante } from 'app/shared/model/variante.model';

describe('Component Tests', () => {
    describe('Variante Management Detail Component', () => {
        let comp: VarianteDetailComponent;
        let fixture: ComponentFixture<VarianteDetailComponent>;
        const route = ({ data: of({ variante: new Variante(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [VarianteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VarianteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VarianteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.variante).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
