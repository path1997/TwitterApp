import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InitViewComponent} from "./init-view/init-view.component";
import {KeywordsComponent} from "./keywords/keywords.component";
import {TweetsComponent} from "./tweets/tweets.component";
import {AuthorTweetsComponent} from "./author-tweets/author-tweets.component";
import {FavouriteAuthorsComponent} from "./favourite-authors/favourite-authors.component";
import {TrashTweetsComponent} from "./trash-tweets/trash-tweets.component";
import {FavouriteAuthorTweetsComponent} from "./favourite-author-tweets/favourite-author-tweets.component";

const routes: Routes = [
  {path: 'initView', component: InitViewComponent},
  {path: '', redirectTo: 'initView', pathMatch: 'full'},
  {path: 'keywords', component: KeywordsComponent},
  {path: 'tweets/:id', component: TweetsComponent},
  {path: 'tweetByAuthor/:name', component: AuthorTweetsComponent},
  {path: 'favouriteAuthors', component: FavouriteAuthorsComponent},
  {path: 'favouriteAuthorsTweets/:id', component: FavouriteAuthorTweetsComponent},
  {path: 'tweetByAuthor/:name', component: AuthorTweetsComponent},
  {path: 'trashTweets', component: TrashTweetsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
