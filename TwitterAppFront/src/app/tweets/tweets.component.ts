import { Component, OnInit } from '@angular/core';
import {Tweet} from "../tweet";
import {MainService} from "../main.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-tweets',
  templateUrl: './tweets.component.html',
  styleUrls: ['./tweets.component.css']
})
export class TweetsComponent implements OnInit {

  id: string;
  tweets: Tweet[];
  constructor(private mainService: MainService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.mainService.getTweetsListById(this.id).subscribe(data => {
      this.tweets = data;
    }, error => console.log(error));
  }

  addAuthorToFavorite(id: string) {
    this.mainService.addAuthorToFavourite(id).subscribe(data =>{
        console.log(data);
      },
      error => console.log(error))
  }

  deleteTweetById(id: string) {
    this.mainService.deleteTweetById(id).subscribe(data =>{
        console.log(data);
        this.ngOnInit()
      },
      error => console.log(error))
  }
}
