import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import { InitViewComponent } from './init-view/init-view.component';
import { KeywordsComponent } from './keywords/keywords.component';
import { TweetsComponent } from './tweets/tweets.component';
import { AuthorTweetsComponent } from './author-tweets/author-tweets.component';
import { TrashTweetsComponent } from './trash-tweets/trash-tweets.component';
import { FavouriteAuthorsComponent } from './favourite-authors/favourite-authors.component';
import { FavouriteAuthorTweetsComponent } from './favourite-author-tweets/favourite-author-tweets.component';
import {
  NgxAwesomePopupModule,
  DialogConfigModule,
  ConfirmBoxConfigModule,
  ToastNotificationConfigModule
} from '@costlydeveloper/ngx-awesome-popup';
import { NgxSpinnerModule } from "ngx-spinner";

@NgModule({
  declarations: [
    AppComponent,
    InitViewComponent,
    KeywordsComponent,
    TweetsComponent,
    AuthorTweetsComponent,
    TrashTweetsComponent,
    FavouriteAuthorsComponent,
    FavouriteAuthorTweetsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxAwesomePopupModule.forRoot(),
    DialogConfigModule.forRoot(),
    ConfirmBoxConfigModule.forRoot(),
    ToastNotificationConfigModule.forRoot(),
    NgxSpinnerModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
