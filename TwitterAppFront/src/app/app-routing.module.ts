import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InitViewComponent} from "./init-view/init-view.component";
import {KeywordsComponent} from "./keywords/keywords.component";
import {TweetsComponent} from "./tweets/tweets.component";
import {AuthorTweetsComponent} from "./author-tweets/author-tweets.component";

const routes: Routes = [
  {path: 'initView', component: InitViewComponent},
  {path: '', redirectTo: 'initView', pathMatch: 'full'},
  {path: 'keywords', component: KeywordsComponent},
  {path: 'tweets/:id', component: TweetsComponent},
  {path: 'tweetByAuthor/:name', component: AuthorTweetsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
