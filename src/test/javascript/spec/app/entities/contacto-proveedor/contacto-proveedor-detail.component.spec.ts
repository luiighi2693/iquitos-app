/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ContactoProveedorDetailComponent } from 'app/entities/contacto-proveedor/contacto-proveedor-detail.component';
import { ContactoProveedor } from 'app/shared/model/contacto-proveedor.model';

describe('Component Tests', () => {
    describe('ContactoProveedor Management Detail Component', () => {
        let comp: ContactoProveedorDetailComponent;
        let fixture: ComponentFixture<ContactoProveedorDetailComponent>;
        const route = ({ data: of({ contactoProveedor: new ContactoProveedor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ContactoProveedorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactoProveedorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactoProveedorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactoProveedor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
