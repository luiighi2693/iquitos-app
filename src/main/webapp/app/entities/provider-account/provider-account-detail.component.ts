import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProviderAccount } from 'app/shared/model/provider-account.model';

@Component({
    selector: 'jhi-provider-account-detail',
    templateUrl: './provider-account-detail.component.html'
})
export class ProviderAccountDetailComponent implements OnInit {
    providerAccount: IProviderAccount;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providerAccount }) => {
            this.providerAccount = providerAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
