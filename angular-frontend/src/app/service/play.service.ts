import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import {PlaySession} from "../game/playSession";
import {Calculation} from "../game/model/calculation";


@Injectable({
  providedIn: 'root'
})
export class PlayService {

  private baseURL = "http://localhost:8080/api/v1/game";



  constructor(private httpClient: HttpClient) { }

  getSession(sessionId: string): Observable<PlaySession>{
    return this.httpClient.get<PlaySession>(`${this.baseURL}/getSession/${sessionId}`);
  }

  calculateStep(playerUuid: string, dice1: number, dice2: number, sessionId: String): Observable<Calculation>{
    return this.httpClient.post<Calculation>(`${this.baseURL}/calculateStep/${sessionId}` , {playerUuid:playerUuid, dice1:dice1, dice2:dice2} );
  }

}

