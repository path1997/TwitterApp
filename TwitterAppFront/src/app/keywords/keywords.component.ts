import { Component, OnInit } from '@angular/core';
import {Keyword} from "../keyword";
import {Router} from "@angular/router";
import {MainService} from "../main.service";
import {NgxSpinnerService} from "ngx-spinner";
import {
  AppearanceAnimation,
  DialogLayoutDisplay, DisappearanceAnimation,
  ToastNotificationInitializer, ToastPositionEnum,
  ToastProgressBarEnum, ToastUserViewTypeEnum
} from "@costlydeveloper/ngx-awesome-popup";

@Component({
  selector: 'app-keywords',
  templateUrl: './keywords.component.html',
  styleUrls: ['./keywords.component.css']
})
export class KeywordsComponent implements OnInit {

  keywords: Keyword[];
  keyword: Keyword = new Keyword();
  constructor(private mainService: MainService,
              private router: Router,
              private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.getKeywords();
  }

  private getKeywords(){
    this.mainService.getKeywordsList().subscribe(data => {
      this.keywords = data;
    }, error => {
      const newToastNotification = new ToastNotificationInitializer();

      newToastNotification.setTitle('Błąd');
      newToastNotification.setMessage('Błąd po stronie backendu, więcej informacji w logach');

      newToastNotification.setConfig({
        AutoCloseDelay: 4000,
        TextPosition: 'center',
        LayoutType: DialogLayoutDisplay.DANGER,
        ProgressBar: ToastProgressBarEnum.INCREASE,
        ToastUserViewType: ToastUserViewTypeEnum.SIMPLE,
        AnimationIn: AppearanceAnimation.BOUNCE_IN,
        AnimationOut: DisappearanceAnimation.BOUNCE_OUT,
        ToastPosition: ToastPositionEnum.BOTTOM_RIGHT,
      });

      newToastNotification.openToastNotification$();
    });
  }
  showTweet(id: string){
    this.router.navigate(['tweets', id]);
  }

  onSubmit(){
    console.log(this.keyword);
    this.createKeyword();
  }

  private createKeyword() {
    this.spinner.show();
    this.mainService.createKeyword(this.keyword.name).subscribe( data =>{
        this.spinner.hide();
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Słowo klucz');
        newToastNotification.setMessage('Dodano słowo klucz '+ this.keyword.name);

        newToastNotification.setConfig({
          AutoCloseDelay: 4000,
          TextPosition: 'center',
          LayoutType: DialogLayoutDisplay.SUCCESS,
          ProgressBar: ToastProgressBarEnum.INCREASE,
          ToastUserViewType: ToastUserViewTypeEnum.SIMPLE,
          AnimationIn: AppearanceAnimation.BOUNCE_IN,
          AnimationOut: DisappearanceAnimation.BOUNCE_OUT,
          ToastPosition: ToastPositionEnum.BOTTOM_RIGHT,
        });
        newToastNotification.openToastNotification$();
        this.ngOnInit();
      }, error => {
      this.spinner.hide();
      const newToastNotification = new ToastNotificationInitializer();

      newToastNotification.setTitle('Błąd');
      newToastNotification.setMessage('Błąd po stronie backendu, więcej informacji w logach');

      newToastNotification.setConfig({
        AutoCloseDelay: 4000,
        TextPosition: 'center',
        LayoutType: DialogLayoutDisplay.DANGER,
        ProgressBar: ToastProgressBarEnum.INCREASE,
        ToastUserViewType: ToastUserViewTypeEnum.SIMPLE,
        AnimationIn: AppearanceAnimation.BOUNCE_IN,
        AnimationOut: DisappearanceAnimation.BOUNCE_OUT,
        ToastPosition: ToastPositionEnum.BOTTOM_RIGHT,
      });

      newToastNotification.openToastNotification$();
    });
  }


  deleteKeyword(id: string, name: string) {
    this.spinner.show();
    this.mainService.deleteKeyword(id).subscribe(data =>{
        this.spinner.hide();
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Słowo klucz');
        newToastNotification.setMessage('Usunięto słowo kluczowe '+name);

        newToastNotification.setConfig({
          AutoCloseDelay: 4000,
          TextPosition: 'center',
          LayoutType: DialogLayoutDisplay.DANGER,
          ProgressBar: ToastProgressBarEnum.INCREASE,
          ToastUserViewType: ToastUserViewTypeEnum.SIMPLE,
          AnimationIn: AppearanceAnimation.BOUNCE_IN,
          AnimationOut: DisappearanceAnimation.BOUNCE_OUT,
          ToastPosition: ToastPositionEnum.BOTTOM_RIGHT,
        });
        newToastNotification.openToastNotification$();
        this.ngOnInit()
    }, error => {
      this.spinner.hide();
      const newToastNotification = new ToastNotificationInitializer();

      newToastNotification.setTitle('Błąd');
      newToastNotification.setMessage('Błąd po stronie backendu, więcej informacji w logach');

      newToastNotification.setConfig({
        AutoCloseDelay: 4000,
        TextPosition: 'center',
        LayoutType: DialogLayoutDisplay.DANGER,
        ProgressBar: ToastProgressBarEnum.INCREASE,
        ToastUserViewType: ToastUserViewTypeEnum.SIMPLE,
        AnimationIn: AppearanceAnimation.BOUNCE_IN,
        AnimationOut: DisappearanceAnimation.BOUNCE_OUT,
        ToastPosition: ToastPositionEnum.BOTTOM_RIGHT,
      });

      newToastNotification.openToastNotification$();
    });
  }

  showTweetByAuthor(name: string) {
    this.router.navigate(['tweetByAuthor',name]);
  }
}
