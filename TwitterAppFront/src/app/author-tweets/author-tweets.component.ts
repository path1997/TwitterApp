import { Component, OnInit } from '@angular/core';
import {MainService} from "../main.service";
import {ActivatedRoute} from "@angular/router";
import {Tweet} from "../tweet";

@Component({
  selector: 'app-author-tweets',
  templateUrl: './author-tweets.component.html',
  styleUrls: ['./author-tweets.component.css']
})
export class AuthorTweetsComponent implements OnInit {

  name: string;
  tweets: Tweet[];
  constructor(private mainService: MainService,
              private route: ActivatedRoute) { }


  ngOnInit(): void {
    this.name = this.route.snapshot.params['name'];

    this.mainService.getTweetsByAuthorName(this.name).subscribe(data => {
      this.tweets = data;
    }, error => console.log(error));
  }

}
