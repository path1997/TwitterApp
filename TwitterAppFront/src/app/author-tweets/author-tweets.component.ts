import { Component, OnInit } from '@angular/core';
import {MainService} from "../main.service";
import {ActivatedRoute} from "@angular/router";
import {Tweet} from "../tweet";
import {NgxSpinnerService} from "ngx-spinner";
import {
  AppearanceAnimation,
  DialogLayoutDisplay, DisappearanceAnimation,
  ToastNotificationInitializer, ToastPositionEnum,
  ToastProgressBarEnum, ToastUserViewTypeEnum
} from "@costlydeveloper/ngx-awesome-popup";

@Component({
  selector: 'app-author-tweets',
  templateUrl: './author-tweets.component.html',
  styleUrls: ['./author-tweets.component.css']
})
export class AuthorTweetsComponent implements OnInit {

  name: string;
  tweets: Tweet[];
  constructor(private mainService: MainService,
              private route: ActivatedRoute,
              private spinner: NgxSpinnerService) { }


  ngOnInit(): void {
    this.name = this.route.snapshot.params['name'];
    this.spinner.show();
    this.mainService.getTweetsByAuthorName(this.name).subscribe(data => {
      this.tweets = data;
      this.spinner.hide();
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

  deleteTweetById(id: string) {
    this.spinner.show();
    this.mainService.deleteTweetById(id).subscribe(data => {
      this.spinner.hide();
      const newToastNotification = new ToastNotificationInitializer();

      newToastNotification.setTitle('Tweet');
      newToastNotification.setMessage('Usunięto tweet');

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

  addAuthorToFavorite(id: string, username: string) {
    this.spinner.show();
    this.mainService.addAuthorToFavourite(id).subscribe(data => {
      this.spinner.hide();
      const newToastNotification = new ToastNotificationInitializer();

      newToastNotification.setTitle('Autor');
      newToastNotification.setMessage('Dodano autora ' + username + ' do ulubionych');

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
}
