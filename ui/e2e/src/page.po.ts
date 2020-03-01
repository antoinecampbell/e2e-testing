import {$} from 'protractor';

export abstract class PageObject {

  abstract navigateTo(): void;

  getTitle() {
    return $('app-root h1').getText();
  }
}
