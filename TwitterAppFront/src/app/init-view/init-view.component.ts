import { Component, OnInit } from '@angular/core';
import {MainService} from "../main.service";
import {Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {
  AppearanceAnimation,
  DialogLayoutDisplay, DisappearanceAnimation,
  ToastNotificationInitializer, ToastPositionEnum,
  ToastProgressBarEnum, ToastUserViewTypeEnum
} from "@costlydeveloper/ngx-awesome-popup";

@Component({
  selector: 'app-init-view',
  templateUrl: './init-view.component.html',
  styleUrls: ['./init-view.component.css']
})
export class InitViewComponent implements OnInit {

  constructor(private mainService: MainService,
              private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  refleshData() {
    this.spinner.show();
    this.mainService.refleshData().subscribe(data =>{
        this.spinner.hide();
        const newToastNotification = new ToastNotificationInitializer();

        newToastNotification.setTitle('Dane');
        newToastNotification.setMessage('Odświeżono dane');

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
