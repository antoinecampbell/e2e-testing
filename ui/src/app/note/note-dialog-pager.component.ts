import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA} from "@angular/material";
import {Note} from "./note";
import {GithubRepo} from "../repo/github-repo";

@Component({
  templateUrl: './note-dialog-pager.component.html',
  styleUrls: ['./note-dialog-pager.component.less']
})
export class NoteDialogPagerComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: { repo: GithubRepo, notes: Note[] }) {
  }

}
