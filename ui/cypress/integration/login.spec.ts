/// <reference types="cypress" />


import {LoginUtils} from '../utils/login-utils';
import {Utils} from '../utils/utils';

describe('Login Page', () => {

  it('should display page', () => {
    LoginUtils.loadPage();
    Utils.getTitle().should('be.visible');
    LoginUtils.getUsernameField().should('be.visible');
    LoginUtils.getPasswordField().should('be.visible');
    LoginUtils.getSignInButton().should('be.visible')
      .should('be.disabled');
  });

  it('should login as user and navigate to notes page', () => {
    LoginUtils.loadPage();
    LoginUtils.getUsernameField().focus()
      .type('user');
    LoginUtils.getPasswordField().focus()
      .type('user');
    LoginUtils.getSignInButton().should('be.enabled')
      .click();

    cy.location('pathname').should('eq', '/');
    Utils.getTitle().should('contain', 'Notes')
      .should('be.visible');
  });

  it('should be logged in already and taken to the note page', () => {
    cy.server();
    cy.route({
      method: 'GET',
      url: '/api/users',
      response: {name: 'user'}
    });
    cy.route({
      method: 'GET',
      url: '/api/notes',
      response: []
    });
    LoginUtils.loadPage();
    Utils.getTitle().should('contain', 'Notes')
      .should('be.visible');

    cy.location('pathname').should('eq', '/');
    Utils.getTitle().should('contain', 'Notes')
      .should('be.visible');
  });

  it('should navigate to the registration page', () => {
    LoginUtils.loadPage();
    LoginUtils.getCreateAccountLink().click();
    cy.location('pathname').should('eq', '/register');
    Utils.getTitle().should('contain', 'Create Account')
      .should('be.visible');
  });

});
