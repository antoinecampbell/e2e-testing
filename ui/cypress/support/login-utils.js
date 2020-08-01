/**
 * Helper methods for login testing
 */
export class LoginUtils {
  static visit() {
    return cy.visit('/login');
  }

  static loadPage() {
    this.visit();
    cy.location('pathname').should('eq', '/login');
  }

  static getUsernameField() {
    return cy.get('input[formControlName=username]');
  }

  static getPasswordField() {
    return cy.get('input[formControlName=password]');
  }

  static getSignInButton() {
    return cy.get('form').contains('Sign In');
  }

  static getCreateAccountLink() {
    return cy.contains('Create Account');
  }

  static login(username='user', password='user') {
    this.getUsernameField().type(username);
    this.getPasswordField().type(password);
    this.getSignInButton().click();
  }
}
