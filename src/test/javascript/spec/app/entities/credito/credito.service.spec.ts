/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CreditoService } from 'app/entities/credito/credito.service';
import { ICredito, Credito } from 'app/shared/model/credito.model';

describe('Service Tests', () => {
    describe('Credito Service', () => {
        let injector: TestBed;
        let service: CreditoService;
        let httpMock: HttpTestingController;
        let elemDefault: ICredito;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CreditoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Credito(0, 0, currentDate, 0, 0, 0, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fecha: currentDate.format(DATE_FORMAT),
                        fechaLimite: currentDate.format(DATE_FORMAT)
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

            it('should create a Credito', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fecha: currentDate.format(DATE_FORMAT),
                        fechaLimite: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fecha: currentDate,
                        fechaLimite: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Credito(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Credito', async () => {
                const returnedFromService = Object.assign(
                    {
                        monto: 1,
                        fecha: currentDate.format(DATE_FORMAT),
                        modoDePago: 1,
                        numero: 1,
                        montoTotal: 1,
                        fechaLimite: currentDate.format(DATE_FORMAT),
                        notaDeCredito: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fecha: currentDate,
                        fechaLimite: currentDate
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

            it('should return a list of Credito', async () => {
                const returnedFromService = Object.assign(
                    {
                        monto: 1,
                        fecha: currentDate.format(DATE_FORMAT),
                        modoDePago: 1,
                        numero: 1,
                        montoTotal: 1,
                        fechaLimite: currentDate.format(DATE_FORMAT),
                        notaDeCredito: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fecha: currentDate,
                        fechaLimite: currentDate
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

            it('should delete a Credito', async () => {
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
