import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {GithubRepo, GithubReposResponse} from './github-repo';

export interface IGithubRepoService {

  searchRepos(query: string): Observable<GithubReposResponse>;

  getRepo(url: string): Observable<GithubRepo>;
}

@Injectable()
export class GithubRepoService implements IGithubRepoService {

  constructor(private httpClient: HttpClient) {
  }

  searchRepos(query: string): Observable<GithubReposResponse> {
    const params = new HttpParams()
      .append('q', query);
    return this.httpClient.get('/api/repos', {
      params: params
    }) as Observable<GithubReposResponse>;
  }

  getRepo(url: string): Observable<GithubRepo> {
    const params = new HttpParams()
      .append('url', url);
    return this.httpClient.get('/api/repos', {
      params: params
    }) as Observable<GithubRepo>;
  }

}
