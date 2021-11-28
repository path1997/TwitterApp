import { Component, OnInit } from '@angular/core';
import {Keyword} from "../keyword";
import {MainService} from "../main.service";
import {Router} from "@angular/router";
import {Author} from "../author";

@Component({
  selector: 'app-favourite-authors',
  templateUrl: './favourite-authors.component.html',
  styleUrls: ['./favourite-authors.component.css']
})
export class FavouriteAuthorsComponent implements OnInit {
  authors: Author[];
  constructor(private mainService: MainService,
              private router: Router) { }

  ngOnInit(): void {
    this.mainService.getFavouriteAuthors().subscribe(data => {
      this.authors = data;
    });
  }

  showFavouriteAuthorTweets(id: string) {

  }

  deleteFavouriteAuthor(id: string) {

  }
}
