import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GithubRepo} from '../repo/github-repo';
import {NoteService} from './note.service';
import {Note} from './note';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  templateUrl: './note-dialog.component.html',
  styleUrls: ['./note-dialog.component.less']
})
export class NoteDialogComponent {

  form: FormGroup;

  constructor(private noteService: NoteService,
              private matSnackBar: MatSnackBar,
              private matDialogRef: MatDialogRef<NoteDialogComponent>,
              private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data: { repo: GithubRepo }) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: '',
      githubRepoUrl: data.repo.url
    });
  }

  onCreateNote(): void {
    const note: Note = this.form.value;
    this.noteService.createNote(note)
      .subscribe(savedNote => {
        this.matDialogRef.close(savedNote);
      }, error => {
        this.matSnackBar.open('Error creating note', null,
          {duration: 4000, verticalPosition: 'top'});
        console.error(error);
      });
  }

}
