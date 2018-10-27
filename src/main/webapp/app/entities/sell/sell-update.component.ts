import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ISell } from 'app/shared/model/sell.model';
import { SellService } from './sell.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IBox } from 'app/shared/model/box.model';
import { BoxService } from 'app/entities/box';
import { IDocumentTypeSell } from 'app/shared/model/document-type-sell.model';
import { DocumentTypeSellService } from 'app/entities/document-type-sell';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from 'app/entities/payment-type';
import { IProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';
import { ProductsDeliveredStatusService } from 'app/entities/products-delivered-status';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-sell-update',
    templateUrl: './sell-update.component.html'
})
export class SellUpdateComponent implements OnInit {
    sell: ISell;
    isSaving: boolean;

    clients: IClient[];

    employees: IEmployee[];

    boxes: IBox[];

    documenttypesells: IDocumentTypeSell[];

    paymenttypes: IPaymentType[];

    productsdeliveredstatuses: IProductsDeliveredStatus[];

    products: IProduct[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private sellService: SellService,
        private clientService: ClientService,
        private employeeService: EmployeeService,
        private boxService: BoxService,
        private documentTypeSellService: DocumentTypeSellService,
        private paymentTypeService: PaymentTypeService,
        private productsDeliveredStatusService: ProductsDeliveredStatusService,
        private productService: ProductService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sell }) => {
            this.sell = sell;
        });
        this.clientService.query({ filter: 'sell-is-null' }).subscribe(
            (res: HttpResponse<IClient[]>) => {
                if (!this.sell.clientId) {
                    this.clients = res.body;
                } else {
                    this.clientService.find(this.sell.clientId).subscribe(
                        (subRes: HttpResponse<IClient>) => {
                            this.clients = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeeService.query({ filter: 'sell-is-null' }).subscribe(
            (res: HttpResponse<IEmployee[]>) => {
                if (!this.sell.employeeId) {
                    this.employees = res.body;
                } else {
                    this.employeeService.find(this.sell.employeeId).subscribe(
                        (subRes: HttpResponse<IEmployee>) => {
                            this.employees = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.boxService.query({ filter: 'sell-is-null' }).subscribe(
            (res: HttpResponse<IBox[]>) => {
                if (!this.sell.boxId) {
                    this.boxes = res.body;
                } else {
                    this.boxService.find(this.sell.boxId).subscribe(
                        (subRes: HttpResponse<IBox>) => {
                            this.boxes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.documentTypeSellService.query().subscribe(
            (res: HttpResponse<IDocumentTypeSell[]>) => {
                this.documenttypesells = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentTypeService.query().subscribe(
            (res: HttpResponse<IPaymentType[]>) => {
                this.paymenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productsDeliveredStatusService.query().subscribe(
            (res: HttpResponse<IProductsDeliveredStatus[]>) => {
                this.productsdeliveredstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sell.id !== undefined) {
            this.subscribeToSaveResponse(this.sellService.update(this.sell));
        } else {
            this.subscribeToSaveResponse(this.sellService.create(this.sell));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISell>>) {
        result.subscribe((res: HttpResponse<ISell>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClientById(index: number, item: IClient) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackBoxById(index: number, item: IBox) {
        return item.id;
    }

    trackDocumentTypeSellById(index: number, item: IDocumentTypeSell) {
        return item.id;
    }

    trackPaymentTypeById(index: number, item: IPaymentType) {
        return item.id;
    }

    trackProductsDeliveredStatusById(index: number, item: IProductsDeliveredStatus) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
