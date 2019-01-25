/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ParametroSistemaDetailComponent } from 'app/entities/parametro-sistema/parametro-sistema-detail.component';
import { ParametroSistema } from 'app/shared/model/parametro-sistema.model';

describe('Component Tests', () => {
    describe('ParametroSistema Management Detail Component', () => {
        let comp: ParametroSistemaDetailComponent;
        let fixture: ComponentFixture<ParametroSistemaDetailComponent>;
        const route = ({ data: of({ parametroSistema: new ParametroSistema(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ParametroSistemaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParametroSistemaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParametroSistemaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parametroSistema).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
