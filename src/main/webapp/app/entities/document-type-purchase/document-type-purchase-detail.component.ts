import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';

@Component({
    selector: 'jhi-document-type-purchase-detail',
    templateUrl: './document-type-purchase-detail.component.html'
})
export class DocumentTypePurchaseDetailComponent implements OnInit {
    documentTypePurchase: IDocumentTypePurchase;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTypePurchase }) => {
            this.documentTypePurchase = documentTypePurchase;
        });
    }

    previousState() {
        window.history.back();
    }
}
