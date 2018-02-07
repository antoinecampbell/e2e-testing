import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {DebugElement} from "@angular/core";
import {UserService} from "./login/user.service";
import {By} from "@angular/platform-browser";
import {Observable} from "rxjs/Observable";
import './rxjs-imports';
import {SharedModule} from "./shared.module";
import {RouterTestingModule} from "@angular/router/testing";

describe('AppComponent', () => {
  let de: DebugElement;
  let component: AppComponent;
  let userService: UserService;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        SharedModule,
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        UserService
      ]
    }).compileComponents().then(() => {
      fixture = TestBed.createComponent(AppComponent);
      component = fixture.debugElement.componentInstance;
      de = fixture.debugElement;
      userService = TestBed.get(UserService);
    });
  }));

  beforeEach(() => {
    userService.user = {name: 'user'};
    spyOn(userService, "checkAuth").and.returnValue(Observable.of(true));
  });

  it('should create the app', () => {
    expect(component).toBeDefined();
  });

  it('should show login button when signed out', () => {
    userService.user = null;
    fixture.detectChanges();
    const element = de.query(By.css('mat-toolbar [routerLink="/login"]'));
    expect(element).toBeTruthy();
  });

  it('should show page links when signed in', () => {
    fixture.detectChanges();
    const elements = de.queryAll(By.css('mat-toolbar a[mat-button]'));
    expect(elements.length).toBe(2);
  });

});
