import { Component, OnInit } from '@angular/core';
import {MainService} from "../main.service";
import {Router} from "@angular/router";
import {Tweet} from "../tweet";

@Component({
  selector: 'app-trash-tweets',
  templateUrl: './trash-tweets.component.html',
  styleUrls: ['./trash-tweets.component.css']
})
export class TrashTweetsComponent implements OnInit {

  constructor(private mainService: MainService,
              private router: Router) { }

  tweets: Tweet[];

  ngOnInit(): void {
    this.mainService.getDeletedList().subscribe(data => {
      this.tweets = data;
    });
  }

  addAuthorToFavorite(id: string) {
    this.mainService.addAuthorToFavourite(id).subscribe(data =>{
        console.log(data);
      },
      error => console.log(error))
  }

  deletePermamentTweetById(id: string) {
    this.mainService.deletePermamentTweetById(id).subscribe(data =>{
        console.log(data);
        this.ngOnInit();
      },
      error => console.log(error))
  }

  revertDeleteTweetById(id: string) {
    this.mainService.revertDeleteTweetById(id).subscribe(data =>{
        console.log(data);
        this.ngOnInit();
      },
      error => console.log(error))
  }
}
