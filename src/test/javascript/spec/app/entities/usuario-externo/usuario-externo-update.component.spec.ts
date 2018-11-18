/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UsuarioExternoUpdateComponent } from 'app/entities/usuario-externo/usuario-externo-update.component';
import { UsuarioExternoService } from 'app/entities/usuario-externo/usuario-externo.service';
import { UsuarioExterno } from 'app/shared/model/usuario-externo.model';

describe('Component Tests', () => {
    describe('UsuarioExterno Management Update Component', () => {
        let comp: UsuarioExternoUpdateComponent;
        let fixture: ComponentFixture<UsuarioExternoUpdateComponent>;
        let service: UsuarioExternoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UsuarioExternoUpdateComponent]
            })
                .overrideTemplate(UsuarioExternoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UsuarioExternoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsuarioExternoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UsuarioExterno(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.usuarioExterno = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UsuarioExterno();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.usuarioExterno = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
