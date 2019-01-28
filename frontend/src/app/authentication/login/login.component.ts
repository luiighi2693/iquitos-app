import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from '@angular/forms';
import {AuthenticationService} from '../../shared/util/authentication.service';
import {UsuarioExterno} from '../../models/usuario-externo.model';
import {finalize} from 'rxjs/operators';
import {UsuarioExternoService} from './usuario-externo.service';
import Util from "../../shared/util/util";
import {AmortizacionService} from "../../ventas/amortizacion.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {IEmpleado} from "../../models/empleado.model";
import {IAmortizacion} from "../../models/amortizacion.model";
import {ITipoDeDocumentoDeVenta} from "../../models/tipo-de-documento-de-venta.model";
import {TipoDeDocumentoDeVentaService} from "../../configuration/documenttypesell/tipo-de-documento-de-venta.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public form: FormGroup;
  error: string;
  isLoading = false;
  user = UsuarioExterno;
  hasError = false;
  constructor(private fb: FormBuilder, private router: Router,
              private authenticationService: AuthenticationService,
              private usuarioExternoService: UsuarioExternoService,
              private amortizacionService: AmortizacionService,
              private tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService) {}

  ngOnInit() {
    this.authenticationService.logout();

    this.form = this.fb.group({
      uname: [null, Validators.compose([Validators.required, Validators.minLength(8)])],
      password: [null, Validators.compose([Validators.required, Validators.minLength(4)])]
    });
  }

  login() {
    if (!this.form.invalid) {
      this.isLoading = true;

      this.usuarioExternoService.findByDni(this.form.controls['uname'].value).subscribe(response => {
          if (response.body.pin.toString() === this.form.controls['password'].value) {
            this.authenticationService
              .login(this.form.value)
              .pipe(
                finalize(() => {
                  this.form.markAsPristine();
                  this.isLoading = false;
                })
              )
              .subscribe(
                credentials => {
                  this.tipoDeDocumentoDeVentaService.query().subscribe(
                    (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => {
                      let tiposDocumento = res.body;
                      tiposDocumento.forEach(tipo => {
                        sessionStorage.setItem('correlativo.' + tipo.nombre, tipo.nombre[0]+'00');
                      });

                      this.amortizacionService.query().subscribe(
                        (res: HttpResponse<IAmortizacion[]>) => {
                          if (res.body.length === 0) {
                            tiposDocumento.forEach(tipo => {
                              sessionStorage.setItem('correlativo.contador.'+tipo.nombre, '0');
                            });
                          } else {
                            tiposDocumento.forEach(tipo => {
                              sessionStorage.setItem('correlativo.contador.'+tipo.nombre, res.body.filter(x => x.tipoDeDocumentoDeVentaNombre === tipo.nombre).length.toString());
                            });
                          }

                          this.router.navigate(['/dashboards'], { replaceUrl: true });
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                      );
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                  );
                },
                error => {
                  this.error = error;
                }
              );
          } else {
            this.hasError = true;
          }
        },
        error => {
          this.error = error;
          this.hasError = true;
        });
    }
  }

  checkNumbersOnly(event: any): boolean {
    return Util.checkNumbersOnly(event);
  }

  checkCharactersOnly(event: any): boolean {
    return Util.checkCharactersOnly(event);
  }

  checkCharactersAndNumbersOnly(event: any): boolean {
    return Util.checkCharactersAndNumbersOnly(event);
  }

  checkNumbersDecimalOnly(event: any): boolean {
    return Util.checkNumbersDecimalOnly(event);
  }

  onError(errorMessage: string) {
    console.log(errorMessage);
  }
}
