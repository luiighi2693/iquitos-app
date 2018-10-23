import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentTypeSell } from 'app/shared/model/document-type-sell.model';

@Component({
    selector: 'jhi-document-type-sell-detail',
    templateUrl: './document-type-sell-detail.component.html'
})
export class DocumentTypeSellDetailComponent implements OnInit {
    documentTypeSell: IDocumentTypeSell;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTypeSell }) => {
            this.documentTypeSell = documentTypeSell;
        });
    }

    previousState() {
        window.history.back();
    }
}
