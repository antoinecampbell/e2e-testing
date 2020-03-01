import {NotePage} from './note.po';
import {E2EUtils} from './e2e-utils';
import {browser} from 'protractor';

describe('Note Page', () => {
  const page = new NotePage();

  beforeAll(() => {
    browser.restart();
  });

  beforeEach(() => {
    page.navigateTo();
  });

  it('should display note page', () => {
    E2EUtils.login();
    expect(page.getTitle()).toEqual('Notes');
    expect(browser.getCurrentUrl()).toMatch('^' + browser.baseUrl);

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'note-page'));
  });

  it('should have 2 notes', () => {
    expect(page.getNotes().count()).toBe(2);
  });

  it('should delete 1 note', () => {
    let notes = page.getNotes();
    expect(notes.count()).toBe(2);

    page.deleteNote(notes.first());
    notes = page.getNotes();
    expect(notes.count()).toBe(1);
  });


});
