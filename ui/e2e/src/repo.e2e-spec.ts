import {browser} from 'protractor';
import {E2EUtils} from './e2e-utils';
import {RepoPage} from './repo.po';

describe('Repo Page', () => {
  const page: RepoPage = new RepoPage();

  beforeAll(() => {
    browser.restart();
  });

  beforeEach(() => {
    page.navigateTo();
  });

  it('should display repo page', () => {
    E2EUtils.login();
    expect(browser.getCurrentUrl()).toMatch('^' + browser.baseUrl);
    page.navigateTo();
    expect(page.getTitle()).toEqual('Github Repo Search');
    expect(browser.getCurrentUrl()).toContain('/repo');

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'repo-page'));
  });

  it('should display repo search results', () => {
    page.search('angular');
    expect(page.getRepos().count()).toBeGreaterThan(10);

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'repo-search'));
  });

  it('should add note to first repo', () => {
    page.search('angular');
    let repos = page.getRepos();
    page.openAddNote(repos.first());

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'repo-open-note'));

    page.addNote('Test', 'Test');
    browser.sleep(1000);

    repos = page.getRepos();
    expect(page.getNoteCount(repos.first()).getText()).toBe('NOTES (1)');

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'repo-add-note'));
  });

});
