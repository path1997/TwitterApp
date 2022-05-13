import { Component, OnInit } from '@angular/core';
import {Keyword} from "../keyword";
import {MainService} from "../main.service";
import {Router} from "@angular/router";
import {Author} from "../author";
import {NgxSpinnerService} from "ngx-spinner";
import {
  AppearanceAnimation,
  DialogLayoutDisplay, DisappearanceAnimation,
  ToastNotificationInitializer, ToastPositionEnum,
  ToastProgressBarEnum, ToastUserViewTypeEnum
} from "@costlydeveloper/ngx-awesome-popup";

@Component({
  selector: 'app-favourite-authors',
  templateUrl: './favourite-authors.component.html',
  styleUrls: ['./favourite-authors.component.css']
})
export class FavouriteAuthorsComponent implements OnInit {
  authors: Author[];
  constructor(private mainService: MainService,
              private router: Router,
              private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.mainService.getFavouriteAuthors().subscribe(data => {
      this.authors = data;
    });
  }

  showFavouriteAuthorTweets(id: string) {
    this.router.navigate(['favouriteAuthorsTweets',id]);
  }

  deleteFavouriteAuthor(id: string, name: string) {
    this.spinner.show();
    this.mainService.deleteFavouriteAuthor(id).subscribe(data =>{
      this.spinner.hide();
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Ulubiony autor');
        newToastNotification.setMessage('Usunięto autora '+ name + 'z ulubionych');

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
      },error => {
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
