/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PagoDeProveedorDetailComponent } from 'app/entities/pago-de-proveedor/pago-de-proveedor-detail.component';
import { PagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';

describe('Component Tests', () => {
    describe('PagoDeProveedor Management Detail Component', () => {
        let comp: PagoDeProveedorDetailComponent;
        let fixture: ComponentFixture<PagoDeProveedorDetailComponent>;
        const route = ({ data: of({ pagoDeProveedor: new PagoDeProveedor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PagoDeProveedorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PagoDeProveedorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PagoDeProveedorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pagoDeProveedor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
