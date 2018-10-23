import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IVariant } from 'app/shared/model/variant.model';
import { VariantService } from './variant.service';

@Component({
    selector: 'jhi-variant-update',
    templateUrl: './variant-update.component.html'
})
export class VariantUpdateComponent implements OnInit {
    variant: IVariant;
    isSaving: boolean;

    constructor(private variantService: VariantService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ variant }) => {
            this.variant = variant;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.variant.id !== undefined) {
            this.subscribeToSaveResponse(this.variantService.update(this.variant));
        } else {
            this.subscribeToSaveResponse(this.variantService.create(this.variant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVariant>>) {
        result.subscribe((res: HttpResponse<IVariant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
