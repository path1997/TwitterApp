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

  id: number;
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

}
