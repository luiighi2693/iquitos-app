/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CajaService } from 'app/entities/caja/caja.service';
import { ICaja, Caja } from 'app/shared/model/caja.model';

describe('Service Tests', () => {
    describe('Caja Service', () => {
        let injector: TestBed;
        let service: CajaService;
        let httpMock: HttpTestingController;
        let elemDefault: ICaja;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CajaService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Caja(0, 0, 0, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaInicial: currentDate.format(DATE_FORMAT),
                        fechaFinal: currentDate.format(DATE_FORMAT)
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

            it('should create a Caja', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaInicial: currentDate.format(DATE_FORMAT),
                        fechaFinal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaInicial: currentDate,
                        fechaFinal: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Caja(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Caja', async () => {
                const returnedFromService = Object.assign(
                    {
                        monto: 1,
                        montoActual: 1,
                        fechaInicial: currentDate.format(DATE_FORMAT),
                        fechaFinal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaInicial: currentDate,
                        fechaFinal: currentDate
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

            it('should return a list of Caja', async () => {
                const returnedFromService = Object.assign(
                    {
                        monto: 1,
                        montoActual: 1,
                        fechaInicial: currentDate.format(DATE_FORMAT),
                        fechaFinal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaInicial: currentDate,
                        fechaFinal: currentDate
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

            it('should delete a Caja', async () => {
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
