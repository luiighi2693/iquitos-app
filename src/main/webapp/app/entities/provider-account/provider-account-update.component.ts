import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IProviderAccount } from 'app/shared/model/provider-account.model';
import { ProviderAccountService } from './provider-account.service';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider';

@Component({
    selector: 'jhi-provider-account-update',
    templateUrl: './provider-account-update.component.html'
})
export class ProviderAccountUpdateComponent implements OnInit {
    providerAccount: IProviderAccount;
    isSaving: boolean;

    providers: IProvider[];
    initDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private providerAccountService: ProviderAccountService,
        private providerService: ProviderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ providerAccount }) => {
            this.providerAccount = providerAccount;
        });
        this.providerService.query().subscribe(
            (res: HttpResponse<IProvider[]>) => {
                this.providers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.providerAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.providerAccountService.update(this.providerAccount));
        } else {
            this.subscribeToSaveResponse(this.providerAccountService.create(this.providerAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProviderAccount>>) {
        result.subscribe((res: HttpResponse<IProviderAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProviderById(index: number, item: IProvider) {
        return item.id;
    }
}
