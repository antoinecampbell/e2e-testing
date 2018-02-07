import {Component} from "@angular/core";
import {FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {UserService} from "./user.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less']
})
export class RegisterComponent {

  form: FormGroup;

  constructor(private userService: UserService,
              private router: Router,
              private matSnackBar: MatSnackBar,
              private fb: FormBuilder) {
    this.form = fb.group({
      username: ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      confirmPassword: ['', Validators.required]
    }, {
      validator: this.passwordValidator()
    });
  }

  passwordValidator(): ValidatorFn {
    return (form: FormGroup): { [key: string]: any } => {
      if (form) {
        form.controls['confirmPassword'].setErrors(null);
        if (form.controls['password'].value !== form.controls['confirmPassword'].value) {
          form.controls['confirmPassword'].setErrors({match: true});
        }
      }
      return {};
    };
  }

  onRegister(): void {
    this.userService.createUser(this.form.value)
      .subscribe(() => {
        this.router.navigate(['']);
      }, (error: HttpErrorResponse) => {
        if (error && error.status === 409) {
          this.matSnackBar.open('Error username already taken', null,
            {duration: 4000, verticalPosition: "top"});
        } else {
          this.matSnackBar.open('Error creating account', null,
            {duration: 4000, verticalPosition: "top"});
        }
        console.error(error);
      });
  }

}
