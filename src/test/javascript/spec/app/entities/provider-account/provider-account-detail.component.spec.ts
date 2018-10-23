/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProviderAccountDetailComponent } from 'app/entities/provider-account/provider-account-detail.component';
import { ProviderAccount } from 'app/shared/model/provider-account.model';

describe('Component Tests', () => {
    describe('ProviderAccount Management Detail Component', () => {
        let comp: ProviderAccountDetailComponent;
        let fixture: ComponentFixture<ProviderAccountDetailComponent>;
        const route = ({ data: of({ providerAccount: new ProviderAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProviderAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProviderAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProviderAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.providerAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
