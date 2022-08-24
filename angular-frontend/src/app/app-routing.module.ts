import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from "./home/home.component";
import {GameComponent} from "./game/game.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'game/:sessionId', component: GameComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
