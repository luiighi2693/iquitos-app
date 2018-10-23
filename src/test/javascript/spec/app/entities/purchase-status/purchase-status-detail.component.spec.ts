/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { PurchaseStatusDetailComponent } from 'app/entities/purchase-status/purchase-status-detail.component';
import { PurchaseStatus } from 'app/shared/model/purchase-status.model';

describe('Component Tests', () => {
    describe('PurchaseStatus Management Detail Component', () => {
        let comp: PurchaseStatusDetailComponent;
        let fixture: ComponentFixture<PurchaseStatusDetailComponent>;
        const route = ({ data: of({ purchaseStatus: new PurchaseStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [PurchaseStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchaseStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchaseStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
