/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { CuentaProveedorDetailComponent } from 'app/entities/cuenta-proveedor/cuenta-proveedor-detail.component';
import { CuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';

describe('Component Tests', () => {
    describe('CuentaProveedor Management Detail Component', () => {
        let comp: CuentaProveedorDetailComponent;
        let fixture: ComponentFixture<CuentaProveedorDetailComponent>;
        const route = ({ data: of({ cuentaProveedor: new CuentaProveedor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [CuentaProveedorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CuentaProveedorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CuentaProveedorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cuentaProveedor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
