/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ConceptOperationInputDetailComponent } from 'app/entities/concept-operation-input/concept-operation-input-detail.component';
import { ConceptOperationInput } from 'app/shared/model/concept-operation-input.model';

describe('Component Tests', () => {
    describe('ConceptOperationInput Management Detail Component', () => {
        let comp: ConceptOperationInputDetailComponent;
        let fixture: ComponentFixture<ConceptOperationInputDetailComponent>;
        const route = ({ data: of({ conceptOperationInput: new ConceptOperationInput(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ConceptOperationInputDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConceptOperationInputDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConceptOperationInputDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.conceptOperationInput).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
