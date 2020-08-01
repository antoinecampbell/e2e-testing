import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NoteComponent} from './note/note.component';
import {AuthGuard} from './login/auth-guard.service';
import {AnonymousGuard} from './login/anonymous-guard.service';
import {RepoComponent} from './repo/repo.component';
import {RegisterComponent} from './login/register.component';

const routes: Routes = [
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent,
    canActivate: [AnonymousGuard]
  },
  {
    path: 'register',
    pathMatch: 'full',
    component: RegisterComponent,
    canActivate: [AnonymousGuard]
  },
  {
    path: 'repo',
    pathMatch: 'full',
    component: RepoComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    pathMatch: 'full',
    component: NoteComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
