/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProviderAccountService } from 'app/entities/provider-account/provider-account.service';
import { IProviderAccount, ProviderAccount, ProviderStatus } from 'app/shared/model/provider-account.model';

describe('Service Tests', () => {
    describe('ProviderAccount Service', () => {
        let injector: TestBed;
        let service: ProviderAccountService;
        let httpMock: HttpTestingController;
        let elemDefault: IProviderAccount;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProviderAccountService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ProviderAccount(0, 'AAAAAAA', ProviderStatus.ACTIVO, 'AAAAAAA', 'AAAAAAA', 0, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        initDate: currentDate.format(DATE_FORMAT)
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

            it('should create a ProviderAccount', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        initDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        initDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ProviderAccount(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProviderAccount', async () => {
                const returnedFromService = Object.assign(
                    {
                        code: 'BBBBBB',
                        status: 'BBBBBB',
                        bank: 'BBBBBB',
                        accountName: 'BBBBBB',
                        accountNumber: 1,
                        initDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        initDate: currentDate
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

            it('should return a list of ProviderAccount', async () => {
                const returnedFromService = Object.assign(
                    {
                        code: 'BBBBBB',
                        status: 'BBBBBB',
                        bank: 'BBBBBB',
                        accountName: 'BBBBBB',
                        accountNumber: 1,
                        initDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        initDate: currentDate
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

            it('should delete a ProviderAccount', async () => {
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
