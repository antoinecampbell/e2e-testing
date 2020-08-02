/// <reference types="cypress" />

export class Utils {
  static getTitle() {
    return cy.get('h1');
  }
}
