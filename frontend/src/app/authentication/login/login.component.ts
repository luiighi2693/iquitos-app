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
              private usuarioExternoService: UsuarioExternoService) {}

  ngOnInit() {
    this.authenticationService.logout();

    this.form = this.fb.group({
      uname: [null, Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required])]
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
                  this.router.navigate(['/dashboards'], { replaceUrl: true });
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
}
