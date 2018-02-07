import {Component} from "@angular/core";
import {GithubRepoService} from "./github-repo.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatDialog, MatSnackBar} from "@angular/material";
import {NoteDialogComponent} from "../note/note-dialog.component";
import {NoteService} from "../note/note.service";
import {GithubRepo} from "./github-repo";
import {Note} from "../note/note";
import {NoteDialogPagerComponent} from "../note/note-dialog-pager.component";

@Component({
  templateUrl: './repo.component.html',
  styleUrls: ['./repo.component.less']
})
export class RepoComponent {

  repos: GithubRepo[];
  form: FormGroup;
  loading: boolean;

  constructor(private githubRepoService: GithubRepoService,
              private noteService: NoteService,
              private fb: FormBuilder,
              private matSnackBar: MatSnackBar,
              private matDialog: MatDialog) {
    this.form = fb.group({
      searchTerm: ''
    });
  }

  onSearch(): void {
    this.repos = null;
    this.loading = true;
    this.githubRepoService.searchRepos(this.form.controls['searchTerm'].value)
      .finally(() => this.loading = false)
      .subscribe(response => {
        if (response && response._embedded) {
          this.repos = response._embedded.githubRepos;
        } else {
          this.repos = [];
        }
      }, error => {
        this.matSnackBar.open('Error searching repos', null,
          {duration: 4000, verticalPosition: "top"});
        console.error(error);
      });
  }

  onCreateNote(repo: GithubRepo): void {
    const dialogRef = this.matDialog.open(NoteDialogComponent, {
      data: {repo: repo}
    });

    dialogRef.afterClosed().subscribe((savedNote: Note) => {
      if (savedNote) {
        this.githubRepoService.getRepo(savedNote.githubRepoUrl)
          .subscribe(repoToUpdate => {
            this.updateRepo(repoToUpdate);
          }, error => {
            this.matSnackBar.open('Error refreshing repo', null,
              {duration: 4000, verticalPosition: "top"});
            console.error(error);
          });
      }
    });
  }

  onShowNotes(repo: GithubRepo): void {
    this.matDialog.open(NoteDialogPagerComponent, {
      data: {repo: repo, notes: repo.notes}
    });
  }

  updateRepo(updatedRepo: GithubRepo): void {
    if (this.repos) {
      const index = this.repos.findIndex(repo => {
        return updatedRepo.id === repo.id;
      });
      if (index !== -1) {
        this.repos[index] = updatedRepo;
      } else {
        console.warn('Updated repo not in list of repos');
      }
    }
  }

  // noinspection JSMethodCanBeStatic
  repoTrackByFn(index: number, repo: GithubRepo): any {
    return repo ? repo.id : undefined;
  }

}
