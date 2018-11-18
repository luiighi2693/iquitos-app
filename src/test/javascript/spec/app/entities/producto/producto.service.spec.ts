/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IProducto, Producto, ProductType } from 'app/shared/model/producto.model';

describe('Service Tests', () => {
    describe('Producto Service', () => {
        let injector: TestBed;
        let service: ProductoService;
        let httpMock: HttpTestingController;
        let elemDefault: IProducto;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProductoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Producto(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                false,
                false,
                'image/png',
                'AAAAAAA',
                0,
                0,
                ProductType.BIENES
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaExpiracion: currentDate.format(DATE_FORMAT)
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

            it('should create a Producto', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaExpiracion: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaExpiracion: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Producto(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Producto', async () => {
                const returnedFromService = Object.assign(
                    {
                        codigo: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        fechaExpiracion: currentDate.format(DATE_FORMAT),
                        esFavorito: true,
                        visibleParaLaVenta: true,
                        imagen: 'BBBBBB',
                        stock: 1,
                        notificacionDeLimiteDeStock: 1,
                        tipoDeProducto: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaExpiracion: currentDate
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

            it('should return a list of Producto', async () => {
                const returnedFromService = Object.assign(
                    {
                        codigo: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        fechaExpiracion: currentDate.format(DATE_FORMAT),
                        esFavorito: true,
                        visibleParaLaVenta: true,
                        imagen: 'BBBBBB',
                        stock: 1,
                        notificacionDeLimiteDeStock: 1,
                        tipoDeProducto: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaExpiracion: currentDate
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

            it('should delete a Producto', async () => {
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
