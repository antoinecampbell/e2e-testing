import {Component, OnInit} from "@angular/core";
import {NoteService} from "./note.service";
import {Note} from "./note";
import {MatSnackBar} from "@angular/material";

@Component({
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.less']
})
export class NoteComponent implements OnInit {

  notes: Note[];

  constructor(private noteService: NoteService,
              private matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.loadNotes();
  }

  loadNotes(): void {
    this.noteService.getNotes()
      .subscribe(response => {
        if (response && response._embedded) {
          this.notes = response._embedded.notes;
        } else {
          this.notes = [];
        }
      }, error => {
        this.matSnackBar.open('Error loading notes', null,
          {duration: 4000, verticalPosition: "top"});
        console.error(error);
      });
  }

  onDeleteNote(note: Note): void {
    this.noteService.deleteNote(note)
      .subscribe(() => {
        this.loadNotes();
      }, error => {
        this.matSnackBar.open('Error deleting note', null,
          {duration: 4000, verticalPosition: "top"});
        console.error(error);
      });
  }
}
