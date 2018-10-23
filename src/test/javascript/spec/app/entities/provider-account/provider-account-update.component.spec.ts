/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IquitosAppTestModule } from '../../../test.module';
import { ProviderAccountUpdateComponent } from 'app/entities/provider-account/provider-account-update.component';
import { ProviderAccountService } from 'app/entities/provider-account/provider-account.service';
import { ProviderAccount } from 'app/shared/model/provider-account.model';

describe('Component Tests', () => {
    describe('ProviderAccount Management Update Component', () => {
        let comp: ProviderAccountUpdateComponent;
        let fixture: ComponentFixture<ProviderAccountUpdateComponent>;
        let service: ProviderAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IquitosAppTestModule],
                declarations: [ProviderAccountUpdateComponent]
            })
                .overrideTemplate(ProviderAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProviderAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProviderAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProviderAccount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.providerAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProviderAccount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.providerAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
