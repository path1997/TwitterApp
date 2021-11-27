import { Component, OnInit } from '@angular/core';
import {Keyword} from "../keyword";
import {Router} from "@angular/router";
import {MainService} from "../main.service";

@Component({
  selector: 'app-keywords',
  templateUrl: './keywords.component.html',
  styleUrls: ['./keywords.component.css']
})
export class KeywordsComponent implements OnInit {

  keywords: Keyword[];
  keyword: Keyword = new Keyword();
  constructor(private mainService: MainService,
              private router: Router) { }

  ngOnInit(): void {
    this.getKeywords();
  }

  private getKeywords(){
    this.mainService.getKeywordsList().subscribe(data => {
      this.keywords = data;
    });
  }
  showTweet(id: number){
    this.router.navigate(['tweets', id]);
  }

  onSubmit(){
    console.log(this.keyword);
    this.createKeyword();
  }

  private createKeyword() {
    this.mainService.createKeyword(this.keyword.name).subscribe( data =>{
        console.log(data);
        this.ngOnInit();
      },
      error => console.log(error));
  }


  deleteKeyword(id: number) {
    this.mainService.deleteKeyword(id).subscribe(data =>{
        console.log(data);
        this.ngOnInit()
    },
      error => console.log(error))
  }

  showTweetByAuthor(name: string) {
    this.router.navigate(['tweetByAuthor',name]);
  }
}
