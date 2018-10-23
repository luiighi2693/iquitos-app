/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { DocumentTypePurchaseDetailComponent } from 'app/entities/document-type-purchase/document-type-purchase-detail.component';
import { DocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';

describe('Component Tests', () => {
    describe('DocumentTypePurchase Management Detail Component', () => {
        let comp: DocumentTypePurchaseDetailComponent;
        let fixture: ComponentFixture<DocumentTypePurchaseDetailComponent>;
        const route = ({ data: of({ documentTypePurchase: new DocumentTypePurchase(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [DocumentTypePurchaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentTypePurchaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTypePurchaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentTypePurchase).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
