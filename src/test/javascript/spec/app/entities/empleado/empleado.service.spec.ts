/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmpleadoService } from 'app/entities/empleado/empleado.service';
import { IEmpleado, Empleado, Sex, EmployeeRole } from 'app/shared/model/empleado.model';

describe('Service Tests', () => {
    describe('Empleado Service', () => {
        let injector: TestBed;
        let service: EmpleadoService;
        let httpMock: HttpTestingController;
        let elemDefault: IEmpleado;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EmpleadoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Empleado(
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                Sex.MASCULINO,
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                EmployeeRole.VENDEDOR
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaDeNacimiento: currentDate.format(DATE_FORMAT)
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

            it('should create a Empleado', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaDeNacimiento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaDeNacimiento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Empleado(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Empleado', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombre: 'BBBBBB',
                        apellido: 'BBBBBB',
                        dni: 1,
                        direccion: 'BBBBBB',
                        correo: 'BBBBBB',
                        fechaDeNacimiento: currentDate.format(DATE_FORMAT),
                        sexo: 'BBBBBB',
                        telefono: 'BBBBBB',
                        imagen: 'BBBBBB',
                        rolEmpleado: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaDeNacimiento: currentDate
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

            it('should return a list of Empleado', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombre: 'BBBBBB',
                        apellido: 'BBBBBB',
                        dni: 1,
                        direccion: 'BBBBBB',
                        correo: 'BBBBBB',
                        fechaDeNacimiento: currentDate.format(DATE_FORMAT),
                        sexo: 'BBBBBB',
                        telefono: 'BBBBBB',
                        imagen: 'BBBBBB',
                        rolEmpleado: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaDeNacimiento: currentDate
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

            it('should delete a Empleado', async () => {
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
