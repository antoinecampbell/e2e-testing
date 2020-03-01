import {$$, browser, by, ElementArrayFinder, ElementFinder} from 'protractor';
import {PageObject} from './page.po';

export class NotePage extends PageObject {

  navigateTo(): void {
    browser.get('');
  }

  getNotes(): ElementArrayFinder {
    return $$('mat-card');
  }

  deleteNote(note: ElementFinder): void {
    note.element(by.buttonText('DELETE')).click();
  }
}
