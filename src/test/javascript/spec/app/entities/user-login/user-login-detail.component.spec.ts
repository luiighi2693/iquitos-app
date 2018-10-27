/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { UserLoginDetailComponent } from 'app/entities/user-login/user-login-detail.component';
import { UserLogin } from 'app/shared/model/user-login.model';

describe('Component Tests', () => {
    describe('UserLogin Management Detail Component', () => {
        let comp: UserLoginDetailComponent;
        let fixture: ComponentFixture<UserLoginDetailComponent>;
        const route = ({ data: of({ userLogin: new UserLogin(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [UserLoginDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserLoginDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserLoginDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userLogin).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
