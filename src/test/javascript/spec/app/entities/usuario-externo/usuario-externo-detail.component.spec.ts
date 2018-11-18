/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UsuarioExternoDetailComponent } from 'app/entities/usuario-externo/usuario-externo-detail.component';
import { UsuarioExterno } from 'app/shared/model/usuario-externo.model';

describe('Component Tests', () => {
    describe('UsuarioExterno Management Detail Component', () => {
        let comp: UsuarioExternoDetailComponent;
        let fixture: ComponentFixture<UsuarioExternoDetailComponent>;
        const route = ({ data: of({ usuarioExterno: new UsuarioExterno(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UsuarioExternoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UsuarioExternoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UsuarioExternoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.usuarioExterno).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
