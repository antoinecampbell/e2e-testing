import {browser} from 'protractor';
import {E2EUtils} from './e2e-utils';
import {RegisterPage} from './register.po';


describe('Register Page', () => {
  const page: RegisterPage = new RegisterPage();

  beforeAll(() => {
    browser.restart();
  });

  beforeEach(() => {
    page.navigateTo();
  });

  it('should display register page', () => {
    expect(page.getTitle()).toEqual('Create Account');
    expect(browser.getCurrentUrl()).toContain('/register');

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'register-page'));
  });

  it('should create test account and navigate to login', () => {
    expect(browser.getCurrentUrl()).toContain('/register');
    page.createAccount('test', 'test');
    expect(browser.getCurrentUrl()).toContain('/login');

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'register-account'));
  });

  it('should login with test account', () => {
    browser.get('/login');
    expect(browser.getCurrentUrl()).toContain('/login');
    E2EUtils.login('test', 'test');
    expect(browser.getCurrentUrl()).toMatch('^' + browser.baseUrl);

    browser.takeScreenshot().then(png => E2EUtils.writeScreenShot(png, 'register-login'));
  });

});
