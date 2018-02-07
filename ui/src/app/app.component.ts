import {Component} from '@angular/core';
import {UserService} from "./login/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {

  constructor(public userService: UserService,
              private router: Router) {
  }

  onLogout() {
    this.userService.logout()
      .subscribe(() => {
        this.router.navigate(['/login']);
      }, error => {
        console.error(error);
      });
  }
}
