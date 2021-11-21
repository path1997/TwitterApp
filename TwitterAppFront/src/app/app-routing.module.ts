import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InitViewComponent} from "./init-view/init-view.component";

const routes: Routes = [
  {path: 'initView', component: InitViewComponent},
  {path: '', redirectTo: 'initView', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
