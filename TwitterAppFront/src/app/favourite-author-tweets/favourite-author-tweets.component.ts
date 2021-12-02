import { Component, OnInit } from '@angular/core';
import {Tweet} from "../tweet";
import {MainService} from "../main.service";
import {ActivatedRoute} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-favourite-author-tweets',
  templateUrl: './favourite-author-tweets.component.html',
  styleUrls: ['./favourite-author-tweets.component.css']
})
export class FavouriteAuthorTweetsComponent implements OnInit {
  id: string;
  tweets: Tweet[];
  constructor(private mainService: MainService,
              private route: ActivatedRoute,
              private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.spinner.show();
    this.mainService.getTweetsByAuthorId(this.id).subscribe(data => {
      this.tweets = data;
      this.spinner.hide();
    }, error => console.log(error));
  }

  deleteTweetById(id: string) {
    this.spinner.show();
    this.mainService.deleteTweetById(id).subscribe(data => {
      this.spinner.hide();
      this.ngOnInit()
    }, error => console.log(error));
  }
}
