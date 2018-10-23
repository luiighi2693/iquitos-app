/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { VariantDetailComponent } from 'app/entities/variant/variant-detail.component';
import { Variant } from 'app/shared/model/variant.model';

describe('Component Tests', () => {
    describe('Variant Management Detail Component', () => {
        let comp: VariantDetailComponent;
        let fixture: ComponentFixture<VariantDetailComponent>;
        const route = ({ data: of({ variant: new Variant(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [VariantDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VariantDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VariantDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.variant).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
