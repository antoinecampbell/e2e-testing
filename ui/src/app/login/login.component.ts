import {Component} from "@angular/core";
import {UserService} from "./user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material";

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent {

  form: FormGroup;

  constructor(private userService: UserService,
              private router: Router,
              private matSnackBar: MatSnackBar,
              private fb: FormBuilder) {
    this.form = fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onLogin(): void {
    const username = this.form.controls['username'].value;
    const password = this.form.controls['password'].value;
    this.userService.login(username, password)
      .subscribe(() => {
        this.router.navigate(['']);
      }, (error) => {
        this.matSnackBar.open('Error signing in', null,
          {duration: 4000, verticalPosition: "top"});
        this.form.controls['password'].reset();
        console.error(error);
      });
  }

}
