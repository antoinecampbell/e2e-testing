import {GithubRepo} from '../repo/github-repo';

export class Note {
  title: string;
  description: string;
  githubRepoUrl: string;
  githubRepo: GithubRepo;
  _links: { [name: string]: { [name: string]: string } };
}

export class NoteEmbedded {
  notes: Note[];
}

export class NotesResponse {
  _embedded: NoteEmbedded;
  _links: { [name: string]: { [name: string]: string } };
}

