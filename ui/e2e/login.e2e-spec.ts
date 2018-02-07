import {LoginPage} from './login.po';
import {E2EUtils} from "./e2e-utils";
import {browser} from "protractor";

describe('Login Page', () => {
  const page: LoginPage = new LoginPage();

  beforeAll(() => {
    browser.restart();
  });

  beforeEach(() => {
    page.navigateTo();
  });

  it('should display login page', () => {
    expect(page.getTitle()).toEqual('Sign In');
    expect(browser.getCurrentUrl()).toContain('/login');

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'login-page'));
  });

  it('should login as user and navigate to notes page', () => {
    expect(browser.getCurrentUrl()).toContain('/login');
    E2EUtils.login();
    expect(browser.getCurrentUrl()).toMatch('^' + browser.baseUrl);

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'login-attempt'));
  });

  it('should be logged in and taken to the note page', () => {
    expect(browser.getCurrentUrl()).toMatch('^' + browser.baseUrl);

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'login-remained'));
  });
});
