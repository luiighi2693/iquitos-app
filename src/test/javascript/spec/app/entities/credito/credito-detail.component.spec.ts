/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { CreditoDetailComponent } from 'app/entities/credito/credito-detail.component';
import { Credito } from 'app/shared/model/credito.model';

describe('Component Tests', () => {
    describe('Credito Management Detail Component', () => {
        let comp: CreditoDetailComponent;
        let fixture: ComponentFixture<CreditoDetailComponent>;
        const route = ({ data: of({ credito: new Credito(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [CreditoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.credito).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
