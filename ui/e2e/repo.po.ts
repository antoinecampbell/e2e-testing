import {PageObject} from "./page.po";
import {$, $$, browser, by, element, ElementArrayFinder, ElementFinder, protractor} from "protractor";

export class RepoPage extends PageObject {

  navigateTo(): void {
    browser.get('/repo');
  }

  search(searchTerm: string): void {
    $('input[formControlName="searchTerm"]').sendKeys(searchTerm, protractor.Key.ENTER);
  }

  getRepos(): ElementArrayFinder {
    return $$('mat-card');
  }

  openAddNote(repo: ElementFinder): void {
    repo.element(by.buttonText('ADD NOTE')).click();
  }

  addNote(title: string, description: string): void {
    $('[formControlName="title"]').sendKeys(title);
    $('[formControlName="description"]').sendKeys(description);
    element(by.buttonText('Save')).click();
  }

  getNoteCount(repo: ElementFinder): ElementFinder {
    return repo.element(by.partialButtonText('NOTES'));
  }
}
