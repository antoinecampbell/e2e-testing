import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Note, NotesResponse} from "./note";

export interface INoteService {
  getNotes(): Observable<NotesResponse>;

  createNote(note: Note): Observable<Note>;

  deleteNote(note: Note): Observable<Note>;
}

@Injectable()
export class NoteService implements INoteService {

  constructor(private httpClient: HttpClient) {
  }

  getNotes(): Observable<NotesResponse> {
    return this.httpClient.get('/api/notes') as Observable<NotesResponse>;
  }

  createNote(note: Note): Observable<Note> {
    return this.httpClient.post('/api/notes', note) as Observable<Note>;
  }

  deleteNote(note: Note): Observable<Note> {
    if (note && note._links && note._links['self']) {
      return this.httpClient.delete(note._links['self']['href']) as Observable<Note>;
    } else {
      return Observable.throw(new Error('Unable to delete note with missing link'));
    }
  }
}
