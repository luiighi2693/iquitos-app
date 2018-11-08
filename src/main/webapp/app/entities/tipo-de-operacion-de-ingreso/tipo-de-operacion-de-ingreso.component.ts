import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TipoDeOperacionDeIngresoService } from './tipo-de-operacion-de-ingreso.service';

@Component({
    selector: 'jhi-tipo-de-operacion-de-ingreso',
    templateUrl: './tipo-de-operacion-de-ingreso.component.html'
})
export class TipoDeOperacionDeIngresoComponent implements OnInit, OnDestroy {
    tipoDeOperacionDeIngresos: ITipoDeOperacionDeIngreso[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    constructor(
        private tipoDeOperacionDeIngresoService: TipoDeOperacionDeIngresoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.tipoDeOperacionDeIngresos = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.tipoDeOperacionDeIngresoService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ITipoDeOperacionDeIngreso[]>) => this.paginateTipoDeOperacionDeIngresos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.tipoDeOperacionDeIngresoService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ITipoDeOperacionDeIngreso[]>) => this.paginateTipoDeOperacionDeIngresos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.tipoDeOperacionDeIngresos = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.tipoDeOperacionDeIngresos = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.tipoDeOperacionDeIngresos = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = '_score';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoDeOperacionDeIngresos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITipoDeOperacionDeIngreso) {
        return item.id;
    }

    registerChangeInTipoDeOperacionDeIngresos() {
        this.eventSubscriber = this.eventManager.subscribe('tipoDeOperacionDeIngresoListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateTipoDeOperacionDeIngresos(data: ITipoDeOperacionDeIngreso[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.tipoDeOperacionDeIngresos.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
