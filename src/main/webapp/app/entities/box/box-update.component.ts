import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IBox } from 'app/shared/model/box.model';
import { BoxService } from './box.service';

@Component({
    selector: 'jhi-box-update',
    templateUrl: './box-update.component.html'
})
export class BoxUpdateComponent implements OnInit {
    box: IBox;
    isSaving: boolean;

    boxes: IBox[];
    initDateDp: any;
    endDateDp: any;

    constructor(private jhiAlertService: JhiAlertService, private boxService: BoxService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ box }) => {
            this.box = box;
        });
        this.boxService.query({ filter: 'box-is-null' }).subscribe(
            (res: HttpResponse<IBox[]>) => {
                if (!this.box.boxId) {
                    this.boxes = res.body;
                } else {
                    this.boxService.find(this.box.boxId).subscribe(
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
        if (this.box.id !== undefined) {
            this.subscribeToSaveResponse(this.boxService.update(this.box));
        } else {
            this.subscribeToSaveResponse(this.boxService.create(this.box));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBox>>) {
        result.subscribe((res: HttpResponse<IBox>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBoxById(index: number, item: IBox) {
        return item.id;
    }
}
