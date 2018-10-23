import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from './provider.service';

@Component({
    selector: 'jhi-provider-update',
    templateUrl: './provider-update.component.html'
})
export class ProviderUpdateComponent implements OnInit {
    provider: IProvider;
    isSaving: boolean;

    constructor(private providerService: ProviderService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ provider }) => {
            this.provider = provider;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.provider.id !== undefined) {
            this.subscribeToSaveResponse(this.providerService.update(this.provider));
        } else {
            this.subscribeToSaveResponse(this.providerService.create(this.provider));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProvider>>) {
        result.subscribe((res: HttpResponse<IProvider>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
