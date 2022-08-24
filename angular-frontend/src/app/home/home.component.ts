import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {PlayService} from "../service/play.service";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private playService: PlayService,
    private router: Router) { }

  public createSession(){
    this.router.navigate(['game', this.getUniqueId()]);
  }

  ngOnInit(): void {
  }

  public getUniqueId(): string {
    const stringArr = [];
    for(let i = 0; i< 4; i++){
      // tslint:disable-next-line:no-bitwise
      const S4 = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
      stringArr.push(S4);
    }
    return stringArr.join('-');
  }

}
