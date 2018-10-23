/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { SellDetailComponent } from 'app/entities/sell/sell-detail.component';
import { Sell } from 'app/shared/model/sell.model';

describe('Component Tests', () => {
    describe('Sell Management Detail Component', () => {
        let comp: SellDetailComponent;
        let fixture: ComponentFixture<SellDetailComponent>;
        const route = ({ data: of({ sell: new Sell(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [SellDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SellDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SellDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sell).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
