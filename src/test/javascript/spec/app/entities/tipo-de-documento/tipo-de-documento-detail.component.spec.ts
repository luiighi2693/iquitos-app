/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { TipoDeDocumentoDetailComponent } from 'app/entities/tipo-de-documento/tipo-de-documento-detail.component';
import { TipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';

describe('Component Tests', () => {
    describe('TipoDeDocumento Management Detail Component', () => {
        let comp: TipoDeDocumentoDetailComponent;
        let fixture: ComponentFixture<TipoDeDocumentoDetailComponent>;
        const route = ({ data: of({ tipoDeDocumento: new TipoDeDocumento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [TipoDeDocumentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDeDocumentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeDocumentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDeDocumento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
