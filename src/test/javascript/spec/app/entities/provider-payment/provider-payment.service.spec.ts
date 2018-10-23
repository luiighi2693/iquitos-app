/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProviderPaymentService } from 'app/entities/provider-payment/provider-payment.service';
import { IProviderPayment, ProviderPayment } from 'app/shared/model/provider-payment.model';

describe('Service Tests', () => {
    describe('ProviderPayment Service', () => {
        let injector: TestBed;
        let service: ProviderPaymentService;
        let httpMock: HttpTestingController;
        let elemDefault: IProviderPayment;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProviderPaymentService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ProviderPayment(0, 0, 0, currentDate, 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        emissionDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ProviderPayment', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        emissionDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        emissionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ProviderPayment(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProviderPayment', async () => {
                const returnedFromService = Object.assign(
                    {
                        amountToPay: 1,
                        amountPayed: 1,
                        emissionDate: currentDate.format(DATE_FORMAT),
                        documentCode: 'BBBBBB',
                        glosa: 'BBBBBB',
                        image: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        emissionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ProviderPayment', async () => {
                const returnedFromService = Object.assign(
                    {
                        amountToPay: 1,
                        amountPayed: 1,
                        emissionDate: currentDate.format(DATE_FORMAT),
                        documentCode: 'BBBBBB',
                        glosa: 'BBBBBB',
                        image: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        emissionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ProviderPayment', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
