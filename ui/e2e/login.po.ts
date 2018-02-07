import {browser} from 'protractor';
import {PageObject} from "./page.po";

export class LoginPage extends PageObject {

  navigateTo() {
    return browser.get('/login');
  }
}
