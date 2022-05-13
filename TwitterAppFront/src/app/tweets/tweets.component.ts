import { Component, OnInit } from '@angular/core';
import {Tweet} from "../tweet";
import {MainService} from "../main.service";
import {ActivatedRoute, Router} from "@angular/router";
import {
  ToastNotificationInitializer,
  DialogLayoutDisplay,
  ToastUserViewTypeEnum,
  ToastProgressBarEnum,
  ToastPositionEnum, AppearanceAnimation, DisappearanceAnimation,
} from '@costlydeveloper/ngx-awesome-popup';
import {NgxSpinnerService} from "ngx-spinner";

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
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.spinner.show();
    this.mainService.getTweetsListById(this.id).subscribe(data => {
      this.tweets = data;
      this.spinner.hide();
    }, error => console.log(error));
  }

  addAuthorToFavorite(id: string, username: string) {
    this.spinner.show();
    this.mainService.addAuthorToFavourite(id).subscribe(data =>{
        this.spinner.hide();
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Autor');
        newToastNotification.setMessage('Dodano autora '+username + ' do ulubionych');

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

  deleteTweetById(id: string) {
    this.spinner.show();
    this.mainService.deleteTweetById(id).subscribe(data =>{
        this.spinner.hide()
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Tweet');
        newToastNotification.setMessage('Przeniesiono tweet do kosza');

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
}
