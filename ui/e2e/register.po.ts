import {$, browser, by, element} from "protractor";
import {PageObject} from "./page.po";

export class RegisterPage extends PageObject {

  navigateTo(): void {
    browser.get('/register');
  }

  createAccount(username: string, password: string): void {
    $('[formControlName="username"]').sendKeys(username);
    $('[formControlName="password"]').sendKeys(password);
    $('[formControlName="confirmPassword"]').sendKeys(password);
    element(by.css('button')).click();
  }
}
