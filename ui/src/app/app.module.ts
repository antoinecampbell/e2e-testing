import {APP_INITIALIZER, NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {LoginComponent} from './login/login.component';
import {UserService} from './login/user.service';
import {NoteComponent} from './note/note.component';
import {NoteService} from './note/note.service';
import {AuthGuard} from './login/auth-guard.service';
import {AppRoutingModule} from './app-routing.module';
import {AnonymousGuard} from './login/anonymous-guard.service';
import {RepoComponent} from './repo/repo.component';
import {GithubRepoService} from './repo/github-repo.service';
import {NoteDialogComponent} from './note/note-dialog.component';
import {DefaultHeaderInterceptorService} from './default-header-interceptor.service';
import {NoteDialogPagerComponent} from './note/note-dialog-pager.component';
import {RegisterComponent} from './login/register.component';
import {SharedModule} from './shared.module';
import {of} from 'rxjs';
import {catchError, flatMap} from 'rxjs/operators';

export function appInit(userService: UserService): () => void {
  return () =>
    userService.checkAuth()
      .pipe(
        flatMap(() => of(true)),
        catchError(() => of(true))
      ).toPromise();
}

@NgModule({
  imports: [
    SharedModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NoteComponent,
    RepoComponent,
    NoteDialogComponent,
    NoteDialogPagerComponent
  ],
  entryComponents: [
    NoteDialogComponent,
    NoteDialogPagerComponent
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: appInit,
      multi: true,
      deps: [UserService]
    },
    {provide: HTTP_INTERCEPTORS, useClass: DefaultHeaderInterceptorService, multi: true},
    UserService,
    NoteService,
    GithubRepoService,
    AuthGuard,
    AnonymousGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
