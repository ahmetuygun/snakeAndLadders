import { Component, OnInit } from '@angular/core';

import {ActivatedRoute, Router} from '@angular/router';
import {PlayService} from "../service/play.service";
import {PlaySession} from "./playSession";
import {MdbModalRef, MdbModalService} from 'mdb-angular-ui-kit/modal';
import {Player} from "./player";
import {ModalComponent} from "../modal/modal.component";
import {pattern} from "./model/patters";


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  public diceValues: Array<number> = [0, 0];
  diceButtonLabel: string = "Roll Dice";
  public cells: any[][];

  sessionId: string;
  playSession : PlaySession
  winner : Player;

  modalRef: MdbModalRef<ModalComponent> | null = null;

  constructor(private playService: PlayService,
              private route: ActivatedRoute, public modalService: MdbModalService) {

    this.cells = pattern;

  }
  diceRoll() {
    for (let i = 0; i < this.diceValues.length; i++) {
      this.diceValues[i] = Math.floor(Math.random() * 6) + 1;
    }
    let currentPlayerUuid = this.playSession.currentPlayerId
    this.diceButtonLabel = this.diceValues[0] + " | " +  this.diceValues[1]
      this.playService.calculateStep(currentPlayerUuid, this.diceValues[0],this.diceValues[1], this.sessionId).subscribe((data) => {
        this.playSession.players.filter( player => player.uuid === currentPlayerUuid).forEach( item => item.currentBox = data.player.currentBox );
        if(data.winner){

          this.modalRef = this.modalService.open(ModalComponent, {
            data: { winner: this.playSession.players.filter( player => player.uuid === currentPlayerUuid)[0].name },
          });
          this.playSession.winnerPlayerId =  currentPlayerUuid;

        }
        this.playSession.currentPlayerId = data.nextPlayerUuid;
      });

  }

  ngOnInit(): void {
    this.sessionId = this.route.snapshot.params['sessionId'];
    this.playService.getSession(this.sessionId).subscribe((data) => {
      this.playSession = data;
    });
  }

  getPoint(boxNumber: any, shape: string) {
    return this.playSession.players.filter( player => player.currentBox === boxNumber && player.shape ===shape).length>0
  }
}
