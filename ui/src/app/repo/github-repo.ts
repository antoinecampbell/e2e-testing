import {Note} from '../note/note';

export class GithubRepo {
  id: number;
  name: string;
  full_name: string;
  description: string;
  html_url: string;
  url: string;
  watchers: number;
  forks: number;
  stargazers_count: number;
  owner: { avatar_url: string };
  notes: Note[];
}

export class GithubReposEmbedded {
  githubRepos: GithubRepo[];
}

export class GithubReposResponse {
  _embedded: GithubReposEmbedded;
}
