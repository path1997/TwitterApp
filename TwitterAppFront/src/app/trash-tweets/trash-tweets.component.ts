import { Component, OnInit } from '@angular/core';
import {MainService} from "../main.service";
import {Router} from "@angular/router";
import {Tweet} from "../tweet";
import {
  AppearanceAnimation,
  DialogLayoutDisplay, DisappearanceAnimation,
  ToastNotificationInitializer, ToastPositionEnum,
  ToastProgressBarEnum, ToastUserViewTypeEnum
} from "@costlydeveloper/ngx-awesome-popup";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-trash-tweets',
  templateUrl: './trash-tweets.component.html',
  styleUrls: ['./trash-tweets.component.css']
})
export class TrashTweetsComponent implements OnInit {

  constructor(private mainService: MainService,
              private spinner: NgxSpinnerService) { }

  tweets: Tweet[];

  ngOnInit(): void {
    this.mainService.getDeletedList().subscribe(data => {
      this.tweets = data;
    });
  }

  addAuthorToFavorite(id: string, username: string) {
    this.mainService.addAuthorToFavourite(id).subscribe(data =>{
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
      },
      error => console.log(error))
  }

  deletePermamentTweetById(id: string) {
    this.mainService.deletePermamentTweetById(id).subscribe(data =>{
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Autor');
        newToastNotification.setMessage('Usunięto tweet permamentnie');

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
        this.ngOnInit();
      },
      error => console.log(error))
  }

  revertDeleteTweetById(id: string) {
    this.mainService.revertDeleteTweetById(id).subscribe(data =>{
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Tweet');
        newToastNotification.setMessage('Przywrócono tweet');

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
      },
      error => console.log(error))
  }
}
