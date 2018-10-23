/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { DocumentTypeSellDetailComponent } from 'app/entities/document-type-sell/document-type-sell-detail.component';
import { DocumentTypeSell } from 'app/shared/model/document-type-sell.model';

describe('Component Tests', () => {
    describe('DocumentTypeSell Management Detail Component', () => {
        let comp: DocumentTypeSellDetailComponent;
        let fixture: ComponentFixture<DocumentTypeSellDetailComponent>;
        const route = ({ data: of({ documentTypeSell: new DocumentTypeSell(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [DocumentTypeSellDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentTypeSellDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTypeSellDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentTypeSell).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
