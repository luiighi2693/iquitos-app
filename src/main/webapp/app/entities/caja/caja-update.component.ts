import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from './caja.service';

@Component({
    selector: 'jhi-caja-update',
    templateUrl: './caja-update.component.html'
})
export class CajaUpdateComponent implements OnInit {
    caja: ICaja;
    isSaving: boolean;

    cajas: ICaja[];
    fechaInicialDp: any;
    fechaFinalDp: any;

    constructor(private jhiAlertService: JhiAlertService, private cajaService: CajaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caja }) => {
            this.caja = caja;
        });
        this.cajaService.query({ filter: 'caja-is-null' }).subscribe(
            (res: HttpResponse<ICaja[]>) => {
                if (!this.caja.cajaId) {
                    this.cajas = res.body;
                } else {
                    this.cajaService.find(this.caja.cajaId).subscribe(
                        (subRes: HttpResponse<ICaja>) => {
                            this.cajas = [subRes.body].concat(res.body);
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
        if (this.caja.id !== undefined) {
            this.subscribeToSaveResponse(this.cajaService.update(this.caja));
        } else {
            this.subscribeToSaveResponse(this.cajaService.create(this.caja));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICaja>>) {
        result.subscribe((res: HttpResponse<ICaja>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCajaById(index: number, item: ICaja) {
        return item.id;
    }
}
