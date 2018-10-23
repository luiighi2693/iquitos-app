import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPurchase } from 'app/shared/model/purchase.model';
import { PurchaseService } from './purchase.service';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider';
import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';
import { DocumentTypePurchaseService } from 'app/entities/document-type-purchase';
import { IPurchaseStatus } from 'app/shared/model/purchase-status.model';
import { PurchaseStatusService } from 'app/entities/purchase-status';
import { IBox } from 'app/shared/model/box.model';
import { BoxService } from 'app/entities/box';

@Component({
    selector: 'jhi-purchase-update',
    templateUrl: './purchase-update.component.html'
})
export class PurchaseUpdateComponent implements OnInit {
    purchase: IPurchase;
    isSaving: boolean;

    providers: IProvider[];

    documenttypepurchases: IDocumentTypePurchase[];

    purchasestatuses: IPurchaseStatus[];

    boxes: IBox[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private purchaseService: PurchaseService,
        private providerService: ProviderService,
        private documentTypePurchaseService: DocumentTypePurchaseService,
        private purchaseStatusService: PurchaseStatusService,
        private boxService: BoxService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchase }) => {
            this.purchase = purchase;
        });
        this.providerService.query({ filter: 'purchase-is-null' }).subscribe(
            (res: HttpResponse<IProvider[]>) => {
                if (!this.purchase.providerId) {
                    this.providers = res.body;
                } else {
                    this.providerService.find(this.purchase.providerId).subscribe(
                        (subRes: HttpResponse<IProvider>) => {
                            this.providers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.documentTypePurchaseService.query({ filter: 'purchase-is-null' }).subscribe(
            (res: HttpResponse<IDocumentTypePurchase[]>) => {
                if (!this.purchase.documentTypePurchaseId) {
                    this.documenttypepurchases = res.body;
                } else {
                    this.documentTypePurchaseService.find(this.purchase.documentTypePurchaseId).subscribe(
                        (subRes: HttpResponse<IDocumentTypePurchase>) => {
                            this.documenttypepurchases = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.purchaseStatusService.query({ filter: 'purchase-is-null' }).subscribe(
            (res: HttpResponse<IPurchaseStatus[]>) => {
                if (!this.purchase.purchaseStatusId) {
                    this.purchasestatuses = res.body;
                } else {
                    this.purchaseStatusService.find(this.purchase.purchaseStatusId).subscribe(
                        (subRes: HttpResponse<IPurchaseStatus>) => {
                            this.purchasestatuses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.boxService.query({ filter: 'purchase-is-null' }).subscribe(
            (res: HttpResponse<IBox[]>) => {
                if (!this.purchase.boxId) {
                    this.boxes = res.body;
                } else {
                    this.boxService.find(this.purchase.boxId).subscribe(
                        (subRes: HttpResponse<IBox>) => {
                            this.boxes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchase.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseService.update(this.purchase));
        } else {
            this.subscribeToSaveResponse(this.purchaseService.create(this.purchase));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPurchase>>) {
        result.subscribe((res: HttpResponse<IPurchase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDocumentTypePurchaseById(index: number, item: IDocumentTypePurchase) {
        return item.id;
    }

    trackPurchaseStatusById(index: number, item: IPurchaseStatus) {
        return item.id;
    }

    trackBoxById(index: number, item: IBox) {
        return item.id;
    }
}
