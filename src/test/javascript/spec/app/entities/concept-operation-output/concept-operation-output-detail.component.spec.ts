/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ConceptOperationOutputDetailComponent } from 'app/entities/concept-operation-output/concept-operation-output-detail.component';
import { ConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';

describe('Component Tests', () => {
    describe('ConceptOperationOutput Management Detail Component', () => {
        let comp: ConceptOperationOutputDetailComponent;
        let fixture: ComponentFixture<ConceptOperationOutputDetailComponent>;
        const route = ({ data: of({ conceptOperationOutput: new ConceptOperationOutput(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ConceptOperationOutputDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConceptOperationOutputDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConceptOperationOutputDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.conceptOperationOutput).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
