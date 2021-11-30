import { Component, OnInit } from '@angular/core';
import {Tweet} from "../tweet";
import {MainService} from "../main.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-favourite-author-tweets',
  templateUrl: './favourite-author-tweets.component.html',
  styleUrls: ['./favourite-author-tweets.component.css']
})
export class FavouriteAuthorTweetsComponent implements OnInit {
  id: string;
  tweets: Tweet[];
  constructor(private mainService: MainService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.mainService.getTweetsByAuthorId(this.id).subscribe(data => {
      this.tweets = data;
    }, error => console.log(error));
  }

  deleteTweetById(id: string) {
    this.mainService.deleteTweetById(id).subscribe(data => {
      this.ngOnInit()
    }, error => console.log(error));
  }
}
