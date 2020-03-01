import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {DebugElement} from '@angular/core';
import {of} from 'rxjs';
import {LoginComponent} from './login.component';
import {UserService} from './user.service';
import {SharedModule} from '../shared.module';
import {RouterTestingModule} from '@angular/router/testing';
import {By} from '@angular/platform-browser';
import {Router} from '@angular/router';

describe('LoginComponent', () => {
  let de: DebugElement;
  let component: LoginComponent;
  let userService: UserService;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        SharedModule,
        RouterTestingModule
      ],
      declarations: [
        LoginComponent
      ],
      providers: [
        UserService
      ]
    }).compileComponents().then(() => {
      fixture = TestBed.createComponent(LoginComponent);
      component = fixture.debugElement.componentInstance;
      de = fixture.debugElement;
      userService = TestBed.inject(UserService);
    });
  }));

  beforeEach(() => {
    userService.user = {name: 'user'};
    spyOn(userService, 'checkAuth').and.returnValue(of(true));
  });

  it('should create component', () => {
    expect(component).toBeDefined();
  });

  it('should disable sign in button when fields not entered', () => {
    fixture.detectChanges();
    const button = de.query(By.css('button'));
    expect(button.properties.disabled).toBeTruthy();
  });

  it('should enable sign in button when fields entered', () => {
    fixture.detectChanges();
    const username = de.query(By.css('input[formControlName="username"'));
    username.nativeElement.value = 'user';
    username.nativeElement.dispatchEvent(new Event('input'));
    const password = de.query(By.css('input[formControlName="password"'));
    password.nativeElement.value = 'user';
    password.nativeElement.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    const button = de.query(By.css('button'));
    expect(button.properties.disabled).toBeFalsy();
  });

  it('should navigate on successful sign in', () => {
    component.form.setValue({
      username: 'user',
      password: 'user'
    });
    fixture.detectChanges();

    const router = TestBed.inject(Router);
    const routerSpy = spyOn(router, 'navigate').and.stub();
    spyOn(userService, 'login').and.returnValue(of(''));
    const button = de.query(By.css('button'));
    button.nativeElement.click();
    fixture.detectChanges();

    expect(routerSpy).toHaveBeenCalledTimes(1);
    expect(routerSpy.calls.first().args[0]).toEqual(['']);
  });

});
